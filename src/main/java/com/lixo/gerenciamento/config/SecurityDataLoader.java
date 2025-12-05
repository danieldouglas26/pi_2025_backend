package com.lixo.gerenciamento.config;

import com.lixo.gerenciamento.model.entity.Usuario;
import com.lixo.gerenciamento.model.enuns.Role;
import com.lixo.gerenciamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityDataLoader implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${app.admin.email:admin@greenlog.com}")
    private String adminEmail;
    
    @Value("${app.admin.senha:Admin123!}")
    private String adminSenha;
    
    @Value("${app.admin.nome:Administrador do Sistema}")
    private String adminNome;
    
    @Override
    public void run(String... args) throws Exception {
        criarUsuarioAdmin();
    }
    
    private void criarUsuarioAdmin() {
        if (!usuarioRepository.existsByEmail(adminEmail)) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_ADMIN);
            roles.add(Role.ROLE_GERENTE);
            
            Usuario admin = Usuario.builder()
                    .email(adminEmail)
                    .senha(passwordEncoder.encode(adminSenha))
                    .nome(adminNome)
                    .roles(roles)
                    .build();
            
            usuarioRepository.save(admin);
            System.out.println("✅ Usuário administrador criado: " + adminEmail);
        }
    }
}
