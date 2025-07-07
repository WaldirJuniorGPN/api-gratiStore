package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import com.gratiStore.api_gratiStore.domain.entities.resultado.ResultadoHoraExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoHoraExtraRepository extends JpaRepository<ResultadoHoraExtra, Long> {

    Optional<ResultadoHoraExtra> findByAtendenteAndMesAndAno(Atendente atendente, Integer mes, Integer ano);
}
