package com.labs.starwars.infrastructure.apis;

import com.labs.starwars.domain.models.external.StarWarsResponse;
import feign.RequestInterceptor;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 */
@FeignClient(name = "swapi", url = "${start.wars.url:https://swapi.co/api}", configuration = StarWarsAPI.Config.class)
public interface StarWarsAPI {
    @RequestMapping(method = GET, value = "planets/")
    StarWarsResponse search(@RequestParam(value = "search") final String name);

    @Configuration
    class Config {
        @Bean
        public RequestInterceptor userAgent() {
            return template -> template.header("User-Agent", "may the force be with you!");
        }
    }
}
