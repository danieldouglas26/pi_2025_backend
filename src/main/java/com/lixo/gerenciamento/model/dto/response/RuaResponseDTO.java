package com.lixo.gerenciamento.model.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Rua;

public class RuaResponseDTO {
    private Long id;
    private Long origemId;
    private String origemNome;
    private Long destinoId;
    private String destinoNome;
    private Double distancia;

    // Construtor padrão (no-args)
    public RuaResponseDTO() {
    }

    // Construtor com todos os campos
    public RuaResponseDTO(Long id, Long origemId, String origemNome, Long destinoId, 
                         String destinoNome, Double distancia) {
        this.id = id;
        this.origemId = origemId;
        this.origemNome = origemNome;
        this.destinoId = destinoId;
        this.destinoNome = destinoNome;
        this.distancia = distancia;
    }

    // Construtor simplificado (sem ID)
    public RuaResponseDTO(Long origemId, String origemNome, Long destinoId, 
                         String destinoNome, Double distancia) {
        this(null, origemId, origemNome, destinoId, destinoNome, distancia);
    }

    // Construtor apenas com IDs e distância
    public RuaResponseDTO(Long origemId, Long destinoId, Double distancia) {
        this(null, origemId, null, destinoId, null, distancia);
    }

    // Construtor reverso (para direção oposta)
    public RuaResponseDTO criarReverso() {
        return new RuaResponseDTO(
            this.id, 
            this.destinoId, 
            this.destinoNome,
            this.origemId, 
            this.origemNome,
            this.distancia
        );
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long origemId) {
        this.origemId = origemId;
    }

    public String getOrigemNome() {
        return origemNome;
    }

    public void setOrigemNome(String origemNome) {
        this.origemNome = origemNome;
    }

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    public String getDestinoNome() {
        return destinoNome;
    }

    public void setDestinoNome(String destinoNome) {
        this.destinoNome = destinoNome;
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
        
        RuaResponseDTO that = (RuaResponseDTO) o;
        

        boolean mesmaRuaDireta = 
            id != null ? id.equals(that.id) : that.id == null;
        
        boolean mesmaRuaBidirecional = 
            ((origemId != null ? origemId.equals(that.origemId) : that.origemId == null) &&
             (destinoId != null ? destinoId.equals(that.destinoId) : that.destinoId == null)) ||
            ((origemId != null ? origemId.equals(that.destinoId) : that.destinoId == null) &&
             (destinoId != null ? destinoId.equals(that.origemId) : that.origemId == null));
        
        return mesmaRuaDireta || mesmaRuaBidirecional;
    }

