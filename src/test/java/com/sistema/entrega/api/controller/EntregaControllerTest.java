package com.sistema.entrega.api.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sistema.entrega.api.assembler.EntregaAssembler;
import com.sistema.entrega.api.model.EntregaModel;
import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.repository.EntregaRepository;
import com.sistema.entrega.domain.service.FinalizacaoEntregaService;
import com.sistema.entrega.domain.service.SolicitacaoEntregaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EntregaControllerTest {
    private MockMvc mvc;
    @InjectMocks
    private EntregaController fixture;
    @Mock
    private EntregaRepository repository;
    @Mock
    private SolicitacaoEntregaService solicitacaoEntregaService;
    @Mock
    private FinalizacaoEntregaService finalizacaoEntregaService;
    @Mock
    private EntregaAssembler entregaAssembler;

    private static final String URL_API = "/entregas";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(fixture)
                .build();
    }


    @DisplayName("TESTE ENDPOINT SOLICITAR")
    @Test
    void solicitar() throws Exception {
        when(entregaAssembler.toEntity(any())).thenReturn(new Entrega());
        when(solicitacaoEntregaService.solicitar(any())).thenReturn(new Entrega());
        String json = "{\n" +
                "    \"cliente\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"destinatario\": {\n" +
                "        \"nome\": \"Maria da Silva\",\n" +
                "        \"logradouro\": \"Rua das Goiabas\",\n" +
                "        \"numero\": \"187\",\n" +
                "        \"complemento\": \"Casa\",\n" +
                "        \"bairro\": \"Centro\"\n" +
                "    },\n" +
                "    \"taxa\": 100.50\n" +
                "}";

        mvc.perform(post(URL_API)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("TESTE ENDPOINT FINALIZAR")
    @Test
    void finalizar() throws Exception {
        final Long entregaId = 7L;
        mvc.perform(put(URL_API + "/{id}", entregaId + "/finalizacao"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("TESTE ENDPOINT LISTAR")
    @Test
    void listar() throws Exception {
        List<Entrega> entregas = List.of(new Entrega());
        List<EntregaModel> models = List.of(new EntregaModel());
        when(repository.findAll()).thenReturn(entregas);
        when(entregaAssembler.toCollectionModel(any())).thenReturn(models);

        mvc.perform(get(URL_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new JsonMapper().writeValueAsString(models)));
    }

    @DisplayName("TESTE ENDPOINT BUSCAR")
    @Test
    void buscar() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(new Entrega()));
        final Long entregaId = 7L;

        mvc.perform(get(URL_API + "/{entregaId}", entregaId))
                .andDo(print())
                .andExpect(status().isOk());
        verify(repository, atLeastOnce()).findById(entregaId);
    }

}