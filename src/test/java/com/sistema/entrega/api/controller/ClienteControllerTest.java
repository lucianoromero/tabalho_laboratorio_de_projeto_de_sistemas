package com.sistema.entrega.api.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sistema.entrega.domain.model.Cliente;
import com.sistema.entrega.domain.repository.ClienteRepository;
import com.sistema.entrega.domain.service.CatalogoClienteService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest {
    private MockMvc mvc;
    @InjectMocks
    private ClienteController fixture;
    @Mock
    private ClienteRepository repository;
    @Mock
    private CatalogoClienteService service;

    private static final String URL_API = "/clientes";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(fixture)
                .build();
    }

    @DisplayName("TESTE ENDPOINT LISTAR")
    @Test
    void listar() throws Exception {
        List<Cliente> clientes = List.of(new Cliente());
        when(repository.findAll()).thenReturn(clientes);

        mvc.perform(get(URL_API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new JsonMapper().writeValueAsString(clientes)));
    }

    @DisplayName("TESTE ENDPOINT BUSCAR")
    @Test
    void buscar() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(7L);
        when(repository.findById(any())).thenReturn(Optional.of(cliente));
        final Long id = 7L;

        mvc.perform(get(URL_API + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        verify(repository, atLeastOnce()).findById(id);
    }

    @DisplayName("TESTE ENDPOINT ADICIONAR")
    @Test
    void adicionar() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(7L);
        when(service.salvar(any(Cliente.class))).thenReturn(cliente);

        String json = "{\n" +
                "    \"id\": 7,\n" +
                "   \"nome\": \"teste\",\n" +
                "    \"email\": \"teste@teste.com\",\n" +
                "    \"telefone\": \"(35) 00000000\"\n" +
                "}";

        mvc.perform(post(URL_API)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("TESTE ENDPOINT ATUALIZAR COM SUCESSO")
    @Test
    void atualizar_com_sucesso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(7L);
        when(repository.existsById(any())).thenReturn(true);
        when(service.salvar(any(Cliente.class))).thenReturn(cliente);

        String json = "{\n" +
                "    \"id\": 7,\n" +
                "   \"nome\": \"teste\",\n" +
                "    \"email\": \"teste@teste.com\",\n" +
                "    \"telefone\": \"(00) 123456789\"\n" +
                "}";

        final Long id = 7L;
        mvc.perform(put(URL_API + "/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("TESTE ENDPOINT ATUALIZAR NÃO ENCONTRADO")
    @Test
    void atualizar_nao_encontrado() throws Exception {
        when(repository.existsById(any())).thenReturn(false);

        String json = "{\n" +
                "    \"id\": 7,\n" +
                "   \"nome\": \"teste\",\n" +
                "    \"email\": \"teste@teste.com\",\n" +
                "    \"telefone\": \"(00) 123456789\"\n" +
                "}";

        final Long id = 7L;
        mvc.perform(put(URL_API + "/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("TESTE ENDPOINT REMOVER SUCESSO")
    @Test
    void remover_sucesso() throws Exception {
        when(repository.existsById(any())).thenReturn(true);
        Long clienteId = 7L;
        mvc.perform(delete(URL_API + "/{clienteId}", clienteId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("TESTE ENDPOINT REMOVER FALHA")
    @Test
    void remover_falha() throws Exception {
        when(repository.existsById(any())).thenReturn(false);
        Long clienteId = 7L;
        mvc.perform(delete(URL_API + "/{clienteId}", clienteId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}