package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Entrega;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistroOcorrenciaServiceTest {

    private BuscaEntregaService service;
    private RegistroOcorrenciaService fixture;

    @BeforeEach
    void setUp() {
        service = mock(BuscaEntregaService.class);
        fixture = new RegistroOcorrenciaService(service);
    }

    @DisplayName("REGISTRAR")
    @Test
    void registrar() {
        when(service.buscar(any())).thenReturn(new Entrega());
        Assertions.assertDoesNotThrow(() -> fixture.registrar(7L, "DESCICAO"));
    }
}