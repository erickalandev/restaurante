package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.datasource;


import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultaUsuarioDTO;
import com.tech.challenge.fiap.gestao.restaurante.application.dtos.ConsultarRestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.ItensCardapio;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Restaurante;
import com.tech.challenge.fiap.gestao.restaurante.domain.entities.Usuario;
import com.tech.challenge.fiap.gestao.restaurante.domain.repository.IDataSource;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository.EnderecoRepository;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository.UsuarioRepository;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository.ItensCardapioRepository;
import com.tech.challenge.fiap.gestao.restaurante.infraestrutura.repository.RestauranteRepository;
import com.tech.challenge.fiap.gestao.restaurante.presenter.ItensCardapioPresenter;
import com.tech.challenge.fiap.gestao.restaurante.presenter.RestaurantePresenter;
import com.tech.challenge.fiap.gestao.restaurante.presenter.UsuarioPresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class GestaoRestauranteDataSource implements IDataSource {

    private final ItensCardapioRepository itensCardapioRepository;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    public Usuario buscarUsuarioPorCpf(String cpf) {
        var usuarioEntity = usuarioRepository.findByCpf(cpf);
        return UsuarioPresenter.toDomain(usuarioEntity);
    }

    @Override
    public Usuario cadastrarUsuario(Usuario novoUsuarioCliente) {
        var UsuarioEntity = UsuarioPresenter.toEntity(novoUsuarioCliente);
        var response = usuarioRepository.save(UsuarioEntity);
        return UsuarioPresenter.toDomain(response);
    }

    @Override
    public Set<Usuario> buscarUsuarios(String nome, String cpf, String email, String contato) {
        ConsultaUsuarioDTO consultaUsuarioDTO = new ConsultaUsuarioDTO(nome, cpf, email,contato);
        var response = usuarioRepository.findAllCustom(consultaUsuarioDTO);
        return response.stream()
                .map(UsuarioPresenter::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Usuario atualizarUsuario(Usuario atualizarUsuarioClienteDTO) {
        return cadastrarUsuario(atualizarUsuarioClienteDTO);
    }

    @Override
    public void deletarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Restaurante buscarRestaurantePorNomeEDono(String nomeRestaurante, String donoRestaurante) {
        var restauranteEntity = restauranteRepository.findByNomeAndDonoRestaurante_Nome(nomeRestaurante, donoRestaurante);
        return RestaurantePresenter.toDomain(restauranteEntity);
    }

    @Override
    public Set<Restaurante> buscarRestauranteComFiltro(String nome, String estado, String cidade, String tipoCozinha, String donoRestaurante) {
        var consulta = new ConsultarRestauranteDTO(nome, estado, cidade,tipoCozinha, donoRestaurante);
        var restaurantes = restauranteRepository.findAllCustom(consulta);
        return restaurantes.stream()
                .map(RestaurantePresenter::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Restaurante cadastrarRestaurante(Restaurante restaurante) {
        var restauranteEntity = RestaurantePresenter.toEntity(restaurante);
        var response = restauranteRepository.save(restauranteEntity);
        return RestaurantePresenter.toDomain(response);
    }

    @Override
    public Restaurante atualizarRestaurante(Restaurante restaurante) {
        return cadastrarRestaurante(restaurante);
    }

    @Override
    public Restaurante buscarRestaurantePorId(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).stream()
                .findFirst()
                .map(RestaurantePresenter::toDomain)
                .get();
    }

    @Override
    public void deletarRestaurante(Long restauranteId) {
        restauranteRepository.deleteById(restauranteId);
    }

    @Override
    public List<ItensCardapio> salvarTodosItensCardapio(List<ItensCardapio> itensCardapio) {
        var request = itensCardapio.stream().map(ItensCardapioPresenter::toEntity).collect(Collectors.toSet());
        return itensCardapioRepository.saveAll(request).stream()
                .map(ItensCardapioPresenter::toDomain)
                .toList();
    }

    @Override
    public ItensCardapio buscarItensCardapioPorId(Long codigoItemCardapio) {
        return itensCardapioRepository.findById(codigoItemCardapio).stream()
                .findFirst()
                .map(ItensCardapioPresenter::toDomain)
                .get();
    }

    @Override
    public void deletarItemCardapio(Long codigoItemCardapio) {
        itensCardapioRepository.deleteById(codigoItemCardapio);
    }

    @Override
    public ItensCardapio atualizarItemCardapio(ItensCardapio itemCardapioAtualizado) {
        var request = ItensCardapioPresenter.toEntity(itemCardapioAtualizado);
        return ItensCardapioPresenter.toDomain(request);
    }
}
