package com.tech.challenge.fiap.gestao.restaurante.domain.entities;

import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import com.tech.challenge.fiap.gestao.restaurante.utils.ContatoUtils;

import java.util.HashSet;
import java.util.Set;

public class Restaurante {
    private Long restauranteId;
    private String nome;
    private TipoCozinhaEnum tipoCozinha;
    private String horarioFuncionamento;
    private String contato;
    private Set<Endereco> enderecos = new HashSet<>();
    private Usuario donoRestaurante;
    private Set<ItensCardapio> itensCardapio = new HashSet<>();

    public static Restaurante criarRestaurante(Long restauranteId, String nome, String tipoCozinha, String horarioFuncionamento, String contato, String donoRestaurante) {
        Restaurante.validarCamposObrigatorio(nome, tipoCozinha, horarioFuncionamento, contato, donoRestaurante);
        Restaurante restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome(nome);
        restaurante.setTipoCozinha(tipoCozinha);
        restaurante.setHorarioFuncionamento(horarioFuncionamento);
        restaurante.setContato(contato);
        restaurante.setDonoRestaurante(Usuario.criarUsuarioDono(donoRestaurante));
        return restaurante;
    }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco == null) {
            return;
        }
        this.enderecos.add(endereco);
    }

    private static void validarCamposObrigatorio(String nome, String tipoCozinha, String horarioFuncionamento, String contato, String donoRestaurante) {
        if(nome == null || nome.isEmpty())
            throw new IllegalArgumentException("O nome do restaurante é obrigatório");
        if(tipoCozinha == null || tipoCozinha.isEmpty())
            throw new IllegalArgumentException("O tipo de cozinha é obrigatório");
        if(horarioFuncionamento == null || horarioFuncionamento.isEmpty())
            throw new IllegalArgumentException("O horário de funcionamento é obrigatório");
        if(contato == null || contato.isEmpty())
            throw new IllegalArgumentException("O contato do restaurante é obrigatório");
        if(donoRestaurante == null || donoRestaurante.isEmpty())
            throw new IllegalArgumentException("O dono do restaurante é obrigatório");
    }

    public static Restaurante merge(Restaurante restaurante) {
        Restaurante r = new Restaurante();
        r.restauranteId = restaurante.getRestauranteId();
        r.nome = restaurante.getNome();
        r.tipoCozinha = restaurante.getTipoCozinha();
        r.horarioFuncionamento = restaurante.getHorarioFuncionamento();
        r.contato = restaurante.getContato();
        r.enderecos = restaurante.getEnderecos();
        r.donoRestaurante = restaurante.getDonoRestaurante();
        return r;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCozinhaEnum getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = TipoCozinhaEnum.getValue(tipoCozinha);
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = ContatoUtils.formatarContato(contato);
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Usuario getDonoRestaurante() {
        return donoRestaurante;
    }

    public void setDonoRestaurante(Usuario donoRestaurante) {
        this.donoRestaurante = donoRestaurante;
    }

    public Set<ItensCardapio> getItensCardapio() {
        return itensCardapio;
    }

    public void setItensCardapio(Set<ItensCardapio> itensCardapio) {
        this.itensCardapio = itensCardapio;
    }
}
