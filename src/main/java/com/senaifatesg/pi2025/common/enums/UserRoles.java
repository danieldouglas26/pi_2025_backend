package com.senaifatesg.pi2025.common.enums;

public enum UserRoles {
    ADMIN("ROLE_ADMIN"),         
    COLLECTOR("ROLE_COLLECTOR"),  
    OPERATOR("ROLE_OPERATOR");    

    private final String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
