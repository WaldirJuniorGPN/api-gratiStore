package com.gratiStore.api_gratiStore.infra.repository;

import com.gratiStore.api_gratiStore.domain.entities.marca.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Optional<Marca> findByIdAndAtivoTrue(Long id);

    Optional<Page<Marca>> findAllByAtivoTrue(Pageable pageable);
}
