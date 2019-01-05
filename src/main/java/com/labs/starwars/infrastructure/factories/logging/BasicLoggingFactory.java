package com.labs.starwars.infrastructure.factories.logging;

import com.labs.starwars.domain.models.internal.Event;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.util.StopWatch;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;
import static net.logstash.logback.marker.Markers.append;
import static net.logstash.logback.marker.Markers.appendEntries;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class BasicLoggingFactory {
    public static LogstashMarker id(final String id) {
        return append("id", id);
    }

    public static LogstashMarker name(final String name) {
        return append("name", name);
    }

    public static LogstashMarker event(final Event event) {
        return append("event", event);
    }

    public static LogstashMarker time(final StopWatch watch) {
        if (watch.isRunning()) watch.stop();
        return appendEntries(of(watch.getTaskInfo()).collect(toMap(StopWatch.TaskInfo::getTaskName, StopWatch.TaskInfo::getTimeMillis)));
    }
}
