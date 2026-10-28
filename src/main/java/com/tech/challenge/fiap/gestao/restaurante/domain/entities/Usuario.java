package com.tech.challenge.fiap.gestao.restaurante.domain.entities;

import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.utils.ContatoUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.CpfUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.EmailUtils;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private Long usuarioId;
    private String nome;
    private String cpf;
    private String email;
    private String contato;
    private Set<Endereco> enderecos = new HashSet<>();
    private TipoUsuarioEnum tipoUsuario;

    public static Usuario criarUsuario(Long usuarioId,String nome, String cpf, String email, String contato, TipoUsuarioEnum tipoUsuario) throws IllegalArgumentException {
        Usuario.validaCamposObrigatorios(nome, cpf, email, tipoUsuario);
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);
        usuario.setNome(nome);
        if (TipoUsuarioEnum.CLIENTE.equals(tipoUsuario)) {
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setContato(contato);
        }
        usuario.setTipoUsuario(tipoUsuario);
        return usuario;
    }

    public static Usuario criarUsuarioDono(String donoRestaurante) {
        return criarUsuario(null, donoRestaurante, null, null, null, TipoUsuarioEnum.DONO_RESTAURANTE);
    }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco == null) {
            return;
        }
        this.enderecos.add(endereco);
    }

    private static void validaCamposObrigatorios(String nome, String cpf, String email, TipoUsuarioEnum tipoUsuario) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O campo 'nome' é obrigatório.");
        }
        if (TipoUsuarioEnum.CLIENTE.equals(tipoUsuario)) {
            if (cpf == null || cpf.isEmpty()) {
                throw new IllegalArgumentException("O campo 'cpf' é obrigatório.");
            }
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("O campo 'email' é obrigatório.");
            }
        }
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getContato() {
        return contato;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = CpfUtils.formatarCpf(cpf);
    }

    public void setEmail(String email) {
        EmailUtils.validaEmail(email);
        this.email = email;
    }

    public void setContato(String contato) {
        this.contato = ContatoUtils.formatarContato(contato);
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
