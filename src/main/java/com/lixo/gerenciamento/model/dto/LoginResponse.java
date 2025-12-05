package com.lixo.gerenciamento.model.dto;

import java.util.Set;

public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String email;
    private String nome;
    private Set<String> roles;
    private Long expiraEm;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, Long id, String email, String nome, Set<String> roles, Long expiraEm) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.roles = roles;
        this.expiraEm = expiraEm;
    }
    
    // Getters e Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    
    public Long getExpiraEm() { return expiraEm; }
    public void setExpiraEm(Long expiraEm) { this.expiraEm = expiraEm; }
}