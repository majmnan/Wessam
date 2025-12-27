package com.example.wessam.Configue;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        return security
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authorizeHttpRequests( auth -> auth
                        .anyRequest().permitAll()
                )

                .logout( logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )

                .httpBasic(basic -> {})

                .build();

    }

}
