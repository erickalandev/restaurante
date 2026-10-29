package com.tech.challenge.fiap.gestao.restaurante.enums;

public enum TipoCozinhaEnum {
    ITALIANA("Italiana"),
    CHINESA("Chinesa"),
    JAPONESA("Japonesa"),
    MEXICANA("Mexicana"),
    BRASILEIRA("Brasileira"),
    INDIANA("Indiana"),
    FRANCESA("Francesa"),
    AMERICANA("Americana"),
    COREANA("Coreana");

    private final String descricao;

    TipoCozinhaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static TipoCozinhaEnum getValue(String value) {
        for (TipoCozinhaEnum tipo : TipoCozinhaEnum.values()) {
            if (tipo.descricao.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor inválido para tipo de Cozinha: " + value);
    }

    public String getDescricao() {
        return descricao;
    }
}
