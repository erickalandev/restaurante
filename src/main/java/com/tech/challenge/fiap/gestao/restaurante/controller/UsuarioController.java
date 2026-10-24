package com.tech.challenge.fiap.gestao.restaurante.controller;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarUsuarioResponse;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.model.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Usuario> cadastrarUsuarioCliente(@RequestBody @Valid UsuarioClienteDTO request) {
        Usuario usuario = usuarioService.cadastrarUsuarioCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuarioCliente(@RequestBody @Valid UsuarioClienteDTO request) {
        Usuario usuario = usuarioService.atualizarUsuarioCliente(request);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarUsuario(@RequestHeader String cpf) {
        usuarioService.deletarUsuario(cpf);
        return ResponseEntity.noContent().build();
    }
}
