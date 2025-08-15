package com.gratiStore.api_gratiStore.domain.service.horasExtras.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.horasExtras.FiltroHorasExtrasRequest;
import com.gratiStore.api_gratiStore.controller.dto.response.horasExtras.ResultadoHorasExtrasResponse;
import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.AgrupadorDePontosPorSemana;
import com.gratiStore.api_gratiStore.domain.service.horasExtras.CalculadoraDeHorasExtras;
import com.gratiStore.api_gratiStore.domain.service.loja.LojaService;
import com.gratiStore.api_gratiStore.domain.service.ponto.PontoEletronicoService;
import com.gratiStore.api_gratiStore.infra.adapter.horasExtras.HorasExtrasAdapter;
import com.gratiStore.api_gratiStore.infra.repository.ResultadoHoraExtraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gratiStore.api_gratiStore.domain.utils.StatusUtils.COMUM;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorasExtrasServiceImplTest {

    private final int MES = 5;
    private final int ANO = 2024;
    private final BigDecimal SALARIO = BigDecimal.valueOf(1500);
    private final BigDecimal VALOR_A_RECEBER_50 = BigDecimal.valueOf(18.75);
    private final BigDecimal VALOR_A_RECEBER_100 = BigDecimal.valueOf(25);
    private final Duration HORAS_EXTRAS = Duration.ofHours(2);
    private final long ID_LOJA = 1l;
    private final String NOME_DA_LOJA = "Americanas";
    private final String NOME_DO_ATENDENTE = "Fulano";
    private final String CNPJ = "06026378000140";

    @Mock
    private PontoEletronicoService pontoEletronicoService;
    @Mock
    private CalculadoraDeHorasExtras calculadoraDeHorasExtras;
    @Mock
    private AgrupadorDePontosPorSemana agrupadorDePontosPorSemana;
    @Mock
    private ResultadoHoraExtraRepository repository;
    @Mock
    private LojaService lojaService;
    @Mock
    private HorasExtrasAdapter horasExtrasAdapter;

    @InjectMocks
    private HorasExtrasServiceImpl horasExtrasService;

    private FiltroHorasExtrasRequest filtroHorasExtrasRequest;
    private Atendente atendente;
    private Loja loja;
    private PontoEletronico pontoEletronico;
    private List<Atendente> atendenteList;

    @BeforeEach
    void setUp() {
        loja = new Loja(NOME_DA_LOJA, CNPJ);
        atendente = new Atendente(NOME_DO_ATENDENTE, loja, SALARIO);
        atendenteList = List.of(atendente);
        filtroHorasExtrasRequest = new FiltroHorasExtrasRequest(MES, ANO, ID_LOJA);
        pontoEletronico = new PontoEletronico(LocalDate.of(2024, 5, 1),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(18, 0),
                COMUM,
                atendente);

        atendenteList.forEach(atendente -> loja.adicionarAtendente(atendente));
    }

    @Test
    void calcularHorasExtrasComSucesso() {
        var pontoEletronicoList = List.of(pontoEletronico);
        var pontosAgrupadosPorSemana = Map.of(1, pontoEletronicoList);
        var totalHorasExtrasPorAtendente = Map.of(atendente, Duration.ofHours(1));

        when(pontoEletronicoService
                .listarHistorico(atendenteList, filtroHorasExtrasRequest.mes(), filtroHorasExtrasRequest.ano()))
                .thenReturn(pontoEletronicoList);

        when(agrupadorDePontosPorSemana.agrupar(pontoEletronicoList)).thenReturn(pontosAgrupadosPorSemana);
        when(lojaService.buscarLoja(filtroHorasExtrasRequest.lojaId())).thenReturn(loja);
        when(calculadoraDeHorasExtras.calcularHorasExtras(pontosAgrupadosPorSemana)).thenReturn(totalHorasExtrasPorAtendente);
        when(calculadoraDeHorasExtras.calcularValorAReceber(eq(atendente.getSalario()), any(Duration.class), any(BigDecimal.class)))
                .thenReturn(VALOR_A_RECEBER_50, VALOR_A_RECEBER_100);

        horasExtrasService.calcular(filtroHorasExtrasRequest);

        verify(pontoEletronicoService, times(1)).listarHistorico(any(List.class), any(Integer.class), any(Integer.class));
        verify(agrupadorDePontosPorSemana, times(1)).agrupar(any(List.class));
        verify(repository, times(1)).save(any(ResultadoHoraExtra.class));
    }

    @Test
    void buscarHorasExtrasComSucesso() {
        var resultadoHoraExtra = new ResultadoHoraExtra(atendente,
                filtroHorasExtrasRequest.mes(),
                filtroHorasExtrasRequest.ano(),
                VALOR_A_RECEBER_50,
                VALOR_A_RECEBER_100,
                Duration.ofHours(1));

        var resultadoHoraExtraResponse = new ResultadoHorasExtrasResponse(resultadoHoraExtra.getAtendente().getNome(),
                resultadoHoraExtra.getMes(),
                resultadoHoraExtra.getAno(),
                VALOR_A_RECEBER_50,
                VALOR_A_RECEBER_100,
                resultadoHoraExtra.getHorasExtras());

        when(repository.findByAtendenteAndMesAndAno(atendente, filtroHorasExtrasRequest.mes(), filtroHorasExtrasRequest.ano()))
                .thenReturn(Optional.of(resultadoHoraExtra));
        when(horasExtrasAdapter.horasExtrasToResultadoHorasExtrasResponse(resultadoHoraExtra)).thenReturn(resultadoHoraExtraResponse);
        when(lojaService.buscarLoja(filtroHorasExtrasRequest.lojaId())).thenReturn(loja);

        horasExtrasService.buscar(filtroHorasExtrasRequest);

        verify(repository, times(atendenteList.size())).findByAtendenteAndMesAndAno(any(Atendente.class), eq(filtroHorasExtrasRequest.mes()), eq(filtroHorasExtrasRequest.ano()));
        verify(horasExtrasAdapter, times(atendenteList.size())).horasExtrasToResultadoHorasExtrasResponse(any(ResultadoHoraExtra.class));
    }

    @Test
    void deveRetornarListaVaziaENaoChamarAdapterAoBuscarHorasExtras_quandoNaoEncontrarNenhumResultado() {

        Optional<ResultadoHoraExtra> resultadoHoraExtraOptional = Optional.empty();

        when(lojaService.buscarLoja(filtroHorasExtrasRequest.lojaId())).thenReturn(loja);
        when(repository.findByAtendenteAndMesAndAno(atendente, filtroHorasExtrasRequest.mes(), filtroHorasExtrasRequest.ano()))
                .thenReturn(resultadoHoraExtraOptional);

        horasExtrasService.buscar(filtroHorasExtrasRequest);

        verify(horasExtrasAdapter, never()).horasExtrasToResultadoHorasExtrasResponse(any());
    }
}