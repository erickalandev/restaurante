package com.tech.challenge.fiap.gestao.restaurante.entities;

import com.tech.challenge.fiap.gestao.restaurante.utils.CepUtils;

public class Endereco {

    private Long enderecoId;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private String numero;
    private String complemento;


    public static Endereco criarEndereco(String rua, String cidade, String estado, String cep, String numero, String complemento) {
        Endereco endereco = new Endereco();
        endereco.setRua(rua);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCep(cep);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        return endereco;
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
        this.cep = CepUtils.formatarCep(cep);
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
}
