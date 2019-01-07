package com.labs.starwars;

import com.labs.starwars.domain.models.external.Film;
import com.labs.starwars.domain.models.external.StarWarsResponse;
import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.entity.Planet;

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
        return singletonList(planet(isEmpty));
    }

    public static Planet planet() {
        return planet(false);
    }

    public static Planet planet(boolean isEmpty) {
        final Planet planet = new Planet();
        planet.setId("id");
        planet.setName("name");
        planet.setClimate("climate");
        planet.setTerrain("terrain");
        planet.setFilms(isEmpty ? emptyList() : singletonList("film"));
        return planet;
    }

    ///////////////////////////////////

    public static StarWarsResponse starWars() {
        return starWars(false);
    }

    public static StarWarsResponse starWars(boolean isEmpty) {
        final StarWarsResponse res = new StarWarsResponse();
        res.setResults(film(isEmpty));
        return res;
    }

    private static List<Film> film(boolean isEmpty) {
        if (isEmpty) {
            return emptyList();
        }
        final Film film = new Film();
        film.setFilms(singletonList("film"));
        return singletonList(film);
    }


    ///////////////////////////////////

    public static Throwable exception() {
        return new RuntimeException("errorMessage");
    }
}
