package com.sistema.entrega.api.controller;

import com.sistema.entrega.api.assembler.OcorrenciaAssembler;
import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.service.BuscaEntregaService;
import com.sistema.entrega.domain.service.RegistroOcorrenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OcorrenciaControllerTest {
    private MockMvc mvc;
    @InjectMocks
    private OcorrenciaController fixture;
    @Mock
    private BuscaEntregaService buscaEntregaService;
    @Mock
    private RegistroOcorrenciaService registroOcorrenciaService;
    @Mock
    private OcorrenciaAssembler ocorrenciaAssembler;

    private static final String URL_API = "/entregas";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(fixture)
                .build();
    }

    @DisplayName("TESTE ENDPOINT REGISTRAR")
    @Test
    void registrar() throws Exception {
        final Long entregaId = 7L;
        String json = "{\n" +
                "    \"descricao\": \"descricao\"\n" +
                "}";

        mvc.perform(post(URL_API + "/{entregaId}", entregaId + "/ocorrencias")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("TESTE ENDPOINT LISTAR")
    @Test
    void listar() throws Exception {
        final Long entregaId = 7L;
        Entrega entrega = new Entrega();
        entrega.setId(entregaId);
        when(buscaEntregaService.buscar(any())).thenReturn(entrega);
        mvc.perform(get(URL_API + "/{entregaId}", entregaId + "/ocorrencias"))
                .andDo(print());
        verify(ocorrenciaAssembler, atLeastOnce()).toCollectionModel(any());
    }
}