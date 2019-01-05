package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.routers.Planet;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.infrastructure.factories.entity.ResponseFactory;
import com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j
@RestController
@AllArgsConstructor
public class SearchController {
    private final PlanetsService service;

    @GetMapping("v1/planets/{id}")
    public ResponseEntity<Response> search(@PathVariable("id") final String id) {
        final StopWatch watch = new StopWatch();

        try {

            watch.start("responseTime");
            final List<Planet> planets = service.findById(id);
            watch.stop();

            return null;
        } catch (final Exception ex) {
            log.error(BasicLoggingFactory.id(id).and(BasicLoggingFactory.time(watch)), "Search finished with error. id[{}]", id, ex);

            return ResponseFactory.internalError(ex);
        }
    }
}
