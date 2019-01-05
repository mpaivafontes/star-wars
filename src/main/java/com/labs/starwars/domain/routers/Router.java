package com.labs.starwars.domain.routers;

import com.labs.starwars.domain.models.internal.Event;
import com.labs.starwars.domain.models.internal.Request;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
public interface Router {
    void execute(String name, Request request, Event action);
}
