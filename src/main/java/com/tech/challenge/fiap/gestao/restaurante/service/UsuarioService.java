package com.tech.challenge.fiap.gestao.restaurante.service;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarUsuarioResponse;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteRequest;
import com.tech.challenge.fiap.gestao.restaurante.model.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarUsuarioCliente(UsuarioClienteRequest request) {
        Usuario usuario = new Usuario().cadastrarCliente(request.nome(),
                                                         request.cpf(),
                                                         request.email(),
                                                         request.contato(),
                                                        request.enderecos());
        usuarioRepository.save(usuario);
    }

    public List<ConsultarUsuarioResponse> listarUsuarios(ConsultaUsuarioRequest request) {
        return usuarioRepository.findAllCustom(request)
                                .stream()
                                .map(ConsultarUsuarioResponse::toResponse)
                                .toList();
    }
}