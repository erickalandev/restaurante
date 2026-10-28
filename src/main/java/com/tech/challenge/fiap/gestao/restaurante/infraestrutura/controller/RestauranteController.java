package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.AtualizarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.gateway.RestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.presenter.RestaurantePresenter;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.AtualizarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.CadastrarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.ConsultarRestauranteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.restaurante.DeletarRestauranteClienteUseCase;
import com.tech.challenge.fiap.gestao.restaurante.utils.LogUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);

    private final IDataSource dataSource;

    public RestauranteController(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RestauranteController create(IDataSource dataSource) {
        return new RestauranteController(dataSource);
    }

    @PostMapping("/consultar")
    public ResponseEntity<List<RestauranteDTO>> consultarRestaurantes(@RequestBody ConsultarRestauranteDTO request) {
        var operacao = "consultar Restaurantes";
        LogUtil.inicio(log, operacao, request);

        try {
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = ConsultarRestauranteUseCase.consultar(restauranteGateway);
            var restaurantes = usecase.executar(request);
            var restaurantesDTO = restaurantes.stream().map(RestaurantePresenter::toDTO).toList();

            LogUtil.fim(log, operacao, restaurantesDTO);
            return ResponseEntity.ok(restaurantesDTO);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, request, e);
            throw e;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<RestauranteDTO> cadastrarRestaurante(@RequestBody RestauranteDTO request) {
        var operacao = "cadastrar Restaurante";
        LogUtil.inicio(log, operacao, request);

        try {
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = CadastrarRestauranteUseCase.cadastrar(restauranteGateway);
            var restaurante = usecase.executar(request);
            var restauranteDTO = RestaurantePresenter.toDTO(restaurante);

            LogUtil.fim(log, operacao, restauranteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTO);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, request, e);
            throw e;
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<RestauranteDTO> atualizarRestaurante(@RequestHeader String nomeRestaurante,
                                                               @RequestHeader String donoRestaurante,
                                                               @RequestBody AtualizarRestauranteDTO request) {
        var operacao = "atualizar Restaurante";
        var dados = "nomeRestaurante: " + nomeRestaurante + " donoRestaurante: " + donoRestaurante +" request: ";
        LogUtil.inicio(log, operacao, dados + request);
        var restauranteDTO = new RestauranteDTO(nomeRestaurante, request.tipoCozinha(), request.horarioFuncionamento(),
                                                request.contato(), request.enderecos(), donoRestaurante);

        try {
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = AtualizarRestauranteUseCase.atualizar(restauranteGateway);
            var restaurante = usecase.executar(restauranteDTO);
            var response = RestaurantePresenter.toDTO(restaurante);

            LogUtil.fim(log, operacao, dados + response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, dados + request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, dados + request, e);
            throw e;
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarRestaurante(@RequestHeader String nomeRestaurante,
                                                   @RequestHeader String donoRestaurante) {
        var operacao = "deletar Restaurante";
        var dados = "nomeRestaurante: " + nomeRestaurante + " donoRestaurante: " + donoRestaurante;
        LogUtil.inicio(log, operacao, dados);

        try {
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = DeletarRestauranteClienteUseCase.deletar(restauranteGateway);
            usecase.executar(nomeRestaurante, donoRestaurante);
            LogUtil.fim(log, operacao, dados);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, dados, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, dados, e);
            throw e;
        }
    }

}
