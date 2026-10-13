package com.tech.challenge.fiap.gestao.restaurante.model;

import com.tech.challenge.fiap.gestao.restaurante.dtos.EnderecoDTO;
import jakarta.persistence.*;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
public class  Endereco implements Serializable {

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
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public static Set<Endereco> popularEnderecos(Set<EnderecoDTO> enderecosDTO, Object obj, Boolean isRestaurante) {
        return ObjectUtils.isEmpty(enderecosDTO)
                ? new HashSet<>()
                : enderecosDTO.stream()
                              .map(dto -> new Endereco().toEndereco(dto, obj, isRestaurante))
                              .collect(Collectors.toSet());
    }

    private Endereco toEndereco(EnderecoDTO dto, Object obj, Boolean isRestaurante) {
        this.rua = dto.rua();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.cep = dto.cep();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        if (isRestaurante) {
            this.restaurante = (Restaurante) obj;
        } else {
            this.usuario = (Usuario) obj;
        }
        return this;
    }

    public static List<EnderecoDTO> toEnderecosDTO(Set<Endereco> enderecos) {
        return ObjectUtils.isEmpty(enderecos) ? List.of() : enderecos.stream()
                .map(Endereco::toEnderecoDTO)
                .collect(Collectors.toList());
    }

    private EnderecoDTO toEnderecoDTO() {
        return new EnderecoDTO(
                this.rua,
                this.cidade,
                this.estado,
                this.cep,
                this.numero,
                this.complemento);
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
