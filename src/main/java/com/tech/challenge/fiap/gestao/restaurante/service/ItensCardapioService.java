package com.tech.challenge.fiap.gestao.restaurante.service;

import com.tech.challenge.fiap.gestao.restaurante.dtos.CadastrarItensCardapioRequest;
import com.tech.challenge.fiap.gestao.restaurante.dtos.ItensCardapioDTO;
import com.tech.challenge.fiap.gestao.restaurante.model.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.model.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.repository.ItensCardapioRepository;
import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ItensCardapioService {

    private final ItensCardapioRepository itensCardapioRepository;
    private final RestauranteService restauranteService;

    public ItensCardapioService(ItensCardapioRepository itensCardapioRepository, RestauranteService restauranteService) {
        this.itensCardapioRepository = itensCardapioRepository;
        this.restauranteService = restauranteService;
    }

    @Transactional
    public void cadastrarItensCardapio(@Valid CadastrarItensCardapioRequest request) {
        Restaurante restaurante = restauranteService.findByNomeAndDonoRestaurante(request.nomeRestaurante(), request.donoRestaurante());
        if (restaurante == null) {
            throw new EmptyResultDataAccessException("Restaurante não encontrado para o nome e dono fornecidos.", 1);
        }

        List<ItensCardapio> itensCardapio = request.itensCardapio().stream()
                .map(dto -> new ItensCardapio(dto.nome(),
                        dto.descricao(),
                        dto.preco(),
                        dto.disponibilidadeComercioLocal() != null ? dto.disponibilidadeComercioLocal() : Boolean.FALSE,
                        dto.foto(),
                        restaurante))
                .toList();
        itensCardapioRepository.saveAll(itensCardapio);
    }

    @Transactional(readOnly = true)
    public List<ItensCardapioDTO> consultarItensCardapioPorRestaurante(String nomeRestaurante, String donoRestaurante) {
        var restaurante = restauranteService.findByNomeAndDonoRestaurante(nomeRestaurante, donoRestaurante);
        if (restaurante == null) {
            throw new EmptyResultDataAccessException("Restaurante não encontrado para o nome e dono fornecidos.", 1);
        }
        return restaurante.getItensCardapio().stream()
                .map(item -> new ItensCardapioDTO(
                        item.getId(),
                        item.getNome(),
                        item.getPreco(),
                        item.getDescricao(),
                        item.getDisponibilidadeComercioLocal(),
                        item.getFoto()
                ))
                .sorted(Comparator.comparing(ItensCardapioDTO::nome))
                .toList();
    }

    @Transactional
    public void deletarItensCardapio(Long codigoItemCardapio) {
        if     (!itensCardapioRepository.existsById(codigoItemCardapio)) {
            throw new EmptyResultDataAccessException("Item do cardápio não encontrado para o código fornecido.", 1);
        }
        itensCardapioRepository.deleteById(codigoItemCardapio);
    }

    @Transactional
    public void atualizarItensCardapio(ItensCardapioDTO itensCardapioDTO) {
        Long codigoItemCardapio = itensCardapioDTO.codigoItemCardapio();
        if (codigoItemCardapio == null) {
            throw new IllegalArgumentException("Código do item do cardápio é obrigatório");
        }
        ItensCardapio itensCardapioExistente = itensCardapioRepository.findById(codigoItemCardapio)
                .orElseThrow(() -> new EmptyResultDataAccessException("Item do cardápio não encontrado para o código fornecido.", 1));

        ItensCardapio itensCardapio =  new ItensCardapio(
                        codigoItemCardapio,
                        itensCardapioDTO.nome(),
                        itensCardapioDTO.descricao(),
                        itensCardapioDTO.preco(),
                        itensCardapioDTO.disponibilidadeComercioLocal() != null ? itensCardapioDTO.disponibilidadeComercioLocal() : Boolean.FALSE,
                        itensCardapioDTO.foto(),
                        itensCardapioExistente.getRestaurante());
        itensCardapioRepository.save(itensCardapio);
    }
}