package com.lixo.gerenciamento.model.dto.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class RotaResponseDTO {
    private Long id;
    private String nome;
    private Long caminhaoId;
    private String caminhaoPlaca;
    private Double distanciaTotalKm;
    private TipoResiduo tiposResiduos;
    private List<ParadaRotaResponseDTO> paradas;
    private List<ParadaPontoColetaResponseDTO> pontosColeta;

    public RotaResponseDTO() {
        this.paradas = new ArrayList<>();
        this.pontosColeta = new ArrayList<>();
    }

    public RotaResponseDTO(Long id, String nome, Long caminhaoId, String caminhaoPlaca,
                          Double distanciaTotalKm, TipoResiduo tiposResiduos,
                          List<ParadaRotaResponseDTO> paradas, 
                          List<ParadaPontoColetaResponseDTO> pontosColeta) {
        this.id = id;
        this.nome = nome;
        this.caminhaoId = caminhaoId;
        this.caminhaoPlaca = caminhaoPlaca;
        this.distanciaTotalKm = distanciaTotalKm;
        this.tiposResiduos = tiposResiduos;
        this.paradas = paradas != null ? paradas : new ArrayList<>();
        this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
    }

    public RotaResponseDTO(Long id, String nome, Long caminhaoId, String caminhaoPlaca,
                          Double distanciaTotalKm, TipoResiduo tiposResiduos) {
        this(id, nome, caminhaoId, caminhaoPlaca, distanciaTotalKm, tiposResiduos, 
             new ArrayList<>(), new ArrayList<>());
    }

    public RotaResponseDTO(Long id, String nome, Double distanciaTotalKm, TipoResiduo tiposResiduos) {
        this(id, nome, null, null, distanciaTotalKm, tiposResiduos, new ArrayList<>(), new ArrayList<>());
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

    public Double getDistanciaTotalKm() {
        return distanciaTotalKm;
    }

    public void setDistanciaTotalKm(Double distanciaTotalKm) {
        this.distanciaTotalKm = distanciaTotalKm;
    }

    public TipoResiduo getTiposResiduos() {
        return tiposResiduos;
    }

    public void setTiposResiduos(TipoResiduo tiposResiduos) {
        this.tiposResiduos = tiposResiduos;
    }

    public List<ParadaRotaResponseDTO> getParadas() {
        if (paradas == null) {
            paradas = new ArrayList<>();
        }
        return paradas;
    }

    public void setParadas(List<ParadaRotaResponseDTO> paradas) {
        this.paradas = paradas != null ? paradas : new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RotaResponseDTO that = (RotaResponseDTO) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (caminhaoId != null ? !caminhaoId.equals(that.caminhaoId) : that.caminhaoId != null) return false;
        if (caminhaoPlaca != null ? !caminhaoPlaca.equals(that.caminhaoPlaca) : that.caminhaoPlaca != null) return false;
        if (distanciaTotalKm != null ? !distanciaTotalKm.equals(that.distanciaTotalKm) : that.distanciaTotalKm != null) return false;
        if (tiposResiduos != that.tiposResiduos) return false;
        if (paradas != null ? !paradas.equals(that.paradas) : that.paradas != null) return false;
        return pontosColeta != null ? pontosColeta.equals(that.pontosColeta) : that.pontosColeta == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (caminhaoId != null ? caminhaoId.hashCode() : 0);
        result = 31 * result + (caminhaoPlaca != null ? caminhaoPlaca.hashCode() : 0);
        result = 31 * result + (distanciaTotalKm != null ? distanciaTotalKm.hashCode() : 0);
        result = 31 * result + (tiposResiduos != null ? tiposResiduos.hashCode() : 0);
        result = 31 * result + (paradas != null ? paradas.hashCode() : 0);
        result = 31 * result + (pontosColeta != null ? pontosColeta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RotaResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminhaoId=" + caminhaoId +
                ", caminhaoPlaca='" + caminhaoPlaca + '\'' +
                ", distanciaTotalKm=" + distanciaTotalKm +
                ", tiposResiduos=" + tiposResiduos +
                ", paradas=" + (paradas != null ? paradas.size() : 0) + " itens" +
                ", pontosColeta=" + (pontosColeta != null ? pontosColeta.size() : 0) + " itens" +
                '}';
    }

    public void addParada(ParadaRotaResponseDTO parada) {
        if (parada != null) {
            getParadas().add(parada);
        }
    }

    public void removeParada(ParadaRotaResponseDTO parada) {
        if (paradas != null && parada != null) {
            paradas.remove(parada);
        }
    }

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

    public boolean hasParadas() {
        return paradas != null && !paradas.isEmpty();
    }

    public boolean hasPontosColeta() {
        return pontosColeta != null && !pontosColeta.isEmpty();
    }

    public int quantidadeParadas() {
        return hasParadas() ? paradas.size() : 0;
    }

    public int quantidadePontosColeta() {
        return hasPontosColeta() ? pontosColeta.size() : 0;
    }

    public boolean todasParadasConcluidas() {
        if (!hasParadas()) {
            return false;
        }
        return paradas.stream().allMatch(ParadaRotaResponseDTO::isConcluida);
    }

    public int quantidadeParadasConcluidas() {
        if (!hasParadas()) {
            return 0;
        }
        return (int) paradas.stream()
                .filter(ParadaRotaResponseDTO::isConcluida)
                .count();
    }

    public int quantidadePontosColetaColetados() {
        if (!hasPontosColeta()) {
            return 0;
        }
        return (int) pontosColeta.stream()
                .filter(ParadaPontoColetaResponseDTO::isColetado)
                .count();
    }

    public double percentualConclusaoParadas() {
        if (!hasParadas()) {
            return 0.0;
        }
        int total = paradas.size();
        int concluidas = quantidadeParadasConcluidas();
        return total > 0 ? (concluidas * 100.0) / total : 0.0;
    }

    public double percentualConclusaoPontosColeta() {
        if (!hasPontosColeta()) {
            return 0.0;
        }
        int total = pontosColeta.size();
        int coletados = quantidadePontosColetaColetados();
        return total > 0 ? (coletados * 100.0) / total : 0.0;
    }

    public boolean isConcluida() {
        return todasParadasConcluidas();
    }

    public boolean hasCaminhaoAssociado() {
        return caminhaoId != null && caminhaoPlaca != null && !caminhaoPlaca.trim().isEmpty();
    }

    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() &&
               distanciaTotalKm != null && distanciaTotalKm > 0 &&
               tiposResiduos != null;
    }

    public List<TipoResiduo> getTiposResiduoNaRota() {
        Set<TipoResiduo> tipos = new HashSet<>();
        if (tiposResiduos != null) {
            tipos.add(tiposResiduos);
        }
        
        if (hasPontosColeta()) {
            for (ParadaPontoColetaResponseDTO ponto : pontosColeta) {
                if (ponto.getTiposResiduo() != null) {
                    tipos.addAll(ponto.getTiposResiduo());
                }
            }
        }
        
        return new ArrayList<>(tipos);
    }

    public static RotaResponseDTOBuilder builder() {
        return new RotaResponseDTOBuilder();
    }

    public static class RotaResponseDTOBuilder {
        private Long id;
        private String nome;
        private Long caminhaoId;
        private String caminhaoPlaca;
        private Double distanciaTotalKm;
        private TipoResiduo tiposResiduos;
        private List<ParadaRotaResponseDTO> paradas;
        private List<ParadaPontoColetaResponseDTO> pontosColeta;

        private RotaResponseDTOBuilder() {
            this.paradas = new ArrayList<>();
            this.pontosColeta = new ArrayList<>();
        }

        public RotaResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RotaResponseDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public RotaResponseDTOBuilder caminhaoId(Long caminhaoId) {
            this.caminhaoId = caminhaoId;
            return this;
        }

        public RotaResponseDTOBuilder caminhaoPlaca(String caminhaoPlaca) {
            this.caminhaoPlaca = caminhaoPlaca;
            return this;
        }

        public RotaResponseDTOBuilder caminhao(Long id, String placa) {
            this.caminhaoId = id;
            this.caminhaoPlaca = placa;
            return this;
        }

        public RotaResponseDTOBuilder distanciaTotalKm(Double distanciaTotalKm) {
            this.distanciaTotalKm = distanciaTotalKm;
            return this;
        }

        public RotaResponseDTOBuilder tiposResiduos(TipoResiduo tiposResiduos) {
            this.tiposResiduos = tiposResiduos;
            return this;
        }

        public RotaResponseDTOBuilder paradas(List<ParadaRotaResponseDTO> paradas) {
            this.paradas = paradas != null ? paradas : new ArrayList<>();
            return this;
        }

        public RotaResponseDTOBuilder parada(ParadaRotaResponseDTO parada) {
            if (this.paradas == null) {
                this.paradas = new ArrayList<>();
            }
            if (parada != null) {
                this.paradas.add(parada);
            }
            return this;
        }

        public RotaResponseDTOBuilder pontosColeta(List<ParadaPontoColetaResponseDTO> pontosColeta) {
            this.pontosColeta = pontosColeta != null ? pontosColeta : new ArrayList<>();
            return this;
        }

        public RotaResponseDTOBuilder pontoColeta(ParadaPontoColetaResponseDTO pontoColeta) {
            if (this.pontosColeta == null) {
                this.pontosColeta = new ArrayList<>();
            }
            if (pontoColeta != null) {
                this.pontosColeta.add(pontoColeta);
            }
            return this;
        }

        public RotaResponseDTO build() {
            
            return new RotaResponseDTO(id, nome, caminhaoId, caminhaoPlaca, distanciaTotalKm,
                                      tiposResiduos, paradas, pontosColeta);
        }
    }

}