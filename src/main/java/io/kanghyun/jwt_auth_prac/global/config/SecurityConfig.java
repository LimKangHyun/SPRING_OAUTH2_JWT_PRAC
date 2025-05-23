package io.kanghyun.jwt_auth_prac.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpB -> httpB.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth -> {
                    oauth.successHandler(oAuth2SuccessHandler);
                })
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/user/**")
                                .hasAnyAuthority("ADMIN", "MEMBER")
                                .requestMatchers("/admin/**")
                                .hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .build();
    }
}