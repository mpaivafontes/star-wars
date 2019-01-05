package com.labs.starwars.infrastructure.factories.logging;

import com.labs.starwars.domain.models.internal.Request;
import net.logstash.logback.marker.LogstashMarker;

import static com.labs.starwars.infrastructure.factories.logging.BasicLoggingFactory.name;
import static net.logstash.logback.marker.Markers.append;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class RequestLoggingFactory {
    public static LogstashMarker request(final Request request) {
        return name(request.getName())
                .and(append("terrain", request.getTerrain()))
                .and(append("climate", request.getClimate()));
    }
}
