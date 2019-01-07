package com.labs.starwars.domain.services.impl;

import com.labs.starwars.domain.models.external.StarWarsResponse;
import com.labs.starwars.domain.services.StarWarsService;
import com.labs.starwars.infrastructure.apis.StarWarsAPI;
import com.labs.starwars.infrastructure.factories.logging.ErrorLoggingFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import static com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory.name;
import static com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory.time;
import static com.labs.starwars.infrastructure.factories.logging.ErrorLoggingFactory.error;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j
@Service
@AllArgsConstructor
public class StarWarsServiceImpl implements StarWarsService {
    private final StarWarsAPI force;

    @Override
    @Cacheable("response.name")
    public StarWarsResponse execute(final String name) {
        final StopWatch watch = new StopWatch();

        final LogstashMarker marker = name(name);

        try {
            watch.start("responseTime");
            final StarWarsResponse response = force.search(name);
            watch.stop();

            log.info(marker.and(time(watch)), "Integration finished. name[{}]", name);

            return response;
        } catch (final Exception ex) {
            log.error(marker.and(time(watch)).and(error(ex)),
                    "Integration finished with error. name[{}]", name, ex);
            throw ex;
        }
    }
}
