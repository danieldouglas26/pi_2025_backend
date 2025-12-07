package com.lixo.gerenciamento.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.interfaces.Builder;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pontocoleta")
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairroid", nullable = false)
    private Bairro bairro;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(name = "nome_responsavel", nullable = false) 
    private String nomeResponsavel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @Column(name = "horario_funcionamento") 
    private String horarioFuncionamento; 

    @Column(name = "tipos_residuo_aceitos") 
    private String tiposResiduoAceitosLegacy; 

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pontocoletatiporesiduo", joinColumns = @JoinColumn(name = "pontocoletaid"))
    @Column(name = "tiporesiduo", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoResiduo> tiposDeResiduo;

    public PontoColeta() {
    }

    public PontoColeta(Long id, Bairro bairro, String nome, String nomeResponsavel, String email, 
                      String telefone, String endereco, String horarioFuncionamento, 
                      String tiposResiduoAceitosLegacy, List<TipoResiduo> tiposDeResiduo) {
        this.id = id;
        this.bairro = bairro;
        this.nome = nome;
        this.nomeResponsavel = nomeResponsavel;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.tiposResiduoAceitosLegacy = tiposResiduoAceitosLegacy;
        this.tiposDeResiduo = tiposDeResiduo;
    }

    private PontoColeta(PontoColetaBuilder builder) {
        this.id = builder.id;
        this.bairro = builder.bairro;
        this.nome = builder.nome;
        this.nomeResponsavel = builder.nomeResponsavel;
        this.email = builder.email;
        this.telefone = builder.telefone;
        this.endereco = builder.endereco;
        this.horarioFuncionamento = builder.horarioFuncionamento;
        this.tiposResiduoAceitosLegacy = builder.tiposResiduoAceitosLegacy;
        this.tiposDeResiduo = builder.tiposDeResiduo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getTiposResiduoAceitosLegacy() {
        return tiposResiduoAceitosLegacy;
    }

    public void setTiposResiduoAceitosLegacy(String tiposResiduoAceitosLegacy) {
        this.tiposResiduoAceitosLegacy = tiposResiduoAceitosLegacy;
    }

    public List<TipoResiduo> getTiposDeResiduo() {
        if (tiposDeResiduo == null) {
            tiposDeResiduo = new ArrayList<>();
        }
        return tiposDeResiduo;
    }

    public void setTiposDeResiduo(List<TipoResiduo> tiposDeResiduo) {
        this.tiposDeResiduo = tiposDeResiduo;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PontoColeta that = (PontoColeta) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "PontoColeta{" +
                "id=" + id +
                ", bairro=" + (bairro != null ? bairro.getId() : "null") +
                ", nome='" + nome + '\'' +
                ", nomeResponsavel='" + nomeResponsavel + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", tiposResiduoAceitosLegacy='" + tiposResiduoAceitosLegacy + '\'' +
                ", tiposDeResiduo=" + tiposDeResiduo +
                '}';
    }

    public static PontoColetaBuilder builder() {
        return new PontoColetaBuilder();
    }
    
    public static class PontoColetaBuilder implements Builder<PontoColeta> {
        protected Long id;
        protected Bairro bairro;
        protected String nome;
        protected String nomeResponsavel;
        protected String email;
        protected String telefone;
        protected String endereco;
        protected String horarioFuncionamento;
        protected String tiposResiduoAceitosLegacy;
        protected List<TipoResiduo> tiposDeResiduo;
        
        public PontoColetaBuilder() {
        }
        
        public PontoColetaBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public PontoColetaBuilder bairro(Bairro bairro) {
            this.bairro = bairro;
            return this;
        }
        
        public PontoColetaBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }
        
        public PontoColetaBuilder nomeResponsavel(String nomeResponsavel) {
            this.nomeResponsavel = nomeResponsavel;
            return this;
        }
        
        public PontoColetaBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public PontoColetaBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }
        
        public PontoColetaBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }
        
        public PontoColetaBuilder horarioFuncionamento(String horarioFuncionamento) {
            this.horarioFuncionamento = horarioFuncionamento;
            return this;
        }
        
        public PontoColetaBuilder tiposResiduoAceitosLegacy(String tiposResiduoAceitosLegacy) {
            this.tiposResiduoAceitosLegacy = tiposResiduoAceitosLegacy;
            return this;
        }
        
        public PontoColetaBuilder tiposDeResiduo(List<TipoResiduo> tiposDeResiduo) {
            this.tiposDeResiduo = tiposDeResiduo;
            return this;
        }
        
        public PontoColetaBuilder tipoDeResiduo(TipoResiduo tipoResiduo) {
            if (this.tiposDeResiduo == null) {
                this.tiposDeResiduo = new ArrayList<>();
            }
            this.tiposDeResiduo.add(tipoResiduo);
            return this;
        }
        
        @Override
        public PontoColeta build() {
            if (bairro == null) {
                throw new IllegalArgumentException("Bairro não pode ser nulo");
            }
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
            }
            if (nomeResponsavel == null || nomeResponsavel.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do responsável não pode ser nulo ou vazio");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
            }
            if (telefone == null || telefone.trim().isEmpty()) {
                throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
            }
            if (endereco == null || endereco.trim().isEmpty()) {
                throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
            }
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email inválido");
            }
            
            return new PontoColeta(this);
        }
        
        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email.matches(emailRegex);
        }
    }
}

