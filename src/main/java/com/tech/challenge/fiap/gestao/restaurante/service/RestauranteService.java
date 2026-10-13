package com.tech.challenge.fiap.gestao.restaurante.service;

import com.tech.challenge.fiap.gestao.restaurante.dtos.CadastrarRestauranteRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteResponse;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.repository.RestauranteRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final Logger logger = LoggerFactory.getLogger(RestauranteService.class);

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional(readOnly = true)
    public List<ConsultarRestauranteResponse> consultarRestaurantes(ConsultarRestauranteRequest request) {
        return restauranteRepository.findAllCustom(request)
                                        .stream()
                                        .map(ConsultarRestauranteResponse::toResponse)
                                        .toList();
    }

    @Transactional
    public void cadastrarRestaurante(CadastrarRestauranteRequest request) {
        logger.info("Cadastrando restaurante: {}", request);
        Restaurante restaurante = new Restaurante().popularRestaurante(request.nome(),
                                                                         TipoCozinhaEnum.getValue(request.tipoCozinha()),
                                                                         request.horarioFuncionamento(),
                                                                         request.contato(),
                                                                         request.enderecos(),
                                                                         request.donoRestaurante());
        restauranteRepository.save(restaurante);
    }

    public Restaurante findByNomeAndDonoRestaurante(String nomeRestaurante, String donoRestaurante) {
        return restauranteRepository.findByNomeAndDonoRestaurante_Nome(nomeRestaurante, donoRestaurante);
    }
}
