package com.lixo.gerenciamento.model.dto.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.entity.ParadaRota;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class ParadaRotaResponseDTO {
    private int ordem;
    private Long bairroId;
    private String bairroNome;
    private List<ParadaPontoColetaResponseDTO> pontosColeta;

    // Construtor padrão (no-args)
    public ParadaRotaResponseDTO() {
        this.pontosColeta = new ArrayList<>();
        this.ordem = 0; // Valor padrão
    }

    // Construtor com todos os campos
    public ParadaRotaResponseDTO(int ordem, Long bairroId, String bairroNome, 
                                List<ParadaPontoColetaResponseDTO> pontosColeta) {
        this.ordem = ordem;
        this.bairroId = bairroId;
        this.bairroNome = bairroNome;
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
    }

    // Construtor simplificado (sem lista de pontos de coleta)
    public ParadaRotaResponseDTO(int ordem, Long bairroId, String bairroNome) {
        this(ordem, bairroId, bairroNome, new ArrayList<>());
    }

    // Getters e Setters
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

    public List<ParadaPontoColetaResponseDTO> getPontosColeta() {
        if (pontosColeta == null) {
            pontosColeta = new ArrayList<>();
        }
        return pontosColeta;
    }

    public void setPontosColeta(List<ParadaPontoColetaResponseDTO> pontosColeta) {
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParadaRotaResponseDTO that = (ParadaRotaResponseDTO) o;
        
        if (ordem != that.ordem) return false;
        if (bairroId != null ? !bairroId.equals(that.bairroId) : that.bairroId != null) return false;
        if (bairroNome != null ? !bairroNome.equals(that.bairroNome) : that.bairroNome != null) return false;
        return pontosColeta != null ? pontosColeta.equals(that.pontosColeta) : that.pontosColeta == null;
    }

    @Override
    public int hashCode() {
        int result = ordem;
        result = 31 * result + (bairroId != null ? bairroId.hashCode() : 0);
        result = 31 * result + (bairroNome != null ? bairroNome.hashCode() : 0);
        result = 31 * result + (pontosColeta != null ? pontosColeta.hashCode() : 0);
        return result;
    }

    // Método toString
    @Override
    public String toString() {
        return "ParadaRotaResponseDTO{" +
                "ordem=" + ordem +
                ", bairroId=" + bairroId +
                ", bairroNome='" + bairroNome + '\'' +
                ", pontosColeta=" + (pontosColeta != null ? pontosColeta.size() : 0) + " itens" +
                '}';
    }

    // Métodos utilitários
    public void addPontoColeta(ParadaPontoColetaResponseDTO pontoColeta) {
        if (pontoColeta != null) {
            getPontosColeta().add(pontoColeta);
        }
    }

    public void removePontoColeta(ParadaPontoColetaResponseDTO pontoColeta) {
        if (pontosColeta != null && pontoColeta != null) {
            pontosColeta.remove(pontoColeta);
        }
    }

    public boolean hasPontosColeta() {
        return pontosColeta != null && !pontosColeta.isEmpty();
    }

    public int quantidadePontosColeta() {
        return hasPontosColeta() ? pontosColeta.size() : 0;
    }

    public boolean todosPontosColetados() {
        if (!hasPontosColeta()) {
            return false;
        }
        return pontosColeta.stream().allMatch(ParadaPontoColetaResponseDTO::isColetado);
    }

    public int quantidadePontosColetados() {
        if (!hasPontosColeta()) {
            return 0;
        }
        return (int) pontosColeta.stream()
                .filter(ParadaPontoColetaResponseDTO::isColetado)
                .count();
    }

    public double percentualConclusao() {
        if (!hasPontosColeta()) {
            return 0.0;
        }
        int total = pontosColeta.size();
        int coletados = quantidadePontosColetados();
        return total > 0 ? (coletados * 100.0) / total : 0.0;
    }

    public boolean isConcluida() {
        return todosPontosColetados();
    }

    public List<TipoResiduo> getTiposResiduoDisponiveis() {
        Set<TipoResiduo> tipos = new HashSet<>();
        if (hasPontosColeta()) {
            for (ParadaPontoColetaResponseDTO ponto : pontosColeta) {
                if (ponto.getTiposResiduo() != null) {
                    tipos.addAll(ponto.getTiposResiduo());
                }
            }
        }
        return new ArrayList<>(tipos);
    }

    // Builder Pattern
    public static ParadaRotaResponseDTOBuilder builder() {
        return new ParadaRotaResponseDTOBuilder();
    }

    public static class ParadaRotaResponseDTOBuilder {
        private int ordem;
        private Long bairroId;
        private String bairroNome;
        private List<ParadaPontoColetaResponseDTO> pontosColeta;

        private ParadaRotaResponseDTOBuilder() {
            this.pontosColeta = new ArrayList<>();
            this.ordem = 0;
        }

        public ParadaRotaResponseDTOBuilder ordem(int ordem) {
            this.ordem = ordem;
            return this;
        }

        public ParadaRotaResponseDTOBuilder bairroId(Long bairroId) {
            this.bairroId = bairroId;
            return this;
        }

        public ParadaRotaResponseDTOBuilder bairroNome(String bairroNome) {
            this.bairroNome = bairroNome;
            return this;
        }

        public ParadaRotaResponseDTOBuilder bairro(Long id, String nome) {
            this.bairroId = id;
            this.bairroNome = nome;
            return this;
        }

        public ParadaRotaResponseDTOBuilder pontosColeta(List<ParadaPontoColetaResponseDTO> pontosColeta) {
            this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
            return this;
        }

        public ParadaRotaResponseDTOBuilder pontoColeta(ParadaPontoColetaResponseDTO pontoColeta) {
            if (this.pontosColeta == null) {
                this.pontosColeta = new ArrayList<>();
            }
            if (pontoColeta != null) {
                this.pontosColeta.add(pontoColeta);
            }
            return this;
        }

        public ParadaRotaResponseDTO build() {
            // Validações básicas
            if (ordem < 0) {
                throw new IllegalArgumentException("Ordem não pode ser negativa");
            }
            if (bairroId == null) {
                throw new IllegalArgumentException("ID do bairro não pode ser nulo");
            }
            if (bairroNome == null || bairroNome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do bairro não pode ser nulo ou vazio");
            }
            
            return new ParadaRotaResponseDTO(ordem, bairroId, bairroNome, pontosColeta);
        }
    }

    public static ParadaRotaResponseDTO fromEntity(ParadaRota paradaRota) {
        if (paradaRota == null) {
            return null;
        }
        
        ParadaRotaResponseDTO dto = new ParadaRotaResponseDTO();
        dto.setOrdem(paradaRota.getOrdem());
        
        if (paradaRota.getBairro() != null) {
            dto.setBairroId(paradaRota.getBairro().getId());
            dto.setBairroNome(paradaRota.getBairro().getNome());
        }
        
        // Converter pontos de coleta se existirem
        if (paradaRota.getParadasPontoColeta() != null && !paradaRota.getParadasPontoColeta().isEmpty()) {
            List<ParadaPontoColetaResponseDTO> pontosDTO = ParadaPontoColetaResponseDTO.fromEntities(paradaRota.getParadasPontoColeta());
            dto.setPontosColeta(pontosDTO);
        }
        
        return dto;
    }

    public static List<ParadaRotaResponseDTO> fromEntities(List<ParadaRota> paradasRota) {
        if (paradasRota == null) {
            return Collections.emptyList();
        }
        return paradasRota.stream()
                .map(ParadaRotaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

}