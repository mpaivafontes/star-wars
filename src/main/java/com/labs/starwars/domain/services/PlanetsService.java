package com.labs.starwars.domain.services;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.entity.Planet;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
public interface PlanetsService {
    void save(Request request);

    void delete(String name);

    Planet findById(String id);

    List<Planet> findAll();

    List<Planet> findByName(String name);
}
