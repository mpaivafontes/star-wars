package com.labs.starwars.domain.services.impl;

import com.labs.starwars.domain.models.internal.entity.Planet;
import com.labs.starwars.domain.services.StarWarsService;
import com.labs.starwars.infrastructure.repositories.PlanetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static com.labs.starwars.TestFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author - marcelo.fontes
 * @since - 1/6/19
 **/
public class PlanetsServiceImplTest {
    private PlanetsServiceImpl service;

    @Mock
    private PlanetRepository repository;
    @Mock
    private StarWarsService force;

    @Captor
    private ArgumentCaptor<Planet> captor;

    @Before
    public void setUp() {
        initMocks(this);

        service = new PlanetsServiceImpl(force, repository);
    }

    @Test
    public void save() {
        service.save(req());

        verify(repository).save(captor.capture());

        assertThat(captor.getValue())
                .isNotNull()
                .extracting("name", "climate", "terrain")
                .contains("name", "climate", "terrain");
    }

    @Test
    public void delete() {
        service.delete("id");

        verify(repository).delete(eq("id"));
    }

    @Test
    public void findByName_no_films() {
        final List<Planet> planets = planets(true);

        doReturn(planets).when(repository).findByName(anyString());

        final List<Planet> result = service.findByName("name");

        verify(repository).findByName(eq("name"));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.get(0).getFilms()).isEmpty();
    }

    @Test
    public void findByName_with_films() {
        final List<Planet> planets = planets();

        doReturn(planets).when(repository).findByName(anyString());
        doReturn(starWars()).when(force).execute(anyString());

        final List<Planet> result = service.findByName("name");

        verify(repository).findByName(eq("name"));
        verify(force).execute(eq("name"));

        assertThat(result).isNotEmpty().hasSize(1);
        assertThat(result.get(0))
                .extracting("name", "climate", "terrain")
                .contains("name", "climate", "terrain");
        assertThat(result.get(0).getFilms()).isNotEmpty().hasSize(1)
                .contains("film");
    }
}