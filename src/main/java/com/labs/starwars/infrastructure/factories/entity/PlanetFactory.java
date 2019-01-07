package com.labs.starwars.infrastructure.factories.entity;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.entity.Planet;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class PlanetFactory {
    public static Planet create(Request request) {
        final Planet planet = new Planet();
        planet.setName(request.getName());
        planet.setClimate(request.getClimate());
        planet.setTerrain(request.getTerrain());
        return planet;
    }
}
