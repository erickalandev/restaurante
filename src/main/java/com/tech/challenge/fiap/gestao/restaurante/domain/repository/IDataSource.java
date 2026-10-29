package com.tech.challenge.fiap.gestao.restaurante.domain.repository;

import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;

import java.util.List;
import java.util.Set;

public interface IDataSource {
    Usuario buscarUsuarioPorCpf(String cpf);
    Usuario cadastrarUsuario(Usuario novoUsuarioClienteDTO);
    Set<Usuario> buscarUsuarios(String nome, String cpf, String email, String contato);
    Usuario atualizarUsuario(Usuario atualizarUsuarioClienteDTO);
    void deletarUsuarioPorId(Long id);
    Restaurante buscarRestaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante);
    Set<Restaurante> buscarRestauranteComFiltro(String nome, String estado, String cidade, String tipoCozinha, String donoRestaurante);
    Restaurante cadastrarRestaurante(Restaurante restaurante);
    Restaurante atualizarRestaurante(Restaurante restaurante);
    Restaurante buscarRestaurantePorId(Long restauranteId);
    void deletarRestaurante(Long restauranteId);
    List<ItensCardapio> salvarTodosItensCardapio(List<ItensCardapio> itensCardapio);
    ItensCardapio buscarItensCardapioPorId(Long codigoItemCardapio);
    void deletarItemCardapio(Long codigoItemCardapio);
    ItensCardapio atualizarItemCardapio(ItensCardapio itemCardapioAtualizado);
}
