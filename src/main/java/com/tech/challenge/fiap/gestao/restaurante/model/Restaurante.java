package com.tech.challenge.fiap.gestao.restaurante.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.challenge.fiap.gestao.restaurante.dtos.RestauranteDTO;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Restaurante implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restauranteId;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoCozinhaEnum tipoCozinha;

    @Column(nullable = false, length = 100)
    private String horarioFuncionamento;

    @Column(nullable = false, length = 20)
    private String contato;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario donoRestaurante;

    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ItensCardapio> itensCardapio = new HashSet<>();

    public Restaurante popularRestaurante(RestauranteDTO request) {
        this.nome = request.nome();
        this.tipoCozinha = TipoCozinhaEnum.getValue(request.tipoCozinha());
        this.horarioFuncionamento = request.horarioFuncionamento();
        this.contato = request.contato();
        this.enderecos.clear();
        if (request.enderecos() != null && !request.enderecos().isEmpty()) {
            this.enderecos.addAll(Endereco.popularEnderecos(request.enderecos(), this, Boolean.TRUE));
        }
        if(this.donoRestaurante == null) {
            this.donoRestaurante = new Usuario();
        }
        this.donoRestaurante.cadastrarDonoRestaurante(request.donoRestaurante())
                                            .vincularRestaurante(this);
        return this;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public TipoCozinhaEnum getTipoCozinha() {
        return tipoCozinha;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public String getContato() {
        return contato;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Usuario getDonoRestaurante() {
        return donoRestaurante;
    }

    public Set<ItensCardapio> getItensCardapio() {
        return itensCardapio;
    }
}
