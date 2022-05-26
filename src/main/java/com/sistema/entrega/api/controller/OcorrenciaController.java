package com.sistema.entrega.api.controller;

import com.sistema.entrega.api.assembler.OcorrenciaAssembler;
import com.sistema.entrega.api.model.OcorrenciaModel;
import com.sistema.entrega.api.model.input.OcorrenciaInput;
import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.model.Ocorrencia;
import com.sistema.entrega.domain.service.BuscaEntregaService;
import com.sistema.entrega.domain.service.RegistroOcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
@Tag(name = "Ocorrencia")
public class OcorrenciaController {

    private BuscaEntregaService buscaEntregaService;
    private RegistroOcorrenciaService registroOcorrenciaService;
    private OcorrenciaAssembler ocorrenciaAssembler;

    @Operation(description = "Registrar uma ocorrencia")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel registrar(@PathVariable Long entregaId,
                                     @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {

        Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
                .registrar(entregaId, ocorrenciaInput.getDescricao());

        return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);

    }

    @Operation(description = "Listar Ocorrencias")
    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable Long entregaId) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);
        return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
    }

}