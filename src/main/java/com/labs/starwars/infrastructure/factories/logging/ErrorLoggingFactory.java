package com.labs.starwars.infrastructure.factories.logging;

import net.logstash.logback.marker.LogstashMarker;

import static net.logstash.logback.marker.Markers.append;

/**
 * @author - marcelo.fontes
 * @since - 12/18/18
 **/
public final class ErrorLoggingFactory {
    public static LogstashMarker error(final Exception ex) {
        return append("errorMessage", ex.getMessage());
    }
}
