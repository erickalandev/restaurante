package com.tech.challenge.fiap.gestao.restaurante.entities;

import com.tech.challenge.fiap.gestao.restaurante.enums.TipoUsuarioEnum;
import com.tech.challenge.fiap.gestao.restaurante.utils.ContatoUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.CpfUtils;
import com.tech.challenge.fiap.gestao.restaurante.utils.EmailUtils;

import java.util.Set;

public class Usuario {
    private Long usuarioId;
    private String nome;
    private String cpf;
    private String email;
    private String contato;
    private Set<Endereco> enderecos;
    private TipoUsuarioEnum tipoUsuario;

    public static Usuario criarUsuario(String nome, String cpf, String email, String contato) throws IllegalArgumentException {
        Usuario.validaCamposObrigatorios(nome, cpf, email);
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setContato(contato);
        usuario.setTipoUsuario(TipoUsuarioEnum.CLIENTE);
        return usuario;
    }

    public static Usuario atualizarUsuario(Long usuarioId, String nome, String cpf, String email, String contato) throws IllegalArgumentException {
        Usuario usuario = Usuario.criarUsuario(nome, cpf, email, contato);
        usuario.setUsuarioId(usuarioId);
        return usuario;
    }

    public static Usuario criarUsuarioDono(String donoRestaurante) {
        Usuario usuario = new Usuario();
        usuario.setNome(donoRestaurante);
        usuario.setTipoUsuario(TipoUsuarioEnum.DONO_RESTAURANTE);
        return usuario;
    }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco == null) {
            return;
        }
        this.enderecos.add(endereco);
    }

    private static void validaCamposObrigatorios(String nome, String cpf, String email) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O campo 'nome' é obrigatório.");
        }
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O campo 'cpf' é obrigatório.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O campo 'email' é obrigatório.");
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
