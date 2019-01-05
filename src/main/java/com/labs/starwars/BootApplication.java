package com.labs.starwars;

import com.labs.starwars.domain.models.internal.entity.Planet;
import com.labs.starwars.infrastructure.repositories.PlanetRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import static org.springframework.boot.SpringApplication.run;

@EnableFeignClients
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        run(BootApplication.class, args);
    }

    @Bean
    @Profile("!it")
    public ApplicationRunner runner(final PlanetRepository repository) {
        return args -> {
            final Planet planet = new Planet();
            planet.setName("Terra");
            planet.setClimate("Calor");
            planet.setTerrain("Plano");

            repository.save(planet);
        };
    }
}

