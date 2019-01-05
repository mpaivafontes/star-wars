package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory;
import com.labs.starwars.infrastructure.factories.logging.RequestLoggingFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.labs.starwars.domain.models.internal.Event.save;
import static com.labs.starwars.infrastructure.factories.entity.ResponseFactory.*;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@Slf4j(topic = "com.b2w.Input")
@RestController
@AllArgsConstructor
public class SaveController {
    private final PlanetsService service;

    @PostMapping("v1/planets")
    public ResponseEntity<Response> save(@RequestBody @Valid final Request request, final Errors errors) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = RequestLoggingFactory.request(request).and(BasicLoggingFactory.event(save));

        try {
            if (errors.hasErrors()) {
                log.warn(marker, "Request is not valid.");

                return badRequest(errors);
            }

            watch.start("responseTime");
            service.save(request);
            watch.stop();

            log.info(marker.and(BasicLoggingFactory.time(watch)), "Request has been processed.");

            return created();
        } catch (final Exception ex) {
            log.error(marker.and(BasicLoggingFactory.time(watch)), "Error to process request.", ex);

            return internalError(ex);
        }
    }
}