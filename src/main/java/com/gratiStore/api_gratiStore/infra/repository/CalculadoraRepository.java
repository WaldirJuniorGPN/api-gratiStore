package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.calculadora.Calculadora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalculadoraRepository extends JpaRepository<Calculadora, Long> {

    Optional<Calculadora> findByIdAndAtivoTrue(Long id);

    Optional<Page<Calculadora>> findAllByAtivoTrue(Pageable pageable);
}
