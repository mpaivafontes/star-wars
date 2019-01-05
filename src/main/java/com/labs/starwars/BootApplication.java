package com.labs.starwars;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import static org.springframework.boot.SpringApplication.run;

@EnableFeignClients
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        run(BootApplication.class, args);
    }
}

