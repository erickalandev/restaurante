package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="itens_cardapio")
public class ItensCardapioEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column
    private Boolean disponibilidadeComercioLocal;

    @Column(length = 255)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntity restauranteEntity;

    public ItensCardapioEntity() {}

    public ItensCardapioEntity(Long id, String nome, String descricao, BigDecimal preco, Boolean disponibilidadeComercioLocal, String foto, RestauranteEntity restauranteEntity) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeComercioLocal = disponibilidadeComercioLocal;
        this.foto = foto;
        this.restauranteEntity = restauranteEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Boolean getDisponibilidadeComercioLocal() {
        return disponibilidadeComercioLocal;
    }

    public String getFoto() {
        return foto;
    }

    public RestauranteEntity getRestaurante() {
        return restauranteEntity;
    }
}
