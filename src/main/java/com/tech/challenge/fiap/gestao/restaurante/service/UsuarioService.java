package com.tech.challenge.fiap.gestao.restaurante.service;

import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarUsuarioResponse;
import com.tech.challenge.fiap.gestao.restaurante.dtos.UsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.exception.ObjetoNaoEncontrado;
import com.tech.challenge.fiap.gestao.restaurante.model.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuarioCliente(UsuarioClienteDTO request) {
        Usuario usuario = new Usuario().cadastrarCliente(request.nome(),
                                                         request.cpf(),
                                                         request.email(),
                                                         request.contato(),
                                                        request.enderecos());
        return usuarioRepository.save(usuario);
    }

    public List<ConsultarUsuarioResponse> listarUsuarios(ConsultaUsuarioDTO request) {
        return usuarioRepository.findAllCustom(request)
                                .stream()
                                .map(ConsultarUsuarioResponse::toResponse)
                                .toList();
    }

    @Transactional
    public Usuario atualizarUsuarioCliente(UsuarioClienteDTO request) {
        Usuario usuarioExistente = findByCpf(request.cpf());
        usuarioExistente.atualizarUsuario(request);
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(String cpf) {
        Usuario usuarioExistente = findByCpf(cpf);
        usuarioRepository.delete(usuarioExistente);
    }

    private Usuario findByCpf(String cpf) {
        Usuario usuarioExistente = usuarioRepository.findByCpf(cpf.replaceAll("\\D", ""));
        if (usuarioExistente == null) {
            throw new ObjetoNaoEncontrado("Usuário com CPF " + cpf + " não encontrado.");
        }
        return usuarioExistente;
    }
}