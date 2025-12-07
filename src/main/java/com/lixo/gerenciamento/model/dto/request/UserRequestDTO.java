package com.lixo.gerenciamento.model.dto.request;

import com.lixo.gerenciamento.model.enums.UserRoles;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO extends LoginRequest {
    
    @NotBlank(message = "É preciso informar o cargo")
    private UserRoles role;

    public UserRequestDTO() {
        super();
    }


    public UserRequestDTO(String username, String password, UserRoles role) {
        super(username, password);
        this.role = role;
    }

    public UserRequestDTO(UserRoles role) {
        super();
        this.role = role;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        
        UserRequestDTO that = (UserRequestDTO) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "username='" + getUsername() + '\'' +
                ", password='***MASKED***'" +
                ", role=" + role +
                '}';
    }
}
