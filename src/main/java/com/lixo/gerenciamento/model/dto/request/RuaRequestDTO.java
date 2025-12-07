package com.lixo.gerenciamento.model.dto.request;

import com.lixo.gerenciamento.model.dto.response.RuaResponseDTO;
import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Rua;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RuaRequestDTO {
    
    @NotNull(message = "O ID do bairro de origem é obrigatório")
    private Long origemId;
    
    @NotNull(message = "O ID do bairro de destino é obrigatório")
    private Long destinoId;
    
    @NotNull(message = "A distância é obrigatória")
    @Positive(message = "A distância deve ser maior que zero")
    private Double distancia;

    public RuaRequestDTO() {
    }


    public RuaRequestDTO(Long origemId, Long destinoId, Double distancia) {
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.distancia = distancia;
    }


    public RuaRequestDTO criarReverso() {
        return new RuaRequestDTO(destinoId, origemId, distancia);
    }

    // Getters e Setters
    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long origemId) {
        this.origemId = origemId;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RuaRequestDTO that = (RuaRequestDTO) o;
        

        boolean mesmaRuaDireta = 
            origemId != null && destinoId != null &&
            origemId.equals(that.origemId) && destinoId.equals(that.destinoId);
        
        boolean mesmaRuaReversa = 
            origemId != null && destinoId != null &&
            origemId.equals(that.destinoId) && destinoId.equals(that.origemId);
        
        boolean distanciasIguais = 
            distancia != null ? distancia.equals(that.distancia) : that.distancia == null;
        
        return (mesmaRuaDireta || mesmaRuaReversa) && distanciasIguais;
    }

    @Override
    public int hashCode() {

        Long menorId = origemId != null && destinoId != null ? 
                      Math.min(origemId, destinoId) : (origemId != null ? origemId : destinoId);
        Long maiorId = origemId != null && destinoId != null ? 
                      Math.max(origemId, destinoId) : (origemId != null ? origemId : destinoId);
        
        int result = menorId != null ? menorId.hashCode() : 0;
        result = 31 * result + (maiorId != null ? maiorId.hashCode() : 0);
        result = 31 * result + (distancia != null ? distancia.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "RuaRequestDTO{" +
                "origemId=" + origemId +
                ", destinoId=" + destinoId +
                ", distancia=" + distancia +
                '}';
    }


    public boolean isValid() {
        return origemId != null && 
               destinoId != null && 
               !origemId.equals(destinoId) && 
               distancia != null && 
               distancia > 0; 
    }

    public boolean isBidirecional() {
        return true; 
        }

    public boolean conectaBairros(Long bairro1Id, Long bairro2Id) {
        if (bairro1Id == null || bairro2Id == null) {
            return false;
        }
        return (origemId != null && destinoId != null) &&
               ((origemId.equals(bairro1Id) && destinoId.equals(bairro2Id)) ||
                (origemId.equals(bairro2Id) && destinoId.equals(bairro1Id)));
    }

    public Long getOutroBairro(Long bairroId) {
        if (origemId != null && origemId.equals(bairroId)) {
            return destinoId;
        } else if (destinoId != null && destinoId.equals(bairroId)) {
            return origemId;
        }
        return null;
    }

    public boolean isMesmaRua(RuaRequestDTO outraRua) {
        return this.equals(outraRua);
    }

    public boolean isDistanciaValida() {
        return distancia != null && distancia > 0;
    }

    public String getDescricao() {
        return origemId + " → " + destinoId + " (" + distancia + " km)";
    }

    public String getDescricaoBidirecional() {
        return origemId + " ↔ " + destinoId + " (" + distancia + " km)";
    }

    public static RuaRequestDTO of(Long origemId, Long destinoId, Double distancia) {
        return new RuaRequestDTO(origemId, destinoId, distancia);
    }

    public static RuaRequestDTO criarDeBairros(Bairro origem, Bairro destino, Double distancia) {
        if (origem == null || destino == null || distancia == null) {
            return null;
        }
        return new RuaRequestDTO(origem.getId(), destino.getId(), distancia);
    }

    public static RuaRequestDTO fromEntity(Rua rua) {
        if (rua == null) {
            return null;
        }
        
        RuaRequestDTO dto = new RuaRequestDTO();
        dto.setDistancia(rua.getDistancia());
        
        if (rua.getOrigem() != null) {
            dto.setOrigemId(rua.getOrigem().getId());
        }
        
        if (rua.getDestino() != null) {
            dto.setDestinoId(rua.getDestino().getId());
        }
        
        return dto;
    }

    public static RuaRequestDTO fromResponseDTO(RuaResponseDTO responseDTO) {
        if (responseDTO == null) {
            return null;
        }
        
        return new RuaRequestDTO(
            responseDTO.getOrigemId(),
            responseDTO.getDestinoId(),
            responseDTO.getDistancia()
        );
    }

    public static RuaRequestDTOBuilder builder() {
        return new RuaRequestDTOBuilder();
    }

    public static class RuaRequestDTOBuilder {
        private Long origemId;
        private Long destinoId;
        private Double distancia;

        private RuaRequestDTOBuilder() {
        }

        public RuaRequestDTOBuilder origemId(Long origemId) {
            this.origemId = origemId;
            return this;
        }

        public RuaRequestDTOBuilder destinoId(Long destinoId) {
            this.destinoId = destinoId;
            return this;
        }

        public RuaRequestDTOBuilder distancia(Double distancia) {
            this.distancia = distancia;
            return this;
        }

        public RuaRequestDTOBuilder bairros(Long origemId, Long destinoId, Double distancia) {
            this.origemId = origemId;
            this.destinoId = destinoId;
            this.distancia = distancia;
            return this;
        }

        public RuaRequestDTO build() {
            if (origemId == null) {
                throw new IllegalArgumentException("ID do bairro de origem é obrigatório");
            }
            if (destinoId == null) {
                throw new IllegalArgumentException("ID do bairro de destino é obrigatório");
            }
            if (origemId.equals(destinoId)) {
                throw new IllegalArgumentException("Origem e destino não podem ser o mesmo bairro");
            }
            if (distancia == null) {
                throw new IllegalArgumentException("Distância é obrigatória");
            }
            if (distancia <= 0) {
                throw new IllegalArgumentException("Distância deve ser maior que zero");
            }
            
            return new RuaRequestDTO(origemId, destinoId, distancia);
        }
    }
}
