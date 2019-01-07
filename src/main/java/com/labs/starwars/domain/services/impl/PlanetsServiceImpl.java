package com.labs.starwars.domain.services.impl;

import com.labs.starwars.domain.models.external.StarWarsResponse;
import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.models.internal.entity.Planet;
import com.labs.starwars.domain.services.PlanetsService;
import com.labs.starwars.domain.services.StarWarsService;
import com.labs.starwars.infrastructure.repositories.PlanetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.labs.starwars.infrastructure.factories.entity.PlanetFactory.create;
import static java.util.stream.Collectors.toList;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Slf4j
@Service
@AllArgsConstructor
public class PlanetsServiceImpl implements PlanetsService {
    private final StarWarsService service;

    private final PlanetRepository repository;

    @Override
    public Planet save(final Request request) {
        return repository.save(create(request));
    }

    @Override
    public void delete(final String id) {
        repository.delete(id);
    }

    @Override
    public Planet findById(final String id) {
        final Planet planet = repository.findOne(id);
        addFilms(planet);
        return planet;
    }

    @Override
    public List<Planet> findByName(final String name) {
        final List<Planet> planets = repository.findByName(name);
        planets.stream().parallel().forEach(this::addFilms);
        return planets;
    }

    private void addFilms(final Planet planet) {
        final StarWarsResponse response = service.execute(planet.getName());

        if (hasResults(response)) {
            final List<String> films = response.getResults().stream()
                    .flatMap(film -> film.getFilms().stream())
                    .filter(film -> !StringUtils.isEmpty(film))
                    .collect(toList());

            planet.setFilms(films);
        }
    }

    private boolean hasResults(final StarWarsResponse response) {
        return response != null &&
                response.getResults() != null &&
                !CollectionUtils.isEmpty(response.getResults());
    }
}
