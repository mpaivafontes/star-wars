package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.models.internal.entity.Planet;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.infrastructure.factories.logging.RequestLoggingFactory;
import com.labs.starwars.infrastructure.factories.response.ResponseFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.labs.starwars.domain.models.internal.Event.*;
import static com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory.*;
import static com.labs.starwars.infrastructure.factories.response.ResponseFactory.*;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@Slf4j(topic = "com.labs.Input")
@RestController
@AllArgsConstructor
public class PlanetController {
    private final PlanetsService service;

    @PostMapping("v1/planets")
    public ResponseEntity<Response> save(@RequestBody @Valid final Request request, final Errors errors) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = RequestLoggingFactory.request(request).and(event(save));

        try {
            if (errors.hasErrors()) {
                log.warn(marker, "Request is not valid.");

                return badRequest(errors);
            }

            watch.start("responseTime");
            service.save(request);
            watch.stop();

            log.info(marker.and(time(watch)), "Request has been processed.");

            return created();
        } catch (final Exception ex) {
            log.error(marker.and(time(watch)), "Error to process request.", ex);

            return internalError(ex);
        }
    }

    @DeleteMapping("v1/planets/{name}")
    public ResponseEntity<Response> delete(@PathVariable("name") final String name) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = name(name).and(event(delete));

        try {
            watch.start("responseTime");
            service.delete(name);
            watch.stop();

            log.info(marker.and(time(watch)), "Request has been processed.");

            return ResponseFactory.created();
        } catch (final Exception ex) {
            log.error(marker.and(time(watch)), "Error to process request.", ex);

            return internalError(ex);
        }
    }

    @GetMapping(value = "v1/planets/{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") final String id) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = id(id).and(event(list_by_id));

        try {
            watch.start("responseTime");
            final Planet planet = service.findById(id);
            watch.stop();

            log.info(marker.and(time(watch)), "Search finished. id[{}]", id);

            return success(planet);
        } catch (final Exception ex) {
            log.error(marker.and(time(watch)), "Search finished with error. id[{}]", id, ex);

            return internalError(ex);
        }
    }

    @GetMapping(value = "v1/planets")
    public ResponseEntity<Response> findByName(@RequestParam("name") final String name) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = name(name).and(event(list_by_name));

        try {
            watch.start("responseTime");
            final List<Planet> planets = service.findByName(name);
            watch.stop();

            log.info(marker.and(time(watch)), "Search finished. name[{}]", name);

            return success(planets);
        } catch (final Exception ex) {
            log.error(marker.and(time(watch)), "Search finished with error. name[{}]", name, ex);

            return internalError(ex);
        }
    }
}