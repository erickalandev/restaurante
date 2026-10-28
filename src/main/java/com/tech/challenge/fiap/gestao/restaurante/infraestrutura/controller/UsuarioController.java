package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.NovoUsuarioClienteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.UsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.gateway.UsuarioGateway;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.presenter.UsuarioPresenter;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.AtualizarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.CadastrarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.ConsultarUsuarioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.usuario.DeletarUsuarioClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final IDataSource dataSource;

    public UsuarioController(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UsuarioController create(IDataSource dataSource) {
        return new UsuarioController(dataSource);
    }

    @PostMapping("/consultar")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDTO>> consultarUsuarios(@RequestBody ConsultaUsuarioDTO request) {
        var operacao = "consultar usuarios";
        LogUtil.inicio(log, operacao, request);
        try {
            var usuarioGateway = UsuarioGateway.create(this.dataSource);
            var usecase = ConsultarUsuarioUseCase.buscar(usuarioGateway);
            var usuarios = usecase.executar(request);
            var usuariosDTO = usuarios.stream().map(UsuarioPresenter::toDTO).toList();

            LogUtil.fim(log, operacao, usuariosDTO);
            return ResponseEntity.ok(usuariosDTO);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, request, e);
            throw e;
        }
    }

    @PostMapping("/cadastrar-cliente")
    @Transactional
    public ResponseEntity<UsuarioDTO> cadastrarUsuarioCliente(@RequestBody NovoUsuarioClienteDTO request) {
        var operacao = "cadastrar Usuario Cliente";
        LogUtil.inicio(log, operacao, request);

        try {
            var usuarioGateway = UsuarioGateway.create(this.dataSource);
            var usecase = CadastrarUsuarioClienteUseCase.criar(usuarioGateway);
            var usuario = usecase.executar(request);
            var usuarioDTO = UsuarioPresenter.toDTO(usuario);

            LogUtil.fim(log, operacao, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, request, e);
            throw e;
        }
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<UsuarioDTO> atualizarUsuarioCliente(@RequestBody NovoUsuarioClienteDTO request) {
        var operacao = "atualizar Usuario Cliente";
        LogUtil.inicio(log, operacao, request);

        try {
            var usuarioGateway = UsuarioGateway.create(dataSource);
            var usecase = AtualizarUsuarioClienteUseCase.atualizar(usuarioGateway);
            var usuario = usecase.executar(request);
            var usuarioDTO = UsuarioPresenter.toDTO(usuario);

            LogUtil.fim(log, operacao, request);
            return ResponseEntity.ok(usuarioDTO);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, request, e);
            throw e;
        }
    }

    @DeleteMapping("/deletar")
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@RequestHeader String cpf) {
        var operacao = "deletar Usuario";
        LogUtil.inicio(log, operacao, cpf);

        try {
            var usuarioGateway = UsuarioGateway.create(dataSource);
            var usecase = DeletarUsuarioClienteUseCase.deletar(usuarioGateway);

            usecase.executar(cpf);
            LogUtil.fim(log, operacao, cpf);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, cpf, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, cpf, e);
            throw e;
        }
    }
}
