package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Long> {


    List<PontoEletronico> findByAtendenteIdAndDataBetween(Long id, LocalDate dataInicio, LocalDate dataFim);
}
