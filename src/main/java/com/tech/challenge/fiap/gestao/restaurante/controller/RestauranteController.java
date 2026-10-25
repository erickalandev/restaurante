package com.tech.challenge.fiap.gestao.restaurante.controller;

import com.tech.challenge.fiap.gestao.restaurante.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteResponse;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.service.RestauranteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping("/consultar")
    public ResponseEntity<List<ConsultarRestauranteResponse>> consultarRestaurantes(@RequestBody ConsultarRestauranteDTO request) {
        return ResponseEntity.ok(restauranteService.consultarRestaurantes(request));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Restaurante> cadastrarRestaurante(@RequestBody @Valid RestauranteDTO request) {
        Restaurante restaurante = restauranteService.cadastrarRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Restaurante> atualizarRestaurante(@RequestParam @NotNull(message = "O código do restaurante é obrigatório") Long codigoRestaurante,
                                                            @RequestBody @Valid RestauranteDTO request) {
        Restaurante restaurante = restauranteService.atualizarRestaurante(codigoRestaurante, request);
        return ResponseEntity.status(HttpStatus.OK).body(restaurante);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarRestaurante(@RequestParam @NotNull(message = "O código do restaurante é obrigatório") Long codigoRestaurante) {
        restauranteService.deletarRestaurante(codigoRestaurante);
        return ResponseEntity.noContent().build();
    }

}
