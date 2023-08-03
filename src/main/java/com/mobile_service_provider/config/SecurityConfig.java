package com.mobile_service_provider.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers("/user/delete/{id}", "/user/update_credits", "/user/get/{id}","/user/get_all").hasRole("ADMIN")
                    .requestMatchers("/user/register_package ","/user/get_package", "/user/get_credits").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.POST, "/package/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/package/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/package/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/user/register", "/login").permitAll()

                )

                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
