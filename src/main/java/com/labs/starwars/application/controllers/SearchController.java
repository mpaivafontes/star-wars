package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.routers.Planet;
import com.labs.starwars.domain.services.PlanetsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.labs.starwars.infrastructure.factories.entity.ResponseFactory.internalError;
import static com.labs.starwars.infrastructure.factories.entity.ResponseFactory.ok;
import static com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory.*;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j
@RestController
@AllArgsConstructor
public class SearchController {
    private final PlanetsService service;

    @GetMapping(value = "v1/planets/{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") final String id) {
        final StopWatch watch = new StopWatch();

        try {
            watch.start("responseTime");
            final List<Planet> planets = service.findById(id);
            watch.stop();

            log.info(id(id), "Search finished. id[{}]", id);

            return ok(planets);
        } catch (final Exception ex) {
            log.error(id(id).and(time(watch)), "Search finished with error. id[{}]", id, ex);

            return internalError(ex);
        }
    }

    @GetMapping(value = "v1/planets")
    public ResponseEntity<Response> findByName(@RequestParam("name") final String name) {
        final StopWatch watch = new StopWatch();

        try {
            watch.start("responseTime");
            final List<Planet> planets = service.findByName(name);
            watch.stop();

            log.info(name(name), "Search finished. name[{}]", name);

            return ok(planets);
        } catch (final Exception ex) {
            log.error(name(name).and(time(watch)), "Search finished with error. name[{}]", name, ex);

            return internalError(ex);
        }
    }
}
