package com.grupo5.AlquilerEquiposConstruccion.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher(HttpMethod.GET, "/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }


}
