package com.lixo.gerenciamento.model.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class PontoColetaResponseDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String responsavel;
    private String email;
    private String telefone;
    private Set<String> tiposResiduo;
    private boolean coletado;
    private String horarioFuncionamento;
    private Long idBairro;

    public PontoColetaResponseDTO() {
        this.tiposResiduo = new HashSet<>();
        this.coletado = false;
    }

    public PontoColetaResponseDTO(Long id, String nome, String endereco, String responsavel,
                                 String email, String telefone, Set<String> tiposResiduo,
                                 boolean coletado, String horarioFuncionamento, Long idBairro) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.email = email;
        this.telefone = telefone;
        this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new HashSet<>();
        this.coletado = coletado;
        this.horarioFuncionamento = horarioFuncionamento;
        this.idBairro = idBairro;
    }

    public PontoColetaResponseDTO(Long id, String nome, String endereco, Long idBairro) {
        this(id, nome, endereco, null, null, null, new HashSet<>(), false, null, idBairro);
    }

    public PontoColetaResponseDTO(Long id, String nome, String endereco, Set<String> tiposResiduo, Long idBairro) {
        this(id, nome, endereco, null, null, null, tiposResiduo, false, null, idBairro);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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

    public Set<String> getTiposResiduo() {
        if (tiposResiduo == null) {
            tiposResiduo = new HashSet<>();
        }
        return tiposResiduo;
    }

    public void setTiposResiduo(Set<String> tiposResiduo) {
        this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new HashSet<>();
    }

    public boolean isColetado() {
        return coletado;
    }

    public boolean getColetado() {
        return coletado;
    }

    public void setColetado(boolean coletado) {
        this.coletado = coletado;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public Long getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Long idBairro) {
        this.idBairro = idBairro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PontoColetaResponseDTO that = (PontoColetaResponseDTO) o;
        
        if (coletado != that.coletado) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (endereco != null ? !endereco.equals(that.endereco) : that.endereco != null) return false;
        if (responsavel != null ? !responsavel.equals(that.responsavel) : that.responsavel != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telefone != null ? !telefone.equals(that.telefone) : that.telefone != null) return false;
        if (tiposResiduo != null ? !tiposResiduo.equals(that.tiposResiduo) : that.tiposResiduo != null) return false;
        if (horarioFuncionamento != null ? !horarioFuncionamento.equals(that.horarioFuncionamento) : that.horarioFuncionamento != null) return false;
        return idBairro != null ? idBairro.equals(that.idBairro) : that.idBairro == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (responsavel != null ? responsavel.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (tiposResiduo != null ? tiposResiduo.hashCode() : 0);
        result = 31 * result + (coletado ? 1 : 0);
        result = 31 * result + (horarioFuncionamento != null ? horarioFuncionamento.hashCode() : 0);
        result = 31 * result + (idBairro != null ? idBairro.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PontoColetaResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tiposResiduo=" + tiposResiduo +
                ", coletado=" + coletado +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", idBairro=" + idBairro +
                '}';
    }

    public void addTipoResiduo(String tipoResiduo) {
        if (tipoResiduo != null && !tipoResiduo.trim().isEmpty()) {
            getTiposResiduo().add(tipoResiduo.trim());
        }
    }

    public void addTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            addTipoResiduo(tipoResiduo.name());
        }
    }

    public void removeTipoResiduo(String tipoResiduo) {
        if (tiposResiduo != null && tipoResiduo != null) {
            tiposResiduo.remove(tipoResiduo);
        }
    }

    public void removeTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            removeTipoResiduo(tipoResiduo.name());
        }
    }

    public boolean hasTipoResiduo(String tipoResiduo) {
        return tiposResiduo != null && tiposResiduo.contains(tipoResiduo);
    }

    public boolean hasTipoResiduo(TipoResiduo tipoResiduo) {
        return tipoResiduo != null && hasTipoResiduo(tipoResiduo.name());
    }

    public boolean hasTiposResiduo() {
        return tiposResiduo != null && !tiposResiduo.isEmpty();
    }

    public void marcarComoColetado() {
        this.coletado = true;
    }

    public void marcarComoNaoColetado() {
        this.coletado = false;
    }

    public boolean aceitaTipoResiduo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        return hasTipoResiduo(tipo.trim());
    }

    public boolean aceitaTipoResiduo(TipoResiduo tipo) {
        return tipo != null && aceitaTipoResiduo(tipo.name());
    }

    public boolean aceitaTodosTipos(Set<String> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposResiduo()) {
            return false;
        }
        return tipos.stream().allMatch(this::aceitaTipoResiduo);
    }

    public boolean aceitaAlgumTipo(Set<String> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposResiduo()) {
            return false;
        }
        return tipos.stream().anyMatch(this::aceitaTipoResiduo);
    }

    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() &&
               endereco != null && !endereco.trim().isEmpty() &&
               idBairro != null;
    }

    public boolean hasInformacoesContato() {
        return (responsavel != null && !responsavel.trim().isEmpty()) ||
               (email != null && !email.trim().isEmpty()) ||
               (telefone != null && !telefone.trim().isEmpty());
    }

    public static PontoColetaResponseDTOBuilder builder() {
        return new PontoColetaResponseDTOBuilder();
    }

    public static class PontoColetaResponseDTOBuilder {
        private Long id;
        private String nome;
        private String endereco;
        private String responsavel;
        private String email;
        private String telefone;
        private Set<String> tiposResiduo;
        private boolean coletado;
        private String horarioFuncionamento;
        private Long idBairro;

        private PontoColetaResponseDTOBuilder() {
            this.tiposResiduo = new HashSet<>();
            this.coletado = false;
        }

        public PontoColetaResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PontoColetaResponseDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public PontoColetaResponseDTOBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public PontoColetaResponseDTOBuilder responsavel(String responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        public PontoColetaResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PontoColetaResponseDTOBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public PontoColetaResponseDTOBuilder tiposResiduo(Set<String> tiposResiduo) {
            this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new HashSet<>();
            return this;
        }

        public PontoColetaResponseDTOBuilder tipoResiduo(String tipoResiduo) {
            if (this.tiposResiduo == null) {
                this.tiposResiduo = new HashSet<>();
            }
            if (tipoResiduo != null && !tipoResiduo.trim().isEmpty()) {
                this.tiposResiduo.add(tipoResiduo.trim());
            }
            return this;
        }

        public PontoColetaResponseDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            if (tipoResiduo != null) {
                tipoResiduo(tipoResiduo.name());
            }
            return this;
        }

        public PontoColetaResponseDTOBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }

        public PontoColetaResponseDTOBuilder coletado() {
            this.coletado = true;
            return this;
        }

        public PontoColetaResponseDTOBuilder horarioFuncionamento(String horarioFuncionamento) {
            this.horarioFuncionamento = horarioFuncionamento;
            return this;
        }

        public PontoColetaResponseDTOBuilder idBairro(Long idBairro) {
            this.idBairro = idBairro;
            return this;
        }

        public PontoColetaResponseDTO build() {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do ponto de coleta não pode ser nulo ou vazio");
            }
            if (endereco == null || endereco.trim().isEmpty()) {
                throw new IllegalArgumentException("Endereço do ponto de coleta não pode ser nulo ou vazio");
            }
            if (idBairro == null) {
                throw new IllegalArgumentException("ID do bairro não pode ser nulo");
            }
            
            if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
                throw new IllegalArgumentException("Email do ponto de coleta é inválido");
            }
            
            return new PontoColetaResponseDTO(id, nome, endereco, responsavel, email, telefone,
                                             tiposResiduo, coletado, horarioFuncionamento, idBairro);
        }
        
        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email != null && email.matches(emailRegex);
        }
    }

}
