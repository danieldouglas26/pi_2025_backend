package com.lixo.gerenciamento.model.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class ItinerarioResponseDTO {

    private Long id;
    private Long rotaId;
    private String rotaNome;
    private Long caminhaoId;
    private String caminhaoPlaca;
    private String motorista;
    private LocalDate data;
    private Double distanciaTotal;
    private TipoResiduo tipoResiduo;
    private boolean concluido;
    private List<ParadaItinerarioResponseDTO> paradas;

    public ItinerarioResponseDTO() {
    }

    public ItinerarioResponseDTO(Long id, Long rotaId, String rotaNome, Long caminhaoId, 
                                String caminhaoPlaca, String motorista, LocalDate data, 
                                Double distanciaTotal, TipoResiduo tipoResiduo, 
                                boolean concluido, List<ParadaItinerarioResponseDTO> paradas) {
        this.id = id;
        this.rotaId = rotaId;
        this.rotaNome = rotaNome;
        this.caminhaoId = caminhaoId;
        this.caminhaoPlaca = caminhaoPlaca;
        this.motorista = motorista;
        this.data = data != null ? data : LocalDate.now();
        this.distanciaTotal = distanciaTotal;
        this.tipoResiduo = tipoResiduo;
        this.concluido = concluido;
        this.paradas = paradas != null ? paradas : new ArrayList<>();
    }

    // Construtor simplificado (sem listas e com valores padrão)
    public ItinerarioResponseDTO(Long id, Long rotaId, String rotaNome, Long caminhaoId, 
                                String caminhaoPlaca, String motorista, LocalDate data, 
                                Double distanciaTotal, TipoResiduo tipoResiduo) {
        this(id, rotaId, rotaNome, caminhaoId, caminhaoPlaca, motorista, data, 
             distanciaTotal, tipoResiduo, false, new ArrayList<>());
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRotaId() {
        return rotaId;
    }

    public void setRotaId(Long rotaId) {
        this.rotaId = rotaId;
    }

    public String getRotaNome() {
        return rotaNome;
    }

    public void setRotaNome(String rotaNome) {
        this.rotaNome = rotaNome;
    }

    public Long getCaminhaoId() {
        return caminhaoId;
    }

    public void setCaminhaoId(Long caminhaoId) {
        this.caminhaoId = caminhaoId;
    }

    public String getCaminhaoPlaca() {
        return caminhaoPlaca;
    }

    public void setCaminhaoPlaca(String caminhaoPlaca) {
        this.caminhaoPlaca = caminhaoPlaca;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data != null ? data : LocalDate.now();
    }

    public Double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(Double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public List<ParadaItinerarioResponseDTO> getParadas() {
        if (paradas == null) {
            paradas = new ArrayList<>();
        }
        return paradas;
    }

    public void setParadas(List<ParadaItinerarioResponseDTO> paradas) {
        this.paradas = paradas != null ? paradas : new ArrayList<>();
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ItinerarioResponseDTO that = (ItinerarioResponseDTO) o;
        
        if (concluido != that.concluido) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (rotaId != null ? !rotaId.equals(that.rotaId) : that.rotaId != null) return false;
        if (rotaNome != null ? !rotaNome.equals(that.rotaNome) : that.rotaNome != null) return false;
        if (caminhaoId != null ? !caminhaoId.equals(that.caminhaoId) : that.caminhaoId != null) return false;
        if (caminhaoPlaca != null ? !caminhaoPlaca.equals(that.caminhaoPlaca) : that.caminhaoPlaca != null) return false;
        if (motorista != null ? !motorista.equals(that.motorista) : that.motorista != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (distanciaTotal != null ? !distanciaTotal.equals(that.distanciaTotal) : that.distanciaTotal != null) return false;
        if (tipoResiduo != that.tipoResiduo) return false;
        return paradas != null ? paradas.equals(that.paradas) : that.paradas == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rotaId != null ? rotaId.hashCode() : 0);
        result = 31 * result + (rotaNome != null ? rotaNome.hashCode() : 0);
        result = 31 * result + (caminhaoId != null ? caminhaoId.hashCode() : 0);
        result = 31 * result + (caminhaoPlaca != null ? caminhaoPlaca.hashCode() : 0);
        result = 31 * result + (motorista != null ? motorista.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (distanciaTotal != null ? distanciaTotal.hashCode() : 0);
        result = 31 * result + (tipoResiduo != null ? tipoResiduo.hashCode() : 0);
        result = 31 * result + (concluido ? 1 : 0);
        result = 31 * result + (paradas != null ? paradas.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "ItinerarioResponseDTO{" +
                "id=" + id +
                ", rotaId=" + rotaId +
                ", rotaNome='" + rotaNome + '\'' +
                ", caminhaoId=" + caminhaoId +
                ", caminhaoPlaca='" + caminhaoPlaca + '\'' +
                ", motorista='" + motorista + '\'' +
                ", data=" + data +
                ", distanciaTotal=" + distanciaTotal +
                ", tipoResiduo=" + tipoResiduo +
                ", concluido=" + concluido +
                ", paradas=" + (paradas != null ? paradas.size() : 0) + " itens" +
                '}';
    }

    // Métodos utilitários
    public void addParada(ParadaItinerarioResponseDTO parada) {
        if (parada != null) {
            getParadas().add(parada);
        }
    }

    public void removeParada(ParadaItinerarioResponseDTO parada) {
        if (paradas != null && parada != null) {
            paradas.remove(parada);
        }
    }

    public boolean hasParadas() {
        return paradas != null && !paradas.isEmpty();
    }

    public boolean isDataFutura() {
        return data != null && data.isAfter(LocalDate.now());
    }

    public boolean isDataPassada() {
        return data != null && data.isBefore(LocalDate.now());
    }

    public boolean isDataHoje() {
        return data != null && data.isEqual(LocalDate.now());
    }

    public void marcarComoConcluido() {
        this.concluido = true;
    }

    public void marcarComoPendente() {
        this.concluido = false;
    }


    public static ItinerarioResponseDTOBuilder builder() {
        return new ItinerarioResponseDTOBuilder();
    }

    public static class ItinerarioResponseDTOBuilder {
        private Long id;
        private Long rotaId;
        private String rotaNome;
        private Long caminhaoId;
        private String caminhaoPlaca;
        private String motorista;
        private LocalDate data;
        private Double distanciaTotal;
        private TipoResiduo tipoResiduo;
        private boolean concluido;
        private List<ParadaItinerarioResponseDTO> paradas;

        private ItinerarioResponseDTOBuilder() {
            this.paradas = new ArrayList<>();
            this.concluido = false;
            this.data = LocalDate.now();
        }

        public ItinerarioResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ItinerarioResponseDTOBuilder rotaId(Long rotaId) {
            this.rotaId = rotaId;
            return this;
        }

        public ItinerarioResponseDTOBuilder rotaNome(String rotaNome) {
            this.rotaNome = rotaNome;
            return this;
        }

        public ItinerarioResponseDTOBuilder caminhaoId(Long caminhaoId) {
            this.caminhaoId = caminhaoId;
            return this;
        }

        public ItinerarioResponseDTOBuilder caminhaoPlaca(String caminhaoPlaca) {
            this.caminhaoPlaca = caminhaoPlaca;
            return this;
        }

        public ItinerarioResponseDTOBuilder motorista(String motorista) {
            this.motorista = motorista;
            return this;
        }

        public ItinerarioResponseDTOBuilder data(LocalDate data) {
            this.data = data;
            return this;
        }

        public ItinerarioResponseDTOBuilder data(int ano, int mes, int dia) {
            this.data = LocalDate.of(ano, mes, dia);
            return this;
        }

        public ItinerarioResponseDTOBuilder distanciaTotal(Double distanciaTotal) {
            this.distanciaTotal = distanciaTotal;
            return this;
        }

        public ItinerarioResponseDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            this.tipoResiduo = tipoResiduo;
            return this;
        }

        public ItinerarioResponseDTOBuilder concluido(boolean concluido) {
            this.concluido = concluido;
            return this;
        }

        public ItinerarioResponseDTOBuilder concluido() {
            this.concluido = true;
            return this;
        }

        public ItinerarioResponseDTOBuilder paradas(List<ParadaItinerarioResponseDTO> paradas) {
            this.paradas = paradas != null ? paradas : new ArrayList<>();
            return this;
        }

        public ItinerarioResponseDTOBuilder parada(ParadaItinerarioResponseDTO parada) {
            if (this.paradas == null) {
                this.paradas = new ArrayList<>();
            }
            if (parada != null) {
                this.paradas.add(parada);
            }
            return this;
        }

        public ItinerarioResponseDTO build() {
            if (rotaId == null) {
                throw new IllegalArgumentException("ID da rota não pode ser nulo");
            }
            if (rotaNome == null || rotaNome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome da rota não pode ser nulo ou vazio");
            }
            if (caminhaoId == null) {
                throw new IllegalArgumentException("ID do caminhão não pode ser nulo");
            }
            if (caminhaoPlaca == null || caminhaoPlaca.trim().isEmpty()) {
                throw new IllegalArgumentException("Placa do caminhão não pode ser nula ou vazia");
            }
            if (distanciaTotal != null && distanciaTotal < 0) {
                throw new IllegalArgumentException("Distância total não pode ser negativa");
            }
            if (data != null && data.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Data do itinerário não pode ser no passado");
            }
            
            return new ItinerarioResponseDTO(id, rotaId, rotaNome, caminhaoId, caminhaoPlaca, 
                                           motorista, data, distanciaTotal, tipoResiduo, 
                                           concluido, paradas);
        }
    }
}