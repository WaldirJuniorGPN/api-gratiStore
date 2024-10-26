package com.gratiStore.api_gratiStore.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_date")
    private LocalDateTime inputDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Setter
    private boolean ativo = true;

    @PrePersist
    public void prePersist() {
        this.inputDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
