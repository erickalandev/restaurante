package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
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
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 11)
    private String cpf;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String contato;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private TipoUsuarioEnum tipoUsuario;

    @OneToOne(mappedBy = "donoRestaurante")
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restauranteEntity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "usuarioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnderecoEntity> enderecoEntities = new HashSet<>();

    public UsuarioEntity(Long usuarioId, String nome, String cpf, String email, String contato, TipoUsuarioEnum tipoUsuario) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.contato = contato;
        this.tipoUsuario = tipoUsuario;
    }

    public void vincularEndereco(Set<EnderecoEntity> enderecos) {
        enderecoEntities.clear();
        enderecoEntities.addAll(enderecos);
    }

    public void vincularRestaurante(RestauranteEntity restauranteEntity) {
        this.restauranteEntity = restauranteEntity;
    }
}
