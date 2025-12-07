package com.lixo.gerenciamento.model.dto.request;

import com.lixo.gerenciamento.model.enums.TipoResiduo;

import jakarta.validation.constraints.NotNull;

public class RotaRequestDTO {
    
    @NotNull(message = "ID do caminhão é obrigatório")
    private Long caminhaoId;
    
    @NotNull(message = "ID do bairro de origem é obrigatório")
    private Long origemId;
    
    @NotNull(message = "ID do bairro de destino é obrigatório")
    private Long destinoId;
    
    @NotNull(message = "Tipo de resíduo é obrigatório")
    private TipoResiduo tipoResiduo;
    
    private String nome;

    public RotaRequestDTO() {
    }

    public RotaRequestDTO(Long caminhaoId, Long origemId, Long destinoId, 
                         TipoResiduo tipoResiduo, String nome) {
        this.caminhaoId = caminhaoId;
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.tipoResiduo = tipoResiduo;
        this.nome = nome;
    }

    public RotaRequestDTO(Long caminhaoId, Long origemId, Long destinoId, TipoResiduo tipoResiduo) {
        this(caminhaoId, origemId, destinoId, tipoResiduo, null);
    }

    public RotaRequestDTO(Long origemId, Long destinoId, TipoResiduo tipoResiduo) {
        this(null, origemId, destinoId, tipoResiduo, null);
    }

    public Long getCaminhaoId() {
        return caminhaoId;
    }

    public void setCaminhaoId(Long caminhaoId) {
        this.caminhaoId = caminhaoId;
    }

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

    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RotaRequestDTO that = (RotaRequestDTO) o;
        
