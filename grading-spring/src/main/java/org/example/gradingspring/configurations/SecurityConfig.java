package org.example.gradingspring.configurations;

import org.example.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserService customUserService;
    private final PasswordService passwordService;

    @Autowired
    public SecurityConfig(CustomUserService customUserService, PasswordService passwordService) {
        this.customUserService = customUserService;
        this.passwordService = passwordService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyOwnPasswordEncoder(passwordService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/student/**").hasRole("STUDENT")
                .antMatchers("/instructor/**").hasRole("INSTRUCTOR")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .defaultSuccessUrl("/login/success", true)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureUrl("/login?e=true")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/login?d=true")
                .and()
                .userDetailsService(customUserService);
        return http.build();
    }
}
