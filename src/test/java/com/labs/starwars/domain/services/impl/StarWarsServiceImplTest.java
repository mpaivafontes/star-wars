package com.labs.starwars.domain.services.impl;

import com.labs.starwars.domain.models.external.StarWarsResponse;
import com.labs.starwars.infrastructure.apis.StarWarsAPI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.labs.starwars.TestFactory.exception;
import static com.labs.starwars.TestFactory.starWars;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public class StarWarsServiceImplTest {

    private StarWarsServiceImpl service;

    @Mock
    private StarWarsAPI force;

    @Before
    public void setUp() {
        initMocks(this);

        service = new StarWarsServiceImpl(force);
    }

    @Test
    public void success_with_result() {
        doReturn(starWars()).when(force).search(anyString());

        final StarWarsResponse result = service.execute("name");

        verify(force).search(eq("name"));

        assertThat(result).isNotNull();
        assertThat(result.getResults()).isNotEmpty();
    }

    @Test
    public void success_no_result() {
        doReturn(starWars(true)).when(force).search(anyString());

        final StarWarsResponse result = service.execute("name");

        verify(force).search(eq("name"));

        assertThat(result).isNotNull();
        assertThat(result.getResults()).isEmpty();
    }

    @Test
    public void error() {
        doThrow(exception()).when(force).search(anyString());

        assertThatCode(() -> service.execute("name")).isInstanceOf(RuntimeException.class);

        verify(force).search(eq("name"));
    }
}