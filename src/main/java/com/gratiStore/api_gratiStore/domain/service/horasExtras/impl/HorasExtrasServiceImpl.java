package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.adapter.horasExtras.HorasExtrasAdapter;
import com.gratiStore.api_gratiStore.infra.repository.ResultadoHoraExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorasExtrasServiceImpl implements HorasExtrasService {

    private static final Duration LIMITE_HORAS_50 = Duration.ofHours(2);
    private static final BigDecimal ADICIONAL_50_POR_CENTO = BigDecimal.valueOf(0.5);
    private static final BigDecimal ADICIONAL_100_POR_CENTO = BigDecimal.valueOf(1);

    private final PontoEletronicoService pontoEletronicoService;
    private final CalculadoraDeHorasExtras calculadora;
    private final AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;
    private final ResultadoHoraExtraRepository repository;
    private final LojaService lojaService;
    private final HorasExtrasAdapter adapter;

    @Override
    public List<ResultadoHorasExtrasResponse> calcular(FiltroHorasExtrasRequest request) {
        var atendentes = buscarAtendentesDaLoja(request.lojaId());
        var pontosEletronicos = pontoEletronicoService.listarHistorico(atendentes, request.mes(), request.ano());
        var pontosAgrupados = agrupadorDePontosPorSemana.agrupar(pontosEletronicos);
        Map<Atendente, Duration> totalHorasExtrasPorAtendente = calculadora.calcularHorasExtras(pontosAgrupados);

        return totalHorasExtrasPorAtendente.entrySet().stream()
                .map(entry -> processarResultado(request, entry))
                .toList();
    }

    @Override
    public List<ResultadoHorasExtrasResponse> buscar(FiltroHorasExtrasRequest request) {
        var atendentes = buscarAtendentesDaLoja(request.lojaId());

        return atendentes.stream()
                .map(atendente -> repository.findByAtendenteAndMesAndAno(atendente, request.mes(), request.ano())
                        .map(adapter::horasExtrasToResultadoHorasExtrasResponse))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

    }

    private ResultadoHorasExtrasResponse processarResultado(FiltroHorasExtrasRequest request, Map.Entry<Atendente, Duration> entry) {
        var atendente = entry.getKey();
        var horasExtras = entry.getValue();

        var horas50 = horasExtras.compareTo(LIMITE_HORAS_50) > 0 ? LIMITE_HORAS_50 : horasExtras;
        var horas100 = horasExtras.minus(horas50);

        var valor50 = calculadora.calcularValorAReceber(atendente.getSalario(), horas50, ADICIONAL_50_POR_CENTO);
        var valor100 = calculadora.calcularValorAReceber(atendente.getSalario(), horas100, ADICIONAL_100_POR_CENTO);

        var resultadoExistente = buscarResultadoNoBanco(atendente, request.mes(), request.ano());

        if (resultadoExistente.isEmpty()) {
            salvarNoBanco(request.mes(), request.ano(), atendente, valor50, valor100, horasExtras);
        } else {
            atualizarResultado(resultadoExistente.get(), valor50, valor100, horasExtras);
        }

        return new ResultadoHorasExtrasResponse(atendente.getNome(), request.mes(), request.ano(), valor50, valor100, horasExtras);
    }

    private List<Atendente> buscarAtendentesDaLoja(Long id) {
        var loja = lojaService.buscarLoja(id);

        return loja.getAtendentes();
    }

    private void salvarNoBanco(int mes, int ano, Atendente atendente, BigDecimal valor50, BigDecimal valor100, Duration horasExtras) {
        repository.save(new ResultadoHoraExtra(atendente, mes, ano, valor50, valor100, horasExtras));
    }

    private void salvarNoBAnco(ResultadoHoraExtra resultadoHoraExtra) {
        repository.save(resultadoHoraExtra);
    }

    private Optional<ResultadoHoraExtra> buscarResultadoNoBanco(Atendente atendente, Integer mes, Integer ano) {
        return repository.findByAtendenteAndMesAndAno(atendente, mes, ano);
    }

    private void atualizarResultado(ResultadoHoraExtra resultadoHoraExtra, BigDecimal valor50, BigDecimal valor100, Duration horasExtras) {
        resultadoHoraExtra.atualizarResultado(valor50, valor100, horasExtras);
        salvarNoBAnco(resultadoHoraExtra);
    }
}
