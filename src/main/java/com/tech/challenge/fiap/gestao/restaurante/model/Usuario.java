package com.tech.challenge.fiap.gestao.restaurante.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Usuario implements Serializable {

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
    private Restaurante restaurante;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new HashSet<>();

    public Usuario cadastrarDonoRestaurante(String nome) {
        this.nome = nome;
        this.tipoUsuario = TipoUsuarioEnum.DONO_RESTAURANTE;
        return this;
    }

    public Usuario cadastrarCliente(String nome, String cpf, String email, String contato, Set<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.cpf = cpf.replaceAll("\\D", "");
        this.email = email;
        this.contato = contato.replaceAll("\\D", "");
        this.tipoUsuario = TipoUsuarioEnum.CLIENTE;
        this.enderecos = Endereco.popularEnderecos(enderecos, this, Boolean.FALSE);
        return this;
    }

    public Usuario vincularRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        return this;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
