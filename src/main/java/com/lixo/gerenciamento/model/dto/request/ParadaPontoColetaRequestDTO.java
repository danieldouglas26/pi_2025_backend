package com.lixo.gerenciamento.model.dto.request;

public class ParadaPontoColetaRequestDTO {
    private Long pontoColetaId;
    private boolean coletado;

    public ParadaPontoColetaRequestDTO() {
        this.coletado = false;
    }

    public ParadaPontoColetaRequestDTO(Long pontoColetaId, boolean coletado) {
        this.pontoColetaId = pontoColetaId;
        this.coletado = coletado;
    }

    public ParadaPontoColetaRequestDTO(Long pontoColetaId) {
        this(pontoColetaId, false);
    }

    // Getters e Setters
    public Long getPontoColetaId() {
        return pontoColetaId;
    }

    public void setPontoColetaId(Long pontoColetaId) {
        this.pontoColetaId = pontoColetaId;
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
        
        ParadaPontoColetaRequestDTO that = (ParadaPontoColetaRequestDTO) o;
        
        if (coletado != that.coletado) return false;
        return pontoColetaId != null ? pontoColetaId.equals(that.pontoColetaId) : that.pontoColetaId == null;
    }

    @Override
    public int hashCode() {
        int result = pontoColetaId != null ? pontoColetaId.hashCode() : 0;
        result = 31 * result + (coletado ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParadaPontoColetaRequestDTO{" +
                "pontoColetaId=" + pontoColetaId +
                ", coletado=" + coletado +
                '}';
    }

    public boolean isValid() {
        return pontoColetaId != null;
    }

    public void marcarComoColetado() {
        this.coletado = true;
    }

    public void marcarComoNaoColetado() {
        this.coletado = false;
    }

    public boolean isColetaRealizada() {
        return coletado;
    }

    public boolean isPendente() {
        return !coletado;
    }

    public String getStatusColeta() {
        return coletado ? "COLETADO" : "PENDENTE";
    }

    public void toggleColeta() {
        this.coletado = !this.coletado;
    }

    public static ParadaPontoColetaRequestDTO coletado(Long pontoColetaId) {
        return new ParadaPontoColetaRequestDTO(pontoColetaId, true);
    }

    public static ParadaPontoColetaRequestDTO pendente(Long pontoColetaId) {
        return new ParadaPontoColetaRequestDTO(pontoColetaId, false);
    }

    public static ParadaPontoColetaRequestDTOBuilder builder() {
        return new ParadaPontoColetaRequestDTOBuilder();
    }

    public static class ParadaPontoColetaRequestDTOBuilder {
        private Long pontoColetaId;
        private boolean coletado;

        private ParadaPontoColetaRequestDTOBuilder() {
            this.coletado = false;
        }

        public ParadaPontoColetaRequestDTOBuilder pontoColetaId(Long pontoColetaId) {
            this.pontoColetaId = pontoColetaId;
            return this;
        }

        public ParadaPontoColetaRequestDTOBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }

        public ParadaPontoColetaRequestDTOBuilder coletado() {
            this.coletado = true;
            return this;
        }

        public ParadaPontoColetaRequestDTOBuilder pendente() {
            this.coletado = false;
            return this;
        }

        public ParadaPontoColetaRequestDTO build() {
            if (pontoColetaId == null) {
                throw new IllegalArgumentException("ID do ponto de coleta não pode ser nulo");
            }
            
            return new ParadaPontoColetaRequestDTO(pontoColetaId, coletado);
        }
    }
}
