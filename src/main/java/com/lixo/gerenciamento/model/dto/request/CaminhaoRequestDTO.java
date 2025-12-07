package com.lixo.gerenciamento.model.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.dto.response.CaminhaoResponseDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

import io.jsonwebtoken.lang.Arrays;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CaminhaoRequestDTO {
    
    @NotBlank(message = "A placa do caminhão é obrigatória")
    private String placa;
    
    @NotBlank(message = "O nome do motorista é obrigatório")
    private String nomeMotorista;
    
    @NotNull(message = "A capacidade do caminhão é obrigatória")
    @Positive(message = "A capacidade deve ser um valor positivo")
    private Double capacidade;
    
    @NotNull(message = "A lista de tipos de resíduos é obrigatória")
    private List<TipoResiduo> tipoResiduos;

    public CaminhaoRequestDTO() {
        this.tipoResiduos = new ArrayList<>();
    }

    public CaminhaoRequestDTO(String placa, String nomeMotorista, Double capacidade, 
                             List<TipoResiduo> tipoResiduos) {
        this.placa = placa;
        this.nomeMotorista = nomeMotorista;
        this.capacidade = capacidade;
        this.tipoResiduos = tipoResiduos != null ? tipoResiduos : new ArrayList<>();
    }

    public CaminhaoRequestDTO(String placa, String nomeMotorista, Double capacidade) {
        this(placa, nomeMotorista, capacidade, new ArrayList<>());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CaminhaoRequestDTO that = (CaminhaoRequestDTO) o;
        
        if (placa != null ? !placa.equals(that.placa) : that.placa != null) return false;
        if (nomeMotorista != null ? !nomeMotorista.equals(that.nomeMotorista) : that.nomeMotorista != null) return false;
        if (capacidade != null ? !capacidade.equals(that.capacidade) : that.capacidade != null) return false;
        return tipoResiduos != null ? tipoResiduos.equals(that.tipoResiduos) : that.tipoResiduos == null;
    }

    @Override
    public int hashCode() {
        int result = placa != null ? placa.hashCode() : 0;
        result = 31 * result + (nomeMotorista != null ? nomeMotorista.hashCode() : 0);
        result = 31 * result + (capacidade != null ? capacidade.hashCode() : 0);
        result = 31 * result + (tipoResiduos != null ? tipoResiduos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CaminhaoRequestDTO{" +
                "placa='" + placa + '\'' +
                ", nomeMotorista='" + nomeMotorista + '\'' +
                ", capacidade=" + capacidade +
                ", tipoResiduos=" + tipoResiduos +
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

    public boolean hasTiposResiduos() {
        return tipoResiduos != null && !tipoResiduos.isEmpty();
    }

    public boolean isValid() {
        return placa != null && !placa.trim().isEmpty() &&
               nomeMotorista != null && !nomeMotorista.trim().isEmpty() &&
               capacidade != null && capacidade > 0 &&
               hasTiposResiduos();
    }

    public String getPlacaFormatada() {
        if (placa == null || placa.trim().isEmpty()) {
            return "";
        }
        String placaLimpa = placa.trim().toUpperCase();
        if (placaLimpa.length() == 7) {
            return placaLimpa.substring(0, 3) + "-" + placaLimpa.substring(3);
        }
        return placaLimpa;
    }

    public boolean aceitaTipoResiduo(TipoResiduo tipoResiduo) {
        return hasTipoResiduo(tipoResiduo);
    }

    public boolean aceitaTodosTipos(List<TipoResiduo> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposResiduos()) {
            return false;
        }
        return tipos.stream().allMatch(this::aceitaTipoResiduo);
    }

    public boolean aceitaAlgumTipo(List<TipoResiduo> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposResiduos()) {
            return false;
        }
        return tipos.stream().anyMatch(this::aceitaTipoResiduo);
    }

    public static CaminhaoRequestDTO of(String placa, String nomeMotorista, Double capacidade, 
                                       List<TipoResiduo> tipoResiduos) {
        return new CaminhaoRequestDTO(placa, nomeMotorista, capacidade, tipoResiduos);
    }

    public static CaminhaoRequestDTO of(String placa, String nomeMotorista, Double capacidade) {
        return new CaminhaoRequestDTO(placa, nomeMotorista, capacidade);
    }

    public static CaminhaoRequestDTO fromEntity(Caminhao caminhao) {
        if (caminhao == null) {
            return null;
        }
        
        return new CaminhaoRequestDTO(
            caminhao.getPlaca(),
            caminhao.getNomeMotorista(),
            caminhao.getCapacidade(),
            caminhao.getTipoResiduos() != null ? new ArrayList<>(caminhao.getTipoResiduos()) : new ArrayList<>()
        );
    }

    public static CaminhaoRequestDTO fromResponseDTO(CaminhaoResponseDTO responseDTO) {
        if (responseDTO == null) {
            return null;
        }
        
        CaminhaoRequestDTO requestDTO = new CaminhaoRequestDTO();
        requestDTO.setPlaca(responseDTO.getPlaca());
        requestDTO.setNomeMotorista(responseDTO.getNomeMotorista());
        requestDTO.setCapacidade(responseDTO.getCapacidade());
        if (responseDTO.getTipoResiduos() != null) {
            requestDTO.setTipoResiduos(new ArrayList<>(responseDTO.getTipoResiduos()));
        }
        
        return requestDTO;
    }

    public static CaminhaoRequestDTOBuilder builder() {
        return new CaminhaoRequestDTOBuilder();
    }

    public static class CaminhaoRequestDTOBuilder {
        private String placa;
        private String nomeMotorista;
        private Double capacidade;
        private List<TipoResiduo> tipoResiduos;

        private CaminhaoRequestDTOBuilder() {
            this.tipoResiduos = new ArrayList<>();
        }

        public CaminhaoRequestDTOBuilder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public CaminhaoRequestDTOBuilder nomeMotorista(String nomeMotorista) {
            this.nomeMotorista = nomeMotorista;
            return this;
        }

        public CaminhaoRequestDTOBuilder capacidade(Double capacidade) {
            this.capacidade = capacidade;
            return this;
        }

        public CaminhaoRequestDTOBuilder tipoResiduos(List<TipoResiduo> tipoResiduos) {
            this.tipoResiduos = tipoResiduos != null ? tipoResiduos : new ArrayList<>();
            return this;
        }

        public CaminhaoRequestDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            if (this.tipoResiduos == null) {
                this.tipoResiduos = new ArrayList<>();
            }
            if (tipoResiduo != null) {
                this.tipoResiduos.add(tipoResiduo);
            }
            return this;
        }

        public CaminhaoRequestDTO build() {
            return new CaminhaoRequestDTO(placa, nomeMotorista, capacidade, tipoResiduos);
        }
    }
}