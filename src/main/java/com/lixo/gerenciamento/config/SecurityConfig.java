package com.lixo.gerenciamento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()
                
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.POST, "/api/caminhoes/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers(HttpMethod.PUT, "/api/caminhoes/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/caminhoes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/caminhoes/**").hasAnyRole("ADMIN", "GERENTE", "COLETOR")
                
                .requestMatchers(HttpMethod.POST, "/api/pontos-coleta/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers(HttpMethod.PUT, "/api/pontos-coleta/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/pontos-coleta/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pontos-coleta/**").hasAnyRole("ADMIN", "GERENTE", "COLETOR", "VISITANTE")
                
                .requestMatchers(HttpMethod.POST, "/api/rotas/**").hasAnyRole("ADMIN", "GERENTE", "COLETOR")
                .requestMatchers(HttpMethod.GET, "/api/rotas/**").hasAnyRole("ADMIN", "GERENTE", "COLETOR")
                
                .requestMatchers("/api/planejamento/**").hasAnyRole("ADMIN", "GERENTE", "COLETOR")
                
                .requestMatchers("/api/consultas/**").hasAnyRole("ADMIN", "GERENTE")
                
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}