package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Cliente;
import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CatalogoClienteServiceTest {

    private CatalogoClienteService fixture;
    private ClienteRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepository.class);
        fixture = new CatalogoClienteService(repository);
    }

    @DisplayName("BUSCAR")
    @Test
    void buscar() {
        Long id = 7L;
        when(repository.findById(any())).thenReturn(Optional.of(new Cliente()));
        Assertions.assertDoesNotThrow(() -> fixture.buscar(id));
    }

    @DisplayName("SALVAR")
    @Test
    void salvar() {
        Long id = 7L;
        when(repository.findByEmail(any())).thenReturn(Optional.of(new Cliente()));
        Assertions.assertDoesNotThrow(() -> fixture.salvar(new Cliente()));
        verify(repository, times(1)).save(any());
    }

    @DisplayName("EXCLUIR")
    @Test
    void excluir() {
        Long id = 7L;
        Assertions.assertDoesNotThrow(() -> fixture.excluir(id));
        verify(repository, atLeastOnce()).deleteById(any());
    }
}