    @Override
    public int hashCode() {
        Long menorId = origemId != null && destinoId != null ? 
                      Math.min(origemId, destinoId) : (origemId != null ? origemId : destinoId);
        Long maiorId = origemId != null && destinoId != null ? 
                      Math.max(origemId, destinoId) : (origemId != null ? origemId : destinoId);
        
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (menorId != null ? menorId.hashCode() : 0);
        result = 31 * result + (maiorId != null ? maiorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuaResponseDTO{" +
                "id=" + id +
                ", origemId=" + origemId +
                ", origemNome='" + origemNome + '\'' +
                ", destinoId=" + destinoId +
                ", destinoNome='" + destinoNome + '\'' +
                ", distancia=" + distancia +
                '}';
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

    public boolean isMesmaRua(RuaResponseDTO outraRua) {
        return this.equals(outraRua);
    }

    public boolean isValid() {
        return origemId != null && destinoId != null && 
               !origemId.equals(destinoId) && // Não pode ser o mesmo bairro
               distancia != null && distancia >= 0;
    }

    public boolean hasNomesBairros() {
        return origemNome != null && !origemNome.trim().isEmpty() &&
               destinoNome != null && !destinoNome.trim().isEmpty();
    }

    public String getDescricao() {
        if (hasNomesBairros()) {
            return origemNome + " → " + destinoNome + " (" + distancia + " km)";
        } else {
            return origemId + " → " + destinoId + " (" + distancia + " km)";
        }
    }

    public static RuaResponseDTOBuilder builder() {
        return new RuaResponseDTOBuilder();
    }

    public static class RuaResponseDTOBuilder {
        private Long id;
        private Long origemId;
        private String origemNome;
        private Long destinoId;
        private String destinoNome;
        private Double distancia;

        private RuaResponseDTOBuilder() {
        }

        public RuaResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RuaResponseDTOBuilder origemId(Long origemId) {
            this.origemId = origemId;
            return this;
        }

        public RuaResponseDTOBuilder origemNome(String origemNome) {
            this.origemNome = origemNome;
            return this;
        }

        public RuaResponseDTOBuilder origem(Long id, String nome) {
            this.origemId = id;
            this.origemNome = nome;
            return this;
        }

        public RuaResponseDTOBuilder destinoId(Long destinoId) {
            this.destinoId = destinoId;
            return this;
        }

        public RuaResponseDTOBuilder destinoNome(String destinoNome) {
            this.destinoNome = destinoNome;
            return this;
        }

        public RuaResponseDTOBuilder destino(Long id, String nome) {
            this.destinoId = id;
            this.destinoNome = nome;
            return this;
        }

        public RuaResponseDTOBuilder distancia(Double distancia) {
            this.distancia = distancia;
            return this;
        }

        public RuaResponseDTOBuilder comBairros(Bairro origem, Bairro destino, Double distancia) {
            if (origem != null) {
                this.origemId = origem.getId();
                this.origemNome = origem.getNome();
            }
            if (destino != null) {
                this.destinoId = destino.getId();
                this.destinoNome = destino.getNome();
            }
            this.distancia = distancia;
            return this;
        }

        public RuaResponseDTO build() {
            if (origemId == null) {
                throw new IllegalArgumentException("ID da origem não pode ser nulo");
            }
            if (destinoId == null) {
                throw new IllegalArgumentException("ID do destino não pode ser nulo");
            }
            if (origemId.equals(destinoId)) {
                throw new IllegalArgumentException("Origem e destino não podem ser o mesmo bairro");
            }
            if (distancia == null) {
                throw new IllegalArgumentException("Distância não pode ser nula");
            }
            if (distancia < 0) {
                throw new IllegalArgumentException("Distância não pode ser negativa");
            }
            
            return new RuaResponseDTO(id, origemId, origemNome, destinoId, destinoNome, distancia);
        }
    }

    public static RuaResponseDTO fromEntity(Rua rua) {
        if (rua == null) {
            return null;
        }
        
        RuaResponseDTO dto = new RuaResponseDTO();
        dto.setId(rua.getId());
        dto.setDistancia(rua.getDistancia());
        
        if (rua.getOrigem() != null) {
            dto.setOrigemId(rua.getOrigem().getId());
            dto.setOrigemNome(rua.getOrigem().getNome());
        }
        
        if (rua.getDestino() != null) {
            dto.setDestinoId(rua.getDestino().getId());
            dto.setDestinoNome(rua.getDestino().getNome());
        }
        
        return dto;
    }

    public static List<RuaResponseDTO> fromEntities(List<Rua> ruas) {
        if (ruas == null) {
            return Collections.emptyList();
        }
        return ruas.stream()
                .map(RuaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static RuaResponseDTO criarDeBairros(Bairro origem, Bairro destino, Double distancia) {
        if (origem == null || destino == null || distancia == null) {
            return null;
        }
        
        return new RuaResponseDTO(
            origem.getId(),
            origem.getNome(),
            destino.getId(),
            destino.getNome(),
            distancia
        );
    }

    public static RuaResponseDTO criarDeIds(Long origemId, Long destinoId, Double distancia) {
        if (origemId == null || destinoId == null || distancia == null) {
            return null;
        }
        
        return new RuaResponseDTO(origemId, destinoId, distancia);
    }
}