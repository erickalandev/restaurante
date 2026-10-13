package com.tech.challenge.fiap.gestao.restaurante.controller;

import com.tech.challenge.fiap.gestao.restaurante.dtos.CadastrarItensCardapioRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.service.ItensCardapioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-cardapio")
public class ItensCardapioController {

    private final ItensCardapioService itensCardapioService;

    public ItensCardapioController(ItensCardapioService itensCardapioService) {
        this.itensCardapioService = itensCardapioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarItensCardapio(@RequestBody @Valid CadastrarItensCardapioRequest request) {
        itensCardapioService.cadastrarItensCardapio(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<ItensCardapioDTO>> consultarItensCardapio(@RequestParam("nomeRestaurante") @Valid @NotBlank(message = "Nome do restaurante é obrigatório") String nomeRestaurante,
                                                                         @RequestParam("donoRestaurante") @Valid @NotBlank(message = "Nome do dono do restaurante é obrigatório") String donoRestaurante) {
        var itensCardapio = itensCardapioService.consultarItensCardapioPorRestaurante(nomeRestaurante, donoRestaurante);
        return ResponseEntity.ok(itensCardapio);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarItensCardapio(@RequestParam @Valid @NotNull(message = "Código do item do cardápio é obrigatório") Long codigoItemCardapio) {
        itensCardapioService.deletarItensCardapio(codigoItemCardapio);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizarItensCardapio(@RequestBody @Valid ItensCardapioDTO itensCardapioDTO) {
        itensCardapioService.atualizarItensCardapio(itensCardapioDTO);
        return ResponseEntity.noContent().build();
    }
}
