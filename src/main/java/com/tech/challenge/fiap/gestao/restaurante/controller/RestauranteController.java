package com.tech.challenge.fiap.gestao.restaurante.controller;

import com.tech.challenge.fiap.gestao.restaurante.dtos.CadastrarRestauranteRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteResponse;
import com.tech.challenge.fiap.gestao.restaurante.service.RestauranteService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ConsultarRestauranteResponse>> consultarRestaurantes(@RequestBody ConsultarRestauranteRequest request) {
        return ResponseEntity.ok(restauranteService.consultarRestaurantes(request));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarRestaurante(@RequestBody @Valid CadastrarRestauranteRequest request) {
        restauranteService.cadastrarRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
