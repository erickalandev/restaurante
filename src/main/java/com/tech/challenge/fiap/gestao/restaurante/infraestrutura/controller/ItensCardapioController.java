package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.controller;

import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.gateway.ItensCardapioGateway;
import com.tech.challenge.fiap.gestao.restaurante.application.gateway.RestauranteGateway;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.presenter.ItensCardapioPresenter;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.AtualizarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.CadastrarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.ConsultarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.application.usecases.itensCardapio.DeletarItensCardapioUseCase;
import com.tech.challenge.fiap.gestao.restaurante.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-cardapio")
public class ItensCardapioController {

    private static final Logger log = LoggerFactory.getLogger(ItensCardapioController.class);
    private final IDataSource dataSource;

    public ItensCardapioController(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ItensCardapioController create(IDataSource dataSource) {
        return new ItensCardapioController(dataSource);
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<List<ItensCardapioDTO>> cadastrarItensCardapio(@RequestHeader String nomeRestaurante,
                                                                         @RequestHeader String donoRestaurante,
                                                                         @RequestBody List<ItensCardapioDTO> request) {
        var operacao = "cadastrar Itens Cardapio";
        var dados = "nomeRestaurante: " + nomeRestaurante + " donoRestaurante: " + donoRestaurante +" itens: ";
        LogUtil.inicio(log, operacao, dados + request);

        try {
            var itensCardapioGateway = ItensCardapioGateway.create(this.dataSource);
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = CadastrarItensCardapioUseCase.criar(itensCardapioGateway, restauranteGateway);
            var itensCardapio = usecase.executar(nomeRestaurante, donoRestaurante, request);
            var itensCardapioDTO = itensCardapio.stream().map(ItensCardapioPresenter::toDTO).toList();

            LogUtil.fim(log, operacao, dados + itensCardapioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(itensCardapioDTO);
        }  catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, dados + request, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, dados + request, e);
            throw e;
        }
    }

    @GetMapping("/consultar")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ItensCardapioDTO>> consultarItensCardapio(@RequestHeader String nomeRestaurante,
                                                                         @RequestHeader String donoRestaurante) {
        var operacao = "consultar Itens Cardapio";
        var dados = "nomeRestaurante: " + nomeRestaurante + " donoRestaurante: " + donoRestaurante;
        LogUtil.inicio(log, operacao, dados);

        try {
            var restauranteGateway = RestauranteGateway.create(this.dataSource);
            var usecase = ConsultarItensCardapioUseCase.consultar(restauranteGateway);
            var itensCardapio = usecase.executar(nomeRestaurante, donoRestaurante);
            var itensCardapioDTO = itensCardapio.stream().map(ItensCardapioPresenter::toDTO).toList();

            LogUtil.fim(log, operacao, dados);
            return ResponseEntity.ok(itensCardapioDTO);
        }  catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, dados, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, dados, e);
            throw e;
        }
    }

    @DeleteMapping("/deletar")
    @Transactional
    public ResponseEntity<Void> deletarItensCardapio(@RequestParam Long codigoItemCardapio) {
        var operacao = "deletar Itens Cardapio";
        LogUtil.inicio(log, operacao, codigoItemCardapio);

        try {
            var itensCardapioGateway = ItensCardapioGateway.create(this.dataSource);
            var usecase = DeletarItensCardapioUseCase.deletar(itensCardapioGateway);

            usecase.executar(codigoItemCardapio);
            LogUtil.fim(log, operacao, codigoItemCardapio);
            return ResponseEntity.noContent().build();
        }  catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, codigoItemCardapio, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, codigoItemCardapio, e);
            throw e;
        }
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<ItensCardapioDTO> atualizarItensCardapio(@RequestBody ItensCardapioDTO itensCardapioDTO) {
        var operacao = "atualizar Itens Cardapio";
        LogUtil.inicio(log, operacao, itensCardapioDTO);

        try {
            var itensCardapioGateway = ItensCardapioGateway.create(this.dataSource);
            var usecase = AtualizarItensCardapioUseCase.atualizar(itensCardapioGateway);
            var itensCardapio = usecase.executar(itensCardapioDTO);

            LogUtil.fim(log, operacao, itensCardapioDTO);
            return ResponseEntity.ok(ItensCardapioPresenter.toDTO(itensCardapio));
        }  catch (IllegalArgumentException e) {
            LogUtil.erroNegocio(log, operacao, itensCardapioDTO, e);
            throw e;
        } catch (Exception e) {
            LogUtil.erroInesperado(log, operacao, itensCardapioDTO, e);
            throw e;
        }
    }
}
