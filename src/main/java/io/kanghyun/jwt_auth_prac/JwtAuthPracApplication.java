package io.kanghyun.jwt_auth_prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JwtAuthPracApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthPracApplication.class, args);
    }

}
