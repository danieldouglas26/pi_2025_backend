package com.lixo.gerenciamento.model.enuns;

public enum Role {
    ROLE_ADMIN("Administrador"),
    ROLE_GERENTE("Gerente"),
    ROLE_COLETOR("Coletor"),
    ROLE_VISITANTE("Visitante");
    
    private final String descricao;
    
    Role(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
