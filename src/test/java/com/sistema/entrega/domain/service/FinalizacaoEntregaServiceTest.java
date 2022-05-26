package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.model.StatusEntrega;
import com.sistema.entrega.domain.repository.EntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FinalizacaoEntregaServiceTest {
    private FinalizacaoEntregaService fixture;
    private EntregaRepository repository;
    private BuscaEntregaService service;

    @BeforeEach
    void setUp() {
        repository = mock(EntregaRepository.class);
        service = mock(BuscaEntregaService.class);
        fixture = new FinalizacaoEntregaService(repository, service);
    }

    @DisplayName("FINALIZAR COM SUCESSO")
    @Test
    void finalizar_sucesso() {
        Entrega entrega = new Entrega();
        entrega.setId(7L);
        entrega.setStatus(StatusEntrega.PENDENTE);
        when(service.buscar(any())).thenReturn(entrega);
        fixture.finalizar(7L);
        verify(repository, atLeastOnce()).save(any());
    }

    @DisplayName("FINALIZAR FALTA")
    @Test
    void finalizar_FALTA() {
        Entrega entrega = new Entrega();
        entrega.setId(7L);
        entrega.setStatus(StatusEntrega.CANCELADA);
        when(service.buscar(any())).thenReturn(entrega);
        assertThrows(Exception.class, () -> fixture.finalizar(7L));
        verify(repository, never()).save(any());
    }
}