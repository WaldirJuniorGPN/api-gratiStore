package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.ponto.PontoEletronico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Long> {

    Optional<PontoEletronico> findById(Long id);

    Optional<Page<PontoEletronico>> findPageAll(Pageable pageable);
}
