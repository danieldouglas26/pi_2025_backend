package com.senaifatesg.pi2025.auth.models;

import com.senaifatesg.pi2025.common.enums.UserRoles;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO extends LoginRequest {
	@NotBlank(message = "É preciso informar o cargo")
	UserRoles role;
}
