package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.loja.Loja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LojaRepository extends JpaRepository<Loja, Long> {

    Optional<Loja> findByIdAndAtivoTrue(Long id);

    Optional<Loja> findByCnpjAndAtivoTrue(String cnpj);

    Optional<Page<Loja>> findAllByAtivoTrue(Pageable pageable);

    List<Loja> findAllByAtivoTrue();
}
