package com.lixo.gerenciamento.model.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

public class CaminhaoResponseDTO {
    
    private Long id;
    private String placa;
    private String nomeMotorista;
    private Double capacidade;
    private List<TipoResiduo> tipoResiduos;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public CaminhaoResponseDTO() {
        this.tipoResiduos = new ArrayList<>();
    }

    public CaminhaoResponseDTO(Long id, String placa, String nomeMotorista, Double capacidade, 
                              List<TipoResiduo> tipoResiduos, LocalDateTime dataCriacao, 
                              LocalDateTime dataAtualizacao) {
        this.id = id;
        this.placa = placa;
        this.nomeMotorista = nomeMotorista;
        this.capacidade = capacidade;
        this.tipoResiduos = tipoResiduos != null ? tipoResiduos : new ArrayList<>();
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public CaminhaoResponseDTO(Long id, String placa, String nomeMotorista, Double capacidade, 
                              List<TipoResiduo> tipoResiduos) {
        this(id, placa, nomeMotorista, capacidade, tipoResiduos, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public List<TipoResiduo> getTipoResiduos() {
        if (tipoResiduos == null) {
            tipoResiduos = new ArrayList<>();
        }
        return tipoResiduos;
    }

    public void setTipoResiduos(List<TipoResiduo> tipoResiduos) {
        this.tipoResiduos = tipoResiduos != null ? tipoResiduos : new ArrayList<>();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CaminhaoResponseDTO that = (CaminhaoResponseDTO) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (placa != null ? !placa.equals(that.placa) : that.placa != null) return false;
        if (nomeMotorista != null ? !nomeMotorista.equals(that.nomeMotorista) : that.nomeMotorista != null) return false;
        if (capacidade != null ? !capacidade.equals(that.capacidade) : that.capacidade != null) return false;
        if (tipoResiduos != null ? !tipoResiduos.equals(that.tipoResiduos) : that.tipoResiduos != null) return false;
        if (dataCriacao != null ? !dataCriacao.equals(that.dataCriacao) : that.dataCriacao != null) return false;
        return dataAtualizacao != null ? dataAtualizacao.equals(that.dataAtualizacao) : that.dataAtualizacao == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (placa != null ? placa.hashCode() : 0);
        result = 31 * result + (nomeMotorista != null ? nomeMotorista.hashCode() : 0);
        result = 31 * result + (capacidade != null ? capacidade.hashCode() : 0);
        result = 31 * result + (tipoResiduos != null ? tipoResiduos.hashCode() : 0);
        result = 31 * result + (dataCriacao != null ? dataCriacao.hashCode() : 0);
        result = 31 * result + (dataAtualizacao != null ? dataAtualizacao.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CaminhaoResponseDTO{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", nomeMotorista='" + nomeMotorista + '\'' +
                ", capacidade=" + capacidade +
                ", tipoResiduos=" + tipoResiduos +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }

    public void addTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            getTipoResiduos().add(tipoResiduo);
        }
    }

    public void removeTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduos != null && tipoResiduo != null) {
            tipoResiduos.remove(tipoResiduo);
        }
    }

    public boolean hasTipoResiduo(TipoResiduo tipoResiduo) {
        return tipoResiduos != null && tipoResiduos.contains(tipoResiduo);
    }

    public boolean isValid() {
        return placa != null && !placa.trim().isEmpty() &&
               nomeMotorista != null && !nomeMotorista.trim().isEmpty() &&
               capacidade != null && capacidade > 0;
    }

    public static CaminhaoResponseDTOBuilder builder() {
        return new CaminhaoResponseDTOBuilder();
    }

    public static class CaminhaoResponseDTOBuilder {
        private Long id;
        private String placa;
        private String nomeMotorista;
        private Double capacidade;
        private List<TipoResiduo> tipoResiduos;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAtualizacao;

        private CaminhaoResponseDTOBuilder() {
            this.tipoResiduos = new ArrayList<>();
        }

        public CaminhaoResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CaminhaoResponseDTOBuilder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public CaminhaoResponseDTOBuilder nomeMotorista(String nomeMotorista) {
            this.nomeMotorista = nomeMotorista;
            return this;
        }

        public CaminhaoResponseDTOBuilder capacidade(Double capacidade) {
            this.capacidade = capacidade;
            return this;
        }

        public CaminhaoResponseDTOBuilder tipoResiduos(List<TipoResiduo> tipoResiduos) {
            this.tipoResiduos = tipoResiduos != null ? tipoResiduos : new ArrayList<>();
            return this;
        }

        public CaminhaoResponseDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            if (this.tipoResiduos == null) {
                this.tipoResiduos = new ArrayList<>();
            }
            if (tipoResiduo != null) {
                this.tipoResiduos.add(tipoResiduo);
            }
            return this;
        }

        public CaminhaoResponseDTOBuilder dataCriacao(LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public CaminhaoResponseDTOBuilder dataCriacao() {
            this.dataCriacao = LocalDateTime.now();
            return this;
        }

        public CaminhaoResponseDTOBuilder dataAtualizacao(LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        public CaminhaoResponseDTOBuilder dataAtualizacao() {
            this.dataAtualizacao = LocalDateTime.now();
            return this;
        }

        public CaminhaoResponseDTO build() {
            if (placa == null || placa.trim().isEmpty()) {
                throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
            }
            if (nomeMotorista == null || nomeMotorista.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do motorista não pode ser nulo ou vazio");
            }
            if (capacidade == null || capacidade <= 0) {
                throw new IllegalArgumentException("Capacidade deve ser maior que zero");
            }
            
            if (dataCriacao == null && dataAtualizacao != null) {
                dataCriacao = dataAtualizacao;
            }
            
            return new CaminhaoResponseDTO(id, placa, nomeMotorista, capacidade, 
                                          tipoResiduos, dataCriacao, dataAtualizacao);
        }
    }

    public static CaminhaoResponseDTO fromEntity(Caminhao caminhao) {
        if (caminhao == null) {
            return null;
        }
        return new CaminhaoResponseDTO(
            caminhao.getId(),
            caminhao.getPlaca(),
            caminhao.getNomeMotorista(),
            caminhao.getCapacidade(),
            caminhao.getTipoResiduos(),
            null,
            null 
        );
    }

    public static List<CaminhaoResponseDTO> fromEntities(List<Caminhao> caminhoes) {
        if (caminhoes == null) {
            return Collections.emptyList();
        }
        return caminhoes.stream()
                .map(CaminhaoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}