package com.lixo.gerenciamento.model.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class ParadaPontoColetaResponseDTO {
    private Long id;
    private Long pontoColetaId;
    private String pontoColetaNome;
    private List<TipoResiduo> tiposResiduo;
    private boolean coletado;

    public ParadaPontoColetaResponseDTO() {
        this.tiposResiduo = new ArrayList<>();
        this.coletado = false;
    }

    public ParadaPontoColetaResponseDTO(Long id, Long pontoColetaId, String pontoColetaNome, 
                                       List<TipoResiduo> tiposResiduo, boolean coletado) {
        this.id = id;
        this.pontoColetaId = pontoColetaId;
        this.pontoColetaNome = pontoColetaNome;
        this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new ArrayList<>();
        this.coletado = coletado;
    }

    public ParadaPontoColetaResponseDTO(Long id, Long pontoColetaId, String pontoColetaNome, 
                                       boolean coletado) {
        this(id, pontoColetaId, pontoColetaNome, new ArrayList<>(), coletado);
    }

    public ParadaPontoColetaResponseDTO(Long pontoColetaId, String pontoColetaNome) {
        this(null, pontoColetaId, pontoColetaNome, new ArrayList<>(), false);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPontoColetaId() {
        return pontoColetaId;
    }

    public void setPontoColetaId(Long pontoColetaId) {
        this.pontoColetaId = pontoColetaId;
    }

    public String getPontoColetaNome() {
        return pontoColetaNome;
    }

    public void setPontoColetaNome(String pontoColetaNome) {
        this.pontoColetaNome = pontoColetaNome;
    }

    public List<TipoResiduo> getTiposResiduo() {
        if (tiposResiduo == null) {
            tiposResiduo = new ArrayList<>();
        }
        return tiposResiduo;
    }

    public void setTiposResiduo(List<TipoResiduo> tiposResiduo) {
        this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParadaPontoColetaResponseDTO that = (ParadaPontoColetaResponseDTO) o;
        
        if (coletado != that.coletado) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pontoColetaId != null ? !pontoColetaId.equals(that.pontoColetaId) : that.pontoColetaId != null) return false;
        if (pontoColetaNome != null ? !pontoColetaNome.equals(that.pontoColetaNome) : that.pontoColetaNome != null) return false;
        return tiposResiduo != null ? tiposResiduo.equals(that.tiposResiduo) : that.tiposResiduo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pontoColetaId != null ? pontoColetaId.hashCode() : 0);
        result = 31 * result + (pontoColetaNome != null ? pontoColetaNome.hashCode() : 0);
        result = 31 * result + (tiposResiduo != null ? tiposResiduo.hashCode() : 0);
        result = 31 * result + (coletado ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParadaPontoColetaResponseDTO{" +
                "id=" + id +
                ", pontoColetaId=" + pontoColetaId +
                ", pontoColetaNome='" + pontoColetaNome + '\'' +
                ", tiposResiduo=" + tiposResiduo +
                ", coletado=" + coletado +
                '}';
    }

    // Métodos utilitários
    public void addTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            getTiposResiduo().add(tipoResiduo);
        }
    }

    public void removeTipoResiduo(TipoResiduo tipoResiduo) {
        if (tiposResiduo != null && tipoResiduo != null) {
            tiposResiduo.remove(tipoResiduo);
        }
    }

    public boolean hasTipoResiduo(TipoResiduo tipoResiduo) {
        return tiposResiduo != null && tiposResiduo.contains(tipoResiduo);
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

    public boolean aceitaTipoResiduo(TipoResiduo tipo) {
        if (tipo == null) {
            return false;
        }
        return hasTipoResiduo(tipo);
    }

    public boolean aceitaTodosTipos(List<TipoResiduo> tipos) {
        if (tipos == null || tipos.isEmpty()) {
            return false;
        }
        return tipos.stream().allMatch(this::aceitaTipoResiduo);
    }

    public boolean aceitaAlgumTipo(List<TipoResiduo> tipos) {
        if (tipos == null || tipos.isEmpty()) {
            return false;
        }
        return tipos.stream().anyMatch(this::aceitaTipoResiduo);
    }

    public static ParadaPontoColetaResponseDTOBuilder builder() {
        return new ParadaPontoColetaResponseDTOBuilder();
    }

    public static class ParadaPontoColetaResponseDTOBuilder {
        private Long id;
        private Long pontoColetaId;
        private String pontoColetaNome;
        private List<TipoResiduo> tiposResiduo;
        private boolean coletado;

        private ParadaPontoColetaResponseDTOBuilder() {
            this.tiposResiduo = new ArrayList<>();
            this.coletado = false;
        }

        public ParadaPontoColetaResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder pontoColetaId(Long pontoColetaId) {
            this.pontoColetaId = pontoColetaId;
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder pontoColetaNome(String pontoColetaNome) {
            this.pontoColetaNome = pontoColetaNome;
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder pontoColeta(Long id, String nome) {
            this.pontoColetaId = id;
            this.pontoColetaNome = nome;
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder tiposResiduo(List<TipoResiduo> tiposResiduo) {
            this.tiposResiduo = tiposResiduo != null ? tiposResiduo : new ArrayList<>();
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            if (this.tiposResiduo == null) {
                this.tiposResiduo = new ArrayList<>();
            }
            if (tipoResiduo != null) {
                this.tiposResiduo.add(tipoResiduo);
            }
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }

        public ParadaPontoColetaResponseDTOBuilder coletado() {
            this.coletado = true;
            return this;
        }

        public ParadaPontoColetaResponseDTO build() {
            if (pontoColetaId == null) {
                throw new IllegalArgumentException("ID do ponto de coleta não pode ser nulo");
            }
            if (pontoColetaNome == null || pontoColetaNome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do ponto de coleta não pode ser nulo ou vazio");
            }
            
            return new ParadaPontoColetaResponseDTO(id, pontoColetaId, pontoColetaNome, 
                                                  tiposResiduo, coletado);
        }
    }

}