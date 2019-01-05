package com.labs.starwars.infrastructure.apis;

import com.labs.starwars.domain.models.external.PlanetResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 */
@FeignClient(name = "${star.wars.name:planets}", url = "${start.wars.url:https://swapi.co/api/planets}")
public interface PlanetsAPI {
    @RequestMapping()
    ResponseEntity<PlanetResponse> search(@RequestParam(value = "search") final String name);
}
