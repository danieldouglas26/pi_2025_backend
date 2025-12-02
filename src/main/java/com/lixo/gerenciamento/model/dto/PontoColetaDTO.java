package com.lixo.gerenciamento.model.dto;

import java.util.List;

import com.lixo.gerenciamento.model.entity.Bairro;

public class PontoColetaDTO {
    private Long id;
    private String nome;
    private String responsavel;
    private String contato;
    private String endereco;
    private Bairro bairro;
    private List<String> tiposResiduos;
    private String horarioFuncionamento;
    private Double capacidadeDiaria;
    
    public PontoColetaDTO() {}
    
    public PontoColetaDTO(Long id, String nome, String responsavel, String contato, String endereco,
                         Bairro bairro, List<String> tiposResiduos, String horarioFuncionamento, Double capacidadeDiaria) {
        this.id = id;
        this.nome = nome;
        this.responsavel = responsavel;
        this.contato = contato;
        this.endereco = endereco;
        this.bairro = bairro;
        this.tiposResiduos = tiposResiduos;
        this.horarioFuncionamento = horarioFuncionamento;
        this.capacidadeDiaria = capacidadeDiaria;
    }
    
    public static PontoColetaDTOBuilder builder() {
        return new PontoColetaDTOBuilder();
    }
    
    public static class PontoColetaDTOBuilder {
        private Long id;
        private String nome;
        private String responsavel;
        private String contato;
        private String endereco;
        private Bairro bairro;
        private List<String> tiposResiduos;
        private String horarioFuncionamento;
        private Double capacidadeDiaria;
        
        public PontoColetaDTOBuilder id(Long id) { this.id = id; return this; }
        public PontoColetaDTOBuilder nome(String nome) { this.nome = nome; return this; }
        public PontoColetaDTOBuilder responsavel(String responsavel) { this.responsavel = responsavel; return this; }
        public PontoColetaDTOBuilder contato(String contato) { this.contato = contato; return this; }
        public PontoColetaDTOBuilder endereco(String endereco) { this.endereco = endereco; return this; }
        public PontoColetaDTOBuilder bairro(Bairro bairro) { this.bairro = bairro; return this; }
        public PontoColetaDTOBuilder tiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; return this; }
        public PontoColetaDTOBuilder horarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; return this; }
        public PontoColetaDTOBuilder capacidadeDiaria(Double capacidadeDiaria) { this.capacidadeDiaria = capacidadeDiaria; return this; }
        
        public PontoColetaDTO build() {
            return new PontoColetaDTO(id, nome, responsavel, contato, endereco, bairro, 
                                    tiposResiduos, horarioFuncionamento, capacidadeDiaria);
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Bairro getBairro() { return bairro; }
    public void setBairro(Bairro bairro) { this.bairro = bairro; }
    public List<String> getTiposResiduos() { return tiposResiduos; }
    public void setTiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; }
    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }
    public Double getCapacidadeDiaria() { return capacidadeDiaria; }
    public void setCapacidadeDiaria(Double capacidadeDiaria) { this.capacidadeDiaria = capacidadeDiaria; }
}
