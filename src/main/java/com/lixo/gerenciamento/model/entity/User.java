package com.lixo.gerenciamento.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lixo.gerenciamento.model.enums.UserRoles;
import com.lixo.gerenciamento.model.interfaces.Builder;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.roles = builder.roles != null ? builder.roles : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
    }


    public void addRole(String role) {
        if (role != null && !role.trim().isEmpty()) {
            getRoles().add(role);
        }
    }

    public void addRole(UserRoles role) {
        if (role != null) {
            addRole(role.getRoleName());
        }
    }

    public void removeRole(String role) {
        if (roles != null && role != null) {
            roles.remove(role);
        }
    }

    public void removeRole(UserRoles role) {
        if (role != null) {
            removeRole(role.getRoleName());
        }
    }

    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    public boolean hasRole(UserRoles role) {
        return role != null && hasRole(role.getRoleName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
    
  public static  class UserBuilder implements Builder<User> {
        private Long id;
        private String username;
        private String password;
        private List<String> roles;
        
        public UserBuilder() {

        }
        
        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }
        
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public UserBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }
        
        public UserBuilder role(String role) {
            if (this.roles == null) {
                this.roles = new ArrayList<>();
            }
            if (role != null && !role.trim().isEmpty()) {
                this.roles.add(role);
            }
            return this;
        }
        
        public UserBuilder role(UserRoles userRole) {
            if (userRole != null) {
                role(userRole.getRoleName());
            }
            return this;
        }
        
        public UserBuilder withDefaultRole() {
            role(UserRoles.OPERATOR);
            return this;
        }
        
        public UserBuilder withAdminRole() {
            role(UserRoles.ADMIN );
            return this;
        }
        
        @Override
        public User build() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username não pode ser nulo ou vazio");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password não pode ser nulo ou vazio");
            }
            
            if (password.length() < 6) {
                throw new IllegalArgumentException("Password deve ter pelo menos 6 caracteres");
            }
            
            if (roles == null || roles.isEmpty()) {
                roles = new ArrayList<>();
                roles.add(UserRoles.OPERATOR.getRoleName());
            }
            
            if (roles != null) {
                Set<String> uniqueRoles = new HashSet<>(roles);
                if (uniqueRoles.size() < roles.size()) {
                    roles = new ArrayList<>(uniqueRoles);
                }
            }
            
            return new User(this);
        }
    }
}



