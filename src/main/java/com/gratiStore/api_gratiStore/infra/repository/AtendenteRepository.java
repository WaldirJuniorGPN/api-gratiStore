package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.atendente.Atendente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtendenteRepository extends JpaRepository<Atendente, Long> {

    Optional<Atendente> findByIdAndAtivoTrue(Long id);

    Optional<Page<Atendente>> findAllByAtivoTrue(Pageable pageable);

    List<Atendente> findAllByAtivoTrue();
}