        if (caminhaoId != null ? !caminhaoId.equals(that.caminhaoId) : that.caminhaoId != null) return false;
        if (origemId != null ? !origemId.equals(that.origemId) : that.origemId != null) return false;
        if (destinoId != null ? !destinoId.equals(that.destinoId) : that.destinoId != null) return false;
        if (tipoResiduo != that.tipoResiduo) return false;
        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        int result = caminhaoId != null ? caminhaoId.hashCode() : 0;
        result = 31 * result + (origemId != null ? origemId.hashCode() : 0);
        result = 31 * result + (destinoId != null ? destinoId.hashCode() : 0);
        result = 31 * result + (tipoResiduo != null ? tipoResiduo.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RotaRequestDTO{" +
                "caminhaoId=" + caminhaoId +
                ", origemId=" + origemId +
                ", destinoId=" + destinoId +
                ", tipoResiduo=" + tipoResiduo +
                ", nome='" + nome + '\'' +
                '}';
    }

    public boolean isValid() {
        return caminhaoId != null &&
               origemId != null &&
               destinoId != null &&
               tipoResiduo != null &&
               !origemId.equals(destinoId); // Origem e destino não podem ser o mesmo
    }

    public boolean hasNome() {
        return nome != null && !nome.trim().isEmpty();
    }

    public String getNomeOrDefault() {
        if (hasNome()) {
            return nome;
        }
        return gerarNomeAutomatico();
    }

    public String gerarNomeAutomatico() {
        StringBuilder nomeGerado = new StringBuilder();
        nomeGerado.append("Rota ");
        
        if (tipoResiduo != null) {
            nomeGerado.append(tipoResiduo.name().toLowerCase());
            nomeGerado.append(" ");
        }
        
        nomeGerado.append(origemId);
        nomeGerado.append("-");
        nomeGerado.append(destinoId);
        
        if (caminhaoId != null) {
            nomeGerado.append(" (Caminhão ");
            nomeGerado.append(caminhaoId);
            nomeGerado.append(")");
        }
        
        return nomeGerado.toString();
    }

    public boolean isRotaValida() {
        return isValid() && !origemId.equals(destinoId);
    }

    public boolean isMesmaRota(RotaRequestDTO outraRota) {
        if (outraRota == null) {
            return false;
        }
        return (origemId != null && destinoId != null &&
                origemId.equals(outraRota.getOrigemId()) && 
                destinoId.equals(outraRota.getDestinoId())) ||
               (origemId != null && destinoId != null &&
                origemId.equals(outraRota.getDestinoId()) && 
                destinoId.equals(outraRota.getOrigemId()));
    }

    public RotaRequestDTO criarReverso() {
        return new RotaRequestDTO(
            caminhaoId,
            destinoId, 
            origemId, 
            tipoResiduo,
            nome != null ? nome + " (Reverso)" : null
        );
    }

    public boolean conectaBairros(Long bairro1Id, Long bairro2Id) {
        if (bairro1Id == null || bairro2Id == null) {
            return false;
        }
        return (origemId != null && destinoId != null) &&
               ((origemId.equals(bairro1Id) && destinoId.equals(bairro2Id)) ||
                (origemId.equals(bairro2Id) && destinoId.equals(bairro1Id)));
    }

    public static RotaRequestDTO of(Long caminhaoId, Long origemId, Long destinoId, 
                                   TipoResiduo tipoResiduo, String nome) {
        return new RotaRequestDTO(caminhaoId, origemId, destinoId, tipoResiduo, nome);
    }

    public static RotaRequestDTO of(Long caminhaoId, Long origemId, Long destinoId, 
                                   TipoResiduo tipoResiduo) {
        return new RotaRequestDTO(caminhaoId, origemId, destinoId, tipoResiduo);
    }





    public static RotaRequestDTOBuilder builder() {
        return new RotaRequestDTOBuilder();
    }

    public static class RotaRequestDTOBuilder {
        private Long caminhaoId;
        private Long origemId;
        private Long destinoId;
        private TipoResiduo tipoResiduo;
        private String nome;

        private RotaRequestDTOBuilder() {
        }

        public RotaRequestDTOBuilder caminhaoId(Long caminhaoId) {
            this.caminhaoId = caminhaoId;
            return this;
        }

        public RotaRequestDTOBuilder origemId(Long origemId) {
            this.origemId = origemId;
            return this;
        }

        public RotaRequestDTOBuilder destinoId(Long destinoId) {
            this.destinoId = destinoId;
            return this;
        }

        public RotaRequestDTOBuilder tipoResiduo(TipoResiduo tipoResiduo) {
            this.tipoResiduo = tipoResiduo;
            return this;
        }

        public RotaRequestDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public RotaRequestDTOBuilder nomeAutomatico() {
            return this;
        }

        public RotaRequestDTO build() {
            if (origemId == null) {
                throw new IllegalArgumentException("ID do bairro de origem é obrigatório");
            }
            if (destinoId == null) {
                throw new IllegalArgumentException("ID do bairro de destino é obrigatório");
            }
            if (origemId.equals(destinoId)) {
                throw new IllegalArgumentException("Origem e destino não podem ser o mesmo bairro");
            }
            if (tipoResiduo == null) {
                throw new IllegalArgumentException("Tipo de resíduo é obrigatório");
            }
            
            if (nome == null || nome.trim().isEmpty()) {
                nome = gerarNomeAutomatico();
            }
            
            return new RotaRequestDTO(caminhaoId, origemId, destinoId, tipoResiduo, nome);
        }
        
        private String gerarNomeAutomatico() {
            StringBuilder nomeGerado = new StringBuilder();
            nomeGerado.append("Rota ");
            
            if (tipoResiduo != null) {
                String tipoFormatado = tipoResiduo.name().toLowerCase();
                tipoFormatado = tipoFormatado.substring(0, 1).toUpperCase() + 
                               tipoFormatado.substring(1);
                nomeGerado.append(tipoFormatado);
                nomeGerado.append(" ");
            }
            
            nomeGerado.append(origemId);
            nomeGerado.append("-");
            nomeGerado.append(destinoId);
            
            if (caminhaoId != null) {
                nomeGerado.append(" (Caminhão ");
                nomeGerado.append(caminhaoId);
                nomeGerado.append(")");
            }
            
            return nomeGerado.toString();
        }
    }
}