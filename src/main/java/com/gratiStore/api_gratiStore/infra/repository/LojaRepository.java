package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.Loja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LojaRepository extends JpaRepository<Loja, Long> {

    Optional<Loja> findByIdAndAtivoTrue(Long id);

    Optional<Page<Loja>> findAllByAtivoTrue(Pageable pageable);
}
