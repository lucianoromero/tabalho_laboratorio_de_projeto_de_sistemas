package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.repository.EntregaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuscaEntregaServiceTest {

    private BuscaEntregaService fixture;
    private EntregaRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(EntregaRepository.class);
        fixture = new BuscaEntregaService(repository);
    }

    @DisplayName("BUSCAR SUCESSO")
    @Test
    void buscar_sucesso() {
        Long entregaId = 7L;
        when(repository.findById(any())).thenReturn(Optional.of(new Entrega()));
        Assertions.assertDoesNotThrow(() -> fixture.buscar(entregaId));
    }

    @DisplayName("BUSCAR NÃO ENCONTRADO")
    @Test
    void buscar_nao_encontrado() {
        Long entregaId = 7L;
        when(repository.findById(any())).thenReturn(null);
        assertThrows(Exception.class, () -> fixture.buscar(entregaId));
    }
}