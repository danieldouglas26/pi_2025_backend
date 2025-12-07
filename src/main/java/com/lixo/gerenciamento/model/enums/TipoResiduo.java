package com.lixo.gerenciamento.model.enums;

public enum TipoResiduo {
    PLASTICO("PLASTICO"),
    PAPEL("PAPEL"),
    METAL("METAL"),
    ORGANICO("ORGANICO"),
    VIDRO("VIDRO"),
    OUTROS("OUTROS");
	
	  

    private final String tipoResiduoNome;

    TipoResiduo(String tipoResiduoNome) {
        this.tipoResiduoNome = tipoResiduoNome;
    }

    public String getNome() {
        return tipoResiduoNome;
    }
}
