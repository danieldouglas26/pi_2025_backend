package com.lixo.gerenciamento.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "É preciso informar o nome de usuario")
    private String username;

    @NotBlank(message = "É preciso informar a senha")
    private String password;
    
    public LoginRequest() {
    	
    }
    
	public LoginRequest(@NotBlank(message = "É preciso informar o nome de usuario") String username,
			@NotBlank(message = "É preciso informar a senha") String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}
