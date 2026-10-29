package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoCozinhaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="restaurante")
public class RestauranteEntity implements Serializable {

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
    @OneToMany(mappedBy = "restauranteEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnderecoEntity> enderecoEntities = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity donoRestaurante;

    @OneToMany(mappedBy = "restauranteEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ItensCardapioEntity> itensCardapioEntity = new HashSet<>();

    public RestauranteEntity(Long restauranteId, String nome, TipoCozinhaEnum tipoCozinha, String horarioFuncionamento, String contato) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.contato = contato;
    }

    public void vincularUsuario(UsuarioEntity usuarioEntity) {
        this.donoRestaurante = usuarioEntity;
    }

    public void vincularEndereco(Set<EnderecoEntity> enderecos) {
        enderecoEntities.clear();
        enderecoEntities.addAll(enderecos);
    }
}
