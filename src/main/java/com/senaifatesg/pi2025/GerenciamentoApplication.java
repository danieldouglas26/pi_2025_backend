package com.senaifatesg.pi2025;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.senaifatesg.pi2025.common.enums.UserRoles;
import com.senaifatesg.pi2025.core.models.User;
import com.senaifatesg.pi2025.core.repositories.UserRepository;

@SpringBootApplication
public class GerenciamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoApplication.class, args);
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