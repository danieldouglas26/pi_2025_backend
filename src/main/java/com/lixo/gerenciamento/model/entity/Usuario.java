package com.lixo.gerenciamento.model.entity;



import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String senha;
    
    @Column(nullable = false)
    private String nome;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
    
    @Column(name = "ativo")
    private boolean ativo = true;
    
    // Construtores
    public Usuario() {}
    
    public Usuario(Long id, String email, String senha, String nome, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.roles = roles != null ? roles : new HashSet<>();
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Builder
    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }
    
    public static class UsuarioBuilder {
        private Long id;
        private String email;
        private String senha;
        private String nome;
        private Set<Role> roles;
        
        public UsuarioBuilder id(Long id) { this.id = id; return this; }
        public UsuarioBuilder email(String email) { this.email = email; return this; }
        public UsuarioBuilder senha(String senha) { this.senha = senha; return this; }
        public UsuarioBuilder nome(String nome) { this.nome = nome; return this; }
        public UsuarioBuilder roles(Set<Role> roles) { this.roles = roles; return this; }
        
        public Usuario build() {
            return new Usuario(id, email, senha, nome, roles);
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
    
    public void adicionarRole(Role role) {
        this.roles.add(role);
    }
    
    public void removerRole(Role role) {
        this.roles.remove(role);
    }
    
    public boolean temRole(Role role) {
        return this.roles.contains(role);
    }
}