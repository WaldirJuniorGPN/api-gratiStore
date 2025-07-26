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
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorasExtrasServiceImpl implements HorasExtrasService {

    private final PontoEletronicoService pontoEletronicoService;
    private final CalculadoraDeHorasExtras calculadora;
    private final AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;
    private final ResultadoHoraExtraRepository repository;
    private final LojaService lojaService;
    private final HorasExtrasAdapter adapter;

    private BigDecimal valorAReceber = BigDecimal.ZERO;

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
        var valorAReceber = calculadora.calcularValorAReceber(atendente.getSalario(), horasExtras);
        var resultadoExistente = buscarResultadoNoBanco(atendente, request.mes(), request.ano());

        if (resultadoExistente.isEmpty()) {
            salvarNoBanco(request.mes(), request.ano(), atendente, valorAReceber, horasExtras);
        } else {
            atualizarResultado(resultadoExistente.get(), valorAReceber, horasExtras);
        }

        return new ResultadoHorasExtrasResponse(atendente.getNome(), request.mes(), request.ano(), valorAReceber, horasExtras);
    }

    private List<Atendente> buscarAtendentesDaLoja(Long id) {
        var loja = lojaService.buscarLoja(id);

        return loja.getAtendentes();
    }

    private void salvarNoBanco(int mes, int ano, Atendente atendente, BigDecimal valorAReceber, Duration horasExtras) {
        repository.save(new ResultadoHoraExtra(atendente, mes, ano, valorAReceber, horasExtras));
    }

    private void salvarNoBAnco(ResultadoHoraExtra resultadoHoraExtra) {
        repository.save(resultadoHoraExtra);
    }

    private Optional<ResultadoHoraExtra> buscarResultadoNoBanco(Atendente atendente, Integer mes, Integer ano) {
        return repository.findByAtendenteAndMesAndAno(atendente, mes, ano);
    }

    private void atualizarResultado(ResultadoHoraExtra resultadoHoraExtra, BigDecimal valorAReceber, Duration horasExtras) {
        resultadoHoraExtra.atualizarResultado(valorAReceber, horasExtras);
        salvarNoBAnco(resultadoHoraExtra);
    }
}
