package com.labs.starwars.domain.services.impl;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.entity.Planet;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.infrastructure.repositories.PlanetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j
@Service
@AllArgsConstructor
public class PlanetsServiceImpl implements PlanetsService {
    private final PlanetRepository repository;

    @Override
    public void save(final Request request) {
    }

    @Override
    public void delete(final String name) {
    }

    @Override
    public Planet findById(final String id) {
        return repository.findOne(id);
    }

    @Override
    public List<Planet> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Planet> findByName(final String name) {
        return repository.findByName(name);
    }
}
