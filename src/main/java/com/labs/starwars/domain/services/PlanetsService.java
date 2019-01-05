package com.labs.starwars.domain.services;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.routers.Planet;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
public interface PlanetsService {
    void save(Request request);

    void delete(String name);

    List<Planet> findById(String id);

    List<Planet> findByName(String name);
}
