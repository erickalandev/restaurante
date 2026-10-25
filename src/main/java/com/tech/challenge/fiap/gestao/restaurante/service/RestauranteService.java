package com.tech.challenge.fiap.gestao.restaurante.service;

import com.tech.challenge.fiap.gestao.restaurante.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ConsultarRestauranteResponse;
import com.tech.challenge.fiap.gestao.restaurante.exception.ObjetoNaoEncontrado;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.repository.RestauranteRepository;
import jakarta.validation.Valid;
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
    public List<ConsultarRestauranteResponse> consultarRestaurantes(ConsultarRestauranteDTO request) {
        return restauranteRepository.findAllCustom(request)
                                        .stream()
                                        .map(ConsultarRestauranteResponse::toResponse)
                                        .toList();
    }

    @Transactional
    public Restaurante cadastrarRestaurante(RestauranteDTO request) {
        logger.info("Cadastrando restaurante: {}", request);
        Restaurante restaurante = new Restaurante().popularRestaurante(request);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante findByNomeAndDonoRestaurante(String nomeRestaurante, String donoRestaurante) {
        return restauranteRepository.findByNomeAndDonoRestaurante_Nome(nomeRestaurante, donoRestaurante);
    }

    @Transactional
    public Restaurante atualizarRestaurante(Long codigoRestaurante, @Valid RestauranteDTO request) {
        Restaurante restaurante = restauranteRepository.findById(codigoRestaurante)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Restaurante não encontrado com o código: " + codigoRestaurante));
        restaurante.popularRestaurante(request);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void deletarRestaurante(Long codigoRestaurante) {
        if (!restauranteRepository.existsById(codigoRestaurante)) {
            throw new ObjetoNaoEncontrado("Restaurante não encontrado com o código: " + codigoRestaurante);
        }
        restauranteRepository.deleteById(codigoRestaurante);
    }
}
