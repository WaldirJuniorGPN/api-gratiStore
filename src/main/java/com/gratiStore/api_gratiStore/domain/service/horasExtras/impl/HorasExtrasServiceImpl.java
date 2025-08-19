package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.HorasExtrasService;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.vo.ResultadoPreliminar;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.adapter.horasExtras.HorasExtrasAdapter;
import com.gratiStore.api_gratiStore.infra.repository.ResultadoHoraExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ResultadoHorasExtrasResponse> calcular(FiltroHorasExtrasRequest request) {
        var atendentes = buscarAtendentesDaLoja(request.lojaId());
        var pontosEletronicos = pontoEletronicoService.listarHistorico(atendentes, request.mes(), request.ano());
        var pontosAgrupados = agrupadorDePontosPorSemana.agrupar(pontosEletronicos);
        Map<Atendente, ResultadoPreliminar> totalHorasExtrasPorAtendente = calculadora.calcularHorasExtras(pontosAgrupados);

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

    private ResultadoHorasExtrasResponse processarResultado(FiltroHorasExtrasRequest request, Map.Entry<Atendente, ResultadoPreliminar> entry) {
        var atendente = entry.getKey();
        var resultado = entry.getValue();

        var resultadoExistente = buscarResultadoNoBanco(atendente, request.mes(), request.ano());

        if (resultadoExistente.isEmpty()) {
            salvarNoBanco(request.mes(),
                    request.ano(),
                    atendente,
                    resultado);
        } else {
            atualizarResultado(resultadoExistente.get(), resultado);
        }

        return new ResultadoHorasExtrasResponse(atendente.getNome(),
                request.mes(),
                request.ano(),
                resultado.valorAReceber50(),
                resultado.valorAReceber100(),
                resultado.horas50(),
                resultado.horas100());
    }

    private List<Atendente> buscarAtendentesDaLoja(Long id) {
        var loja = lojaService.buscarLoja(id);

        return loja.getAtendentes();
    }

    private void salvarNoBanco(int mes, int ano, Atendente atendente, ResultadoPreliminar resultadoPreliminar) {
        repository.save(new ResultadoHoraExtra(atendente,
                mes,
                ano,
                resultadoPreliminar.valorAReceber50(),
                resultadoPreliminar.valorAReceber100(),
                resultadoPreliminar.horas50(),
                resultadoPreliminar.horas100()));
    }

    private void salvarNoBAnco(ResultadoHoraExtra resultadoHoraExtra) {
        repository.save(resultadoHoraExtra);
    }

    private Optional<ResultadoHoraExtra> buscarResultadoNoBanco(Atendente atendente, Integer mes, Integer ano) {
        return repository.findByAtendenteAndMesAndAno(atendente, mes, ano);
    }

    private void atualizarResultado(ResultadoHoraExtra resultadoHoraExtra, ResultadoPreliminar resultadoPreliminar) {
        resultadoHoraExtra.atualizarResultado(resultadoPreliminar.valorAReceber50(),
                resultadoPreliminar.valorAReceber100(),
                resultadoPreliminar.horas50(),
                resultadoPreliminar.horas100());
        salvarNoBAnco(resultadoHoraExtra);
    }
}
