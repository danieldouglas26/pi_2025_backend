package com.lixo.gerenciamento;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lixo.gerenciamento.model.entity.User;
import com.lixo.gerenciamento.model.enums.UserRoles;
import com.lixo.gerenciamento.repository.UserRepository;

@SpringBootApplication
public class GerenciamentoGreenLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoGreenLogApplication.class, args);
	}
	
    @Bean
    CommandLineRunner run(UserRepository userRepository , PasswordEncoder passwordEncoder ) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                String encodedPassword = passwordEncoder.encode("password123");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encodedPassword);
                admin.addRole(UserRoles.ADMIN);
                userRepository.save(admin);
                System.out.println("User 'admin' created with password 'password123'");
            }
        };
    }
}
