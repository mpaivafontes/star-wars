package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.domain.models.internal.Event;
import com.labs.starwars.infrastructure.factories.entity.ResponseFactory;
import com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j(topic = "com.b2w.Input")
@RestController
@AllArgsConstructor
public class DeleteController {
    private final PlanetsService service;

    @DeleteMapping("v1/planets/{name}")
    public ResponseEntity<Response> delete(@PathVariable("name") final String name) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = BasicLoggingFactory.name(name).and(BasicLoggingFactory.event(Event.delete));

        try {
            watch.start("responseTime");
            service.delete(name);
            watch.stop();

            log.info(marker.and(BasicLoggingFactory.time(watch)), "Request has been processed.");

            return ResponseFactory.created();
        } catch (final Exception ex) {
            log.error(marker.and(BasicLoggingFactory.time(watch)), "Error to process request.", ex);

            return ResponseFactory.internalError(ex);
        }
    }
}
