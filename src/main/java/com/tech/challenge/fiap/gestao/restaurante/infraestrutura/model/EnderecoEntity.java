package com.tech.challenge.fiap.gestao.restaurante.infraestrutura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco")
public class EnderecoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enderecoId;

    @Column(nullable = false, length = 100)
    private String rua;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 100)
    private String estado;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 25)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restauranteEntity;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioEntity;

}
