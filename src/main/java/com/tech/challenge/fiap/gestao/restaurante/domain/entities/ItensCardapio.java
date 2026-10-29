package com.tech.challenge.fiap.gestao.restaurante.domain.entities;

import java.math.BigDecimal;

public class ItensCardapio {
    private Long codigoItemCardapio;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponibilidadeComercioLocal;
    private String foto;
    private Restaurante restaurante;

    public static ItensCardapio criarItemCardapio(String nome, BigDecimal preco, String descricao, Boolean disponibilidadeComercioLocal, String foto) {
        ItensCardapio.validaCamposObrigatorios(nome, preco);
        ItensCardapio item = new ItensCardapio();
        item.nome = nome;
        item.preco = preco;
        item.descricao = descricao;
        item.disponibilidadeComercioLocal = disponibilidadeComercioLocal;
        item.foto = foto;
        return item;
    }

    public static ItensCardapio atualizarItemCardapio(Long codigoItemCardapio, String nome, BigDecimal preco, String descricao, Boolean disponibilidadeComercioLocal, String foto) {
        ItensCardapio item = criarItemCardapio(nome, preco, descricao, disponibilidadeComercioLocal, foto);
        item.setCodigoItemCardapio(codigoItemCardapio);
        return item;
    }

    public ItensCardapio adicionarRestaurante(Restaurante restaurante) {
        this.restaurante = Restaurante.merge(restaurante);
        return this;
    }

    private static void validaCamposObrigatorios(String nome, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do item do cardápio é obrigatório.");
        }
        if (preco == null) {
            throw new IllegalArgumentException("O preço do item do cardápio é obrigatório.");
        }
    }

    public Long getCodigoItemCardapio() {
        return codigoItemCardapio;
    }

    public void setCodigoItemCardapio(Long codigoItemCardapio) {
        this.codigoItemCardapio = codigoItemCardapio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getDisponibilidadeComercioLocal() {
        return disponibilidadeComercioLocal;
    }

    public void setDisponibilidadeComercioLocal(Boolean disponibilidadeComercioLocal) {
        this.disponibilidadeComercioLocal = disponibilidadeComercioLocal == null ? Boolean.FALSE : disponibilidadeComercioLocal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
