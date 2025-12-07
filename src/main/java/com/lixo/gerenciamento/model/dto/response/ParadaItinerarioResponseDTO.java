package com.lixo.gerenciamento.model.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParadaItinerarioResponseDTO {
    private int ordem;
    private Long bairroId;
    private String bairroNome;
    private List<PontoColetaResponseDTO> pontosColeta;
    private boolean coletado;
    private LocalDateTime horaColeta;

    public ParadaItinerarioResponseDTO() {
        this.pontosColeta = new ArrayList<>();
        this.coletado = false; 
        this.ordem = 0;
    }

    public ParadaItinerarioResponseDTO(int ordem, Long bairroId, String bairroNome, 
                                      List<PontoColetaResponseDTO> pontosColeta, 
                                      boolean coletado, LocalDateTime horaColeta) {
        this.ordem = ordem;
        this.bairroId = bairroId;
        this.bairroNome = bairroNome;
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
        this.coletado = coletado;
        this.horaColeta = horaColeta;
    }

    public ParadaItinerarioResponseDTO(int ordem, Long bairroId, String bairroNome, 
                                      boolean coletado, LocalDateTime horaColeta) {
        this(ordem, bairroId, bairroNome, new ArrayList<>(), coletado, horaColeta);
    }

    public ParadaItinerarioResponseDTO(int ordem, Long bairroId, String bairroNome) {
        this(ordem, bairroId, bairroNome, new ArrayList<>(), false, null);
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Long getBairroId() {
        return bairroId;
    }

    public void setBairroId(Long bairroId) {
        this.bairroId = bairroId;
    }

    public String getBairroNome() {
        return bairroNome;
    }

    public void setBairroNome(String bairroNome) {
        this.bairroNome = bairroNome;
    }

    public List<PontoColetaResponseDTO> getPontosColeta() {
        if (pontosColeta == null) {
            pontosColeta = new ArrayList<>();
        }
        return pontosColeta;
    }

    public void setPontosColeta(List<PontoColetaResponseDTO> pontosColeta) {
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
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

    public LocalDateTime getHoraColeta() {
        return horaColeta;
    }

    public void setHoraColeta(LocalDateTime horaColeta) {
        this.horaColeta = horaColeta;
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParadaItinerarioResponseDTO that = (ParadaItinerarioResponseDTO) o;
        
        if (ordem != that.ordem) return false;
        if (coletado != that.coletado) return false;
        if (bairroId != null ? !bairroId.equals(that.bairroId) : that.bairroId != null) return false;
        if (bairroNome != null ? !bairroNome.equals(that.bairroNome) : that.bairroNome != null) return false;
        if (pontosColeta != null ? !pontosColeta.equals(that.pontosColeta) : that.pontosColeta != null) return false;
        return horaColeta != null ? horaColeta.equals(that.horaColeta) : that.horaColeta == null;
    }

    @Override
    public int hashCode() {
        int result = ordem;
        result = 31 * result + (bairroId != null ? bairroId.hashCode() : 0);
        result = 31 * result + (bairroNome != null ? bairroNome.hashCode() : 0);
        result = 31 * result + (pontosColeta != null ? pontosColeta.hashCode() : 0);
        result = 31 * result + (coletado ? 1 : 0);
        result = 31 * result + (horaColeta != null ? horaColeta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParadaItinerarioResponseDTO{" +
                "ordem=" + ordem +
                ", bairroId=" + bairroId +
                ", bairroNome='" + bairroNome + '\'' +
                ", pontosColeta=" + (pontosColeta != null ? pontosColeta.size() : 0) + " itens" +
                ", coletado=" + coletado +
                ", horaColeta=" + horaColeta +
                '}';
    }

    public void addPontoColeta(PontoColetaResponseDTO pontoColeta) {
        if (pontoColeta != null) {
            getPontosColeta().add(pontoColeta);
        }
    }

    public void removePontoColeta(PontoColetaResponseDTO pontoColeta) {
        if (pontosColeta != null && pontoColeta != null) {
            pontosColeta.remove(pontoColeta);
        }
    }

    public boolean hasPontosColeta() {
        return pontosColeta != null && !pontosColeta.isEmpty();
    }

    public void marcarComoColetado() {
        this.coletado = true;
        this.horaColeta = LocalDateTime.now();
    }

    public void marcarComoNaoColetado() {
        this.coletado = false;
        this.horaColeta = null;
    }

    public boolean todosPontosColetados() {
        if (!hasPontosColeta()) {
            return false;
        }
        return pontosColeta.stream().allMatch(PontoColetaResponseDTO::isColetado);
    }

    public boolean algumPontoColetado() {
        if (!hasPontosColeta()) {
            return false;
        }
        return pontosColeta.stream().anyMatch(PontoColetaResponseDTO::isColetado);
    }

    public int quantidadePontosColetados() {
        if (!hasPontosColeta()) {
            return 0;
        }
        return (int) pontosColeta.stream()
                .filter(PontoColetaResponseDTO::isColetado)
                .count();
    }

    // Builder Pattern
    public static ParadaItinerarioResponseDTOBuilder builder() {
        return new ParadaItinerarioResponseDTOBuilder();
    }

    public static class ParadaItinerarioResponseDTOBuilder {
        private int ordem;
        private Long bairroId;
        private String bairroNome;
        private List<PontoColetaResponseDTO> pontosColeta;
        private boolean coletado;
        private LocalDateTime horaColeta;

        private ParadaItinerarioResponseDTOBuilder() {
            this.pontosColeta = new ArrayList<>();
            this.coletado = false;
            this.ordem = 0;
        }

        public ParadaItinerarioResponseDTOBuilder ordem(int ordem) {
            this.ordem = ordem;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder bairroId(Long bairroId) {
            this.bairroId = bairroId;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder bairroNome(String bairroNome) {
            this.bairroNome = bairroNome;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder bairro(Long id, String nome) {
            this.bairroId = id;
            this.bairroNome = nome;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder pontosColeta(List<PontoColetaResponseDTO> pontosColeta) {
            this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder pontoColeta(PontoColetaResponseDTO pontoColeta) {
            if (this.pontosColeta == null) {
                this.pontosColeta = new ArrayList<>();
            }
            if (pontoColeta != null) {
                this.pontosColeta.add(pontoColeta);
            }
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder coletado(boolean coletado) {
            this.coletado = coletado;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder coletado() {
            this.coletado = true;
            this.horaColeta = LocalDateTime.now();
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder horaColeta(LocalDateTime horaColeta) {
            this.horaColeta = horaColeta;
            return this;
        }

        public ParadaItinerarioResponseDTOBuilder horaColeta() {
            this.horaColeta = LocalDateTime.now();
            return this;
        }

        public ParadaItinerarioResponseDTO build() {
            if (ordem < 0) {
                throw new IllegalArgumentException("Ordem não pode ser negativa");
            }
            if (bairroId == null) {
                throw new IllegalArgumentException("ID do bairro não pode ser nulo");
            }
            if (bairroNome == null || bairroNome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do bairro não pode ser nulo ou vazio");
            }
            
            if (coletado && horaColeta == null) {
                horaColeta = LocalDateTime.now();
            }
            
            if (!coletado && horaColeta != null) {
                throw new IllegalArgumentException("Parada não coletada não pode ter hora de coleta");
            }
            
            return new ParadaItinerarioResponseDTO(ordem, bairroId, bairroNome, 
                                                  pontosColeta, coletado, horaColeta);
        }
    }
}
