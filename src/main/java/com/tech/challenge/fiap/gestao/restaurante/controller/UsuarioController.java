package com.tech.challenge.fiap.gestao.restaurante.controller;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarUsuarioResponse;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteRequest;
import com.tech.challenge.fiap.gestao.restaurante.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/consultar")
    public ResponseEntity<List<ConsultarUsuarioResponse>> consultarUsuarios(@RequestBody ConsultaUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(request));
    }

    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<Void> cadastrarUsuarioCliente(@RequestBody @Valid UsuarioClienteRequest request) {
        usuarioService.cadastrarUsuarioCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
