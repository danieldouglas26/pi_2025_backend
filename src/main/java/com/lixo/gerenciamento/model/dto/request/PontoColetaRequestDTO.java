package com.lixo.gerenciamento.model.dto.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.lixo.gerenciamento.model.dto.response.PontoColetaResponseDTO;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.enums.TipoResiduo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PontoColetaRequestDTO {

    @NotBlank(message = "O nome do ponto de coleta é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "O ID do bairro é obrigatório")
    private Long idBairro;

    @NotBlank(message = "O nome do responsável é obrigatório")
    @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres")
    private String nomeResponsavel;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um formato de email válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\+?[\\d\\s()-]{10,}$", message = "Informe um telefone válido")
    private String telefone;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String endereco;

    private String horarioFuncionamento;

    @NotNull(message = "Pelo menos um tipo de resíduo deve ser informado")
    @Size(min = 1, message = "Deve haver pelo menos um tipo de resíduo")
    private List<String> tiposDeResiduo;

    // Construtor padrão (no-args)
    public PontoColetaRequestDTO() {
        this.tiposDeResiduo = new ArrayList<>();
    }

    // Construtor com todos os campos
    public PontoColetaRequestDTO(String nome, Long idBairro, String nomeResponsavel, 
                                String email, String telefone, String endereco,
                                String horarioFuncionamento, List<String> tiposDeResiduo) {
        this.nome = nome;
        this.idBairro = idBairro;
        this.nomeResponsavel = nomeResponsavel;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.tiposDeResiduo = tiposDeResiduo != null ? tiposDeResiduo : new ArrayList<>();
    }

    // Construtor simplificado (sem horário de funcionamento)
    public PontoColetaRequestDTO(String nome, Long idBairro, String nomeResponsavel,
                                String email, String telefone, String endereco,
                                List<String> tiposDeResiduo) {
        this(nome, idBairro, nomeResponsavel, email, telefone, endereco, null, tiposDeResiduo);
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Long idBairro) {
        this.idBairro = idBairro;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public List<String> getTiposDeResiduo() {
        if (tiposDeResiduo == null) {
            tiposDeResiduo = new ArrayList<>();
        }
        return tiposDeResiduo;
    }

    public void setTiposDeResiduo(List<String> tiposDeResiduo) {
        this.tiposDeResiduo = tiposDeResiduo != null ? tiposDeResiduo : new ArrayList<>();
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PontoColetaRequestDTO that = (PontoColetaRequestDTO) o;
        
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (idBairro != null ? !idBairro.equals(that.idBairro) : that.idBairro != null) return false;
        if (nomeResponsavel != null ? !nomeResponsavel.equals(that.nomeResponsavel) : that.nomeResponsavel != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telefone != null ? !telefone.equals(that.telefone) : that.telefone != null) return false;
        if (endereco != null ? !endereco.equals(that.endereco) : that.endereco != null) return false;
        if (horarioFuncionamento != null ? !horarioFuncionamento.equals(that.horarioFuncionamento) : that.horarioFuncionamento != null) return false;
        return tiposDeResiduo != null ? tiposDeResiduo.equals(that.tiposDeResiduo) : that.tiposDeResiduo == null;
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (idBairro != null ? idBairro.hashCode() : 0);
        result = 31 * result + (nomeResponsavel != null ? nomeResponsavel.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (horarioFuncionamento != null ? horarioFuncionamento.hashCode() : 0);
        result = 31 * result + (tiposDeResiduo != null ? tiposDeResiduo.hashCode() : 0);
        return result;
    }

    // Método toString (sem informações sensíveis completas)
    @Override
    public String toString() {
        return "PontoColetaRequestDTO{" +
                "nome='" + nome + '\'' +
                ", idBairro=" + idBairro +
                ", nomeResponsavel='" + nomeResponsavel + '\'' +
                ", email='" + (email != null ? "***@***" : "null") + '\'' +
                ", telefone='" + (telefone != null ? "***-****" : "null") + '\'' +
                ", endereco='" + endereco + '\'' +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", tiposDeResiduo=" + tiposDeResiduo +
                '}';
    }

    // Métodos utilitários
    public void addTipoResiduo(String tipoResiduo) {
        if (tipoResiduo != null && !tipoResiduo.trim().isEmpty()) {
            getTiposDeResiduo().add(tipoResiduo.trim());
        }
    }

    public void addTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            addTipoResiduo(tipoResiduo.name());
        }
    }

    public void removeTipoResiduo(String tipoResiduo) {
        if (tiposDeResiduo != null && tipoResiduo != null) {
            tiposDeResiduo.remove(tipoResiduo);
        }
    }

    public void removeTipoResiduo(TipoResiduo tipoResiduo) {
        if (tipoResiduo != null) {
            removeTipoResiduo(tipoResiduo.name());
        }
    }

    public boolean hasTipoResiduo(String tipoResiduo) {
        return tiposDeResiduo != null && tiposDeResiduo.contains(tipoResiduo);
    }

    public boolean hasTipoResiduo(TipoResiduo tipoResiduo) {
        return tipoResiduo != null && hasTipoResiduo(tipoResiduo.name());
    }

    public boolean hasTiposDeResiduo() {
        return tiposDeResiduo != null && !tiposDeResiduo.isEmpty();
    }

    public boolean isValid() {
        return nome != null && !nome.trim().isEmpty() && nome.length() <= 100 &&
               idBairro != null &&
               nomeResponsavel != null && !nomeResponsavel.trim().isEmpty() && nomeResponsavel.length() <= 100 &&
               email != null && !email.trim().isEmpty() && isValidEmail(email) &&
               telefone != null && !telefone.trim().isEmpty() && isValidTelefone(telefone) &&
               endereco != null && !endereco.trim().isEmpty() && endereco.length() <= 200 &&
               hasTiposDeResiduo();
    }

    public String getNomeFormatado() {
        if (nome == null) {
            return "";
        }
        // Capitaliza a primeira letra de cada palavra
        return Arrays.stream(nome.trim().split("\\s+"))
                .map(palavra -> palavra.length() > 0 ? 
                    Character.toUpperCase(palavra.charAt(0)) + palavra.substring(1).toLowerCase() : "")
                .collect(Collectors.joining(" "));
    }

    public String getNomeResponsavelFormatado() {
        if (nomeResponsavel == null) {
            return "";
        }
        return Arrays.stream(nomeResponsavel.trim().split("\\s+"))
                .map(palavra -> palavra.length() > 0 ? 
                    Character.toUpperCase(palavra.charAt(0)) + palavra.substring(1).toLowerCase() : "")
                .collect(Collectors.joining(" "));
    }

    public String getTelefoneFormatado() {
        if (telefone == null || telefone.trim().isEmpty()) {
            return "";
        }
        String telefoneLimpo = telefone.replaceAll("[^\\d]", "");
        if (telefoneLimpo.length() == 11) {
            return String.format("(%s) %s-%s", 
                telefoneLimpo.substring(0, 2),
                telefoneLimpo.substring(2, 7),
                telefoneLimpo.substring(7));
        } else if (telefoneLimpo.length() == 10) {
            return String.format("(%s) %s-%s", 
                telefoneLimpo.substring(0, 2),
                telefoneLimpo.substring(2, 6),
                telefoneLimpo.substring(6));
        }
        return telefone;
    }

    public boolean aceitaTipoResiduo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        return hasTipoResiduo(tipo.trim());
    }

    public boolean aceitaTipoResiduo(TipoResiduo tipo) {
        return tipo != null && aceitaTipoResiduo(tipo.name());
    }

    public boolean aceitaTodosTipos(List<String> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposDeResiduo()) {
            return false;
        }
        return tipos.stream().allMatch(this::aceitaTipoResiduo);
    }

    public boolean aceitaAlgumTipo(List<String> tipos) {
        if (tipos == null || tipos.isEmpty() || !hasTiposDeResiduo()) {
            return false;
        }
        return tipos.stream().anyMatch(this::aceitaTipoResiduo);
    }

    // Métodos auxiliares de validação
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isValidTelefone(String telefone) {
        // Remove todos os não-dígitos e verifica se tem pelo menos 10 dígitos
        String digitos = telefone.replaceAll("[^\\d]", "");
        return digitos.length() >= 10;
    }

    // Métodos de fábrica
    public static PontoColetaRequestDTO of(String nome, Long idBairro, String nomeResponsavel,
                                          String email, String telefone, String endereco,
                                          String horarioFuncionamento, List<String> tiposDeResiduo) {
        return new PontoColetaRequestDTO(nome, idBairro, nomeResponsavel, email, telefone, 
                                       endereco, horarioFuncionamento, tiposDeResiduo);
    }

    public static PontoColetaRequestDTO fromEntity(PontoColeta pontoColeta) {
        if (pontoColeta == null) {
            return null;
        }
        
        PontoColetaRequestDTO dto = new PontoColetaRequestDTO();
        dto.setNome(pontoColeta.getNome());
        dto.setNomeResponsavel(pontoColeta.getNomeResponsavel());
        dto.setEmail(pontoColeta.getEmail());
        dto.setTelefone(pontoColeta.getTelefone());
        dto.setEndereco(pontoColeta.getEndereco());
        dto.setHorarioFuncionamento(pontoColeta.getHorarioFuncionamento());
        
        if (pontoColeta.getBairro() != null) {
            dto.setIdBairro(pontoColeta.getBairro().getId());
        }
        
        // Converter tipos de resíduo do enum para strings
        if (pontoColeta.getTiposDeResiduo() != null) {
            List<String> tiposString = pontoColeta.getTiposDeResiduo().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList());
            dto.setTiposDeResiduo(tiposString);
        }
        
        return dto;
    }

    public static PontoColetaRequestDTO fromResponseDTO(PontoColetaResponseDTO responseDTO) {
        if (responseDTO == null) {
            return null;
        }
        
        PontoColetaRequestDTO dto = new PontoColetaRequestDTO();
        dto.setNome(responseDTO.getNome());
        dto.setEndereco(responseDTO.getEndereco());
        dto.setHorarioFuncionamento(responseDTO.getHorarioFuncionamento());
        dto.setIdBairro(responseDTO.getIdBairro());
        
        if (responseDTO.getTiposResiduo() != null) {
            dto.setTiposDeResiduo(new ArrayList<>(responseDTO.getTiposResiduo()));
        }
        
        return dto;
    }

    // Builder Pattern (opcional)
    public static PontoColetaRequestDTOBuilder builder() {
        return new PontoColetaRequestDTOBuilder();
    }

    public static class PontoColetaRequestDTOBuilder {
        private String nome;
        private Long idBairro;
        private String nomeResponsavel;
        private String email;
        private String telefone;
        private String endereco;
        private String horarioFuncionamento;
        private List<String> tiposDeResiduo;

        private PontoColetaRequestDTOBuilder() {
            this.tiposDeResiduo = new ArrayList<>();
        }

        public PontoColetaRequestDTOBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public PontoColetaRequestDTOBuilder idBairro(Long idBairro) {
            this.idBairro = idBairro;
            return this;
        }

        public PontoColetaRequestDTOBuilder nomeResponsavel(String nomeResponsavel) {
            this.nomeResponsavel = nomeResponsavel;
            return this;
        }

        public PontoColetaRequestDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PontoColetaRequestDTOBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public PontoColetaRequestDTOBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public PontoColetaRequestDTOBuilder horarioFuncionamento(String horarioFuncionamento) {
            this.horarioFuncionamento = horarioFuncionamento;
            return this;
        }

        public PontoColetaRequestDTOBuilder tiposDeResiduo(List<String> tiposDeResiduo) {
            this.tiposDeResiduo = tiposDeResiduo != null ? tiposDeResiduo : new ArrayList<>();
            return this;
        }

        public PontoColetaRequestDTOBuilder tipoDeResiduo(String tipoResiduo) {
            if (this.tiposDeResiduo == null) {
                this.tiposDeResiduo = new ArrayList<>();
            }
            if (tipoResiduo != null && !tipoResiduo.trim().isEmpty()) {
                this.tiposDeResiduo.add(tipoResiduo.trim());
            }
            return this;
        }

        public PontoColetaRequestDTOBuilder tipoDeResiduo(TipoResiduo tipoResiduo) {
            if (tipoResiduo != null) {
                tipoDeResiduo(tipoResiduo.name());
            }
            return this;
        }

        public PontoColetaRequestDTO build() {
            // Validações básicas
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do ponto de coleta não pode ser nulo ou vazio");
            }
            if (nome.length() > 100) {
                throw new IllegalArgumentException("Nome do ponto de coleta deve ter no máximo 100 caracteres");
            }
            if (idBairro == null) {
                throw new IllegalArgumentException("ID do bairro não pode ser nulo");
            }
            if (nomeResponsavel == null || nomeResponsavel.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do responsável não pode ser nulo ou vazio");
            }
            if (nomeResponsavel.length() > 100) {
                throw new IllegalArgumentException("Nome do responsável deve ter no máximo 100 caracteres");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
            }
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email informado é inválido");
            }
            if (telefone == null || telefone.trim().isEmpty()) {
                throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
            }
            if (!isValidTelefone(telefone)) {
                throw new IllegalArgumentException("Telefone informado é inválido");
            }
            if (endereco == null || endereco.trim().isEmpty()) {
                throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
            }
            if (endereco.length() > 200) {
                throw new IllegalArgumentException("Endereço deve ter no máximo 200 caracteres");
            }
            if (tiposDeResiduo == null || tiposDeResiduo.isEmpty()) {
                throw new IllegalArgumentException("Pelo menos um tipo de resíduo deve ser informado");
            }
            
            return new PontoColetaRequestDTO(nome, idBairro, nomeResponsavel, email, telefone,
                                           endereco, horarioFuncionamento, tiposDeResiduo);
        }
        
        private boolean isValidEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            return email != null && email.matches(emailRegex);
        }
        
        private boolean isValidTelefone(String telefone) {
            String digitos = telefone.replaceAll("[^\\d]", "");
            return digitos.length() >= 10;
        }
    }
}