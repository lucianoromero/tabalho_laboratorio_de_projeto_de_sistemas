package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Cliente;
import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.repository.EntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SolicitacaoEntregaServiceTest {

    private CatalogoClienteService service;
    private EntregaRepository repository;
    private SolicitacaoEntregaService fixture;

    @BeforeEach
    void setUp() {
        repository = mock(EntregaRepository.class);
        service = mock(CatalogoClienteService.class);
        fixture = new SolicitacaoEntregaService(service, repository);
    }

    @DisplayName("SOLICITAR")
    @Test
    void solicitar() {
        Cliente cliente = new Cliente();
        cliente.setId(7L);
        Entrega entrega = new Entrega();
        entrega.setCliente(cliente);
        when(service.buscar(any())).thenReturn(new Cliente());
        fixture.solicitar(entrega);
        verify(repository, atLeastOnce()).save(any());
    }
}