package com.sistema.entrega.domain.service;

import com.sistema.entrega.domain.model.Entrega;
import com.sistema.entrega.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

    private EntregaRepository entregaRepository;
    private BuscaEntregaService buscaEntregaService;

    @Transactional
    public void finalizar(Long entregaId) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);
        entrega.finalizar();
        entregaRepository.save(entrega);
    }
}