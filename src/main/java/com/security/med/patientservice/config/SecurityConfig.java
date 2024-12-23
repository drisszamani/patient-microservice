package com.security.med.patientservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/register").permitAll()  // Allow public access to login/register
                        .requestMatchers("/patients").authenticated()  // Only authenticated users can access /patients
                        .anyRequest().authenticated()  // Other requests also need authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Spring Security handles this login page
                        .loginProcessingUrl("/login")  // URL where the login form is submitted
                        .defaultSuccessUrl("/patients", true)  // Redirect to /patients after successful login
                        .failureUrl("/login?error=true")  // Redirect to login with error if login fails
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()  // Allow anyone to log out
                );
    }
}