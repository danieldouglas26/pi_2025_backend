package com.lixo.gerenciamento.model.dto.request;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotNull;

public class ItinerarioRequestDTO {

    @NotNull(message = "ID da rota é obrigatório")
    private Long rotaId;

    @NotNull(message = "ID do caminhão é obrigatório")
    private Long caminhaoId;

    @NotNull(message = "Data do itinerário é obrigatória")
    private LocalDate data;

    // Construtor padrão (no-args)
    public ItinerarioRequestDTO() {
        // Data padrão é hoje
        this.data = LocalDate.now();
    }

    // Construtor com todos os campos
    public ItinerarioRequestDTO(Long rotaId, Long caminhaoId, LocalDate data) {
        this.rotaId = rotaId;
        this.caminhaoId = caminhaoId;
        this.data = data != null ? data : LocalDate.now();
    }

    // Construtor com data padrão (hoje)
    public ItinerarioRequestDTO(Long rotaId, Long caminhaoId) {
        this(rotaId, caminhaoId, LocalDate.now());
    }

    // Getters e Setters
    public Long getRotaId() {
        return rotaId;
    }

    public void setRotaId(Long rotaId) {
        this.rotaId = rotaId;
    }

    public Long getCaminhaoId() {
        return caminhaoId;
    }

    public void setCaminhaoId(Long caminhaoId) {
        this.caminhaoId = caminhaoId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data != null ? data : LocalDate.now();
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ItinerarioRequestDTO that = (ItinerarioRequestDTO) o;
        
        if (rotaId != null ? !rotaId.equals(that.rotaId) : that.rotaId != null) return false;
        if (caminhaoId != null ? !caminhaoId.equals(that.caminhaoId) : that.caminhaoId != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = rotaId != null ? rotaId.hashCode() : 0;
        result = 31 * result + (caminhaoId != null ? caminhaoId.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "ItinerarioRequestDTO{" +
                "rotaId=" + rotaId +
                ", caminhaoId=" + caminhaoId +
                ", data=" + data +
                '}';
    }

    // Métodos utilitários
    public boolean isValid() {
        return rotaId != null && 
               caminhaoId != null && 
               data != null &&
               !data.isBefore(LocalDate.now()); // Data não pode ser no passado
    }

    public boolean isDataFutura() {
        return data != null && data.isAfter(LocalDate.now());
    }

    public boolean isDataHoje() {
        return data != null && data.isEqual(LocalDate.now());
    }

    public boolean isDataPassada() {
        return data != null && data.isBefore(LocalDate.now());
    }

    public void agendarPara(LocalDate novaData) {
        if (novaData != null && !novaData.isBefore(LocalDate.now())) {
            this.data = novaData;
        } else {
            throw new IllegalArgumentException("Data de agendamento deve ser hoje ou no futuro");
        }
    }

    public void agendarParaHoje() {
        this.data = LocalDate.now();
    }

    public void agendarParaAmanha() {
        this.data = LocalDate.now().plusDays(1);
    }

    public void agendarParaProximaSemana() {
        this.data = LocalDate.now().plusWeeks(1);
    }

    public void agendarPara(int ano, int mes, int dia) {
        try {
            LocalDate novaData = LocalDate.of(ano, mes, dia);
            agendarPara(novaData);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Data inválida: " + ano + "-" + mes + "-" + dia, e);
        }
    }

    public String getDataFormatada() {
        if (data == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public boolean isAgendamentoValido() {
        return isValid() && !isDataPassada();
    }

    public static ItinerarioRequestDTO of(Long rotaId, Long caminhaoId, LocalDate data) {
        return new ItinerarioRequestDTO(rotaId, caminhaoId, data);
    }

    public static ItinerarioRequestDTO of(Long rotaId, Long caminhaoId) {
        return new ItinerarioRequestDTO(rotaId, caminhaoId);
    }


    public static ItinerarioRequestDTOBuilder builder() {
        return new ItinerarioRequestDTOBuilder();
    }

    public static class ItinerarioRequestDTOBuilder {
        private Long rotaId;
        private Long caminhaoId;
        private LocalDate data;

        private ItinerarioRequestDTOBuilder() {
            this.data = LocalDate.now();
        }

        public ItinerarioRequestDTOBuilder rotaId(Long rotaId) {
            this.rotaId = rotaId;
            return this;
        }

        public ItinerarioRequestDTOBuilder caminhaoId(Long caminhaoId) {
            this.caminhaoId = caminhaoId;
            return this;
        }

        public ItinerarioRequestDTOBuilder data(LocalDate data) {
            this.data = data;
            return this;
        }

        public ItinerarioRequestDTOBuilder data(int ano, int mes, int dia) {
            this.data = LocalDate.of(ano, mes, dia);
            return this;
        }

        public ItinerarioRequestDTOBuilder paraHoje() {
            this.data = LocalDate.now();
            return this;
        }

        public ItinerarioRequestDTOBuilder paraAmanha() {
            this.data = LocalDate.now().plusDays(1);
            return this;
        }

        public ItinerarioRequestDTOBuilder paraProximaSemana() {
            this.data = LocalDate.now().plusWeeks(1);
            return this;
        }

        public ItinerarioRequestDTO build() {
            // Validações
            if (rotaId == null) {
                throw new IllegalArgumentException("ID da rota não pode ser nulo");
            }
            if (caminhaoId == null) {
                throw new IllegalArgumentException("ID do caminhão não pode ser nulo");
            }
            if (data == null) {
                data = LocalDate.now();
            }
            if (data.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Data do itinerário não pode ser no passado");
            }
            
            return new ItinerarioRequestDTO(rotaId, caminhaoId, data);
        }
    }
}