package com.labs.starwars;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.routers.Planet;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class TestFactory {
    public static Request req() {
        return req(true);
    }

    public static Request req(boolean isValid) {
        final Request req = new Request();
        if (isValid) {
            req.setName("name");
            req.setClimate("climate");
            req.setTerrain("terrain");
        }
        return req;
    }

    ///////////////////////////////////

    public static List<Planet> planets() {
        return planets(false);
    }

    public static List<Planet> planets(boolean isEmpty) {
        return isEmpty ? singletonList(planet()) : emptyList();
    }

    private static Planet planet() {
        final Planet planet = new Planet();
        planet.setName("name");
        planet.setClimate("climate");
        planet.setTerrain("terrain");
        planet.setFilms(singletonList("film"));
        return planet;
    }

    ///////////////////////////////////

    public static Throwable exception() {
        return new RuntimeException("errorMessage");
    }
}
