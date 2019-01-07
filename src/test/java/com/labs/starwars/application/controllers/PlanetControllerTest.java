package com.labs.starwars.application.controllers;

import com.google.gson.Gson;
import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.services.PlanetsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.labs.starwars.TestFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
@ActiveProfiles(value = "it")
@TestPropertySource(properties = {"logging.path /tmp/labs"})
public class PlanetControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanetsService service;

    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void save_success() throws Exception {
        doReturn(planet()).when(service).save(any(Request.class));

        final Request req = req();

        final MockHttpServletRequestBuilder request = post("/v1/planets")
                .content(gson.toJson(req))
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.planet.name", is(req.getName())))
                .andExpect(jsonPath("$.planet.climate", is(req.getClimate())))
                .andExpect(jsonPath("$.planet.terrain", is(req.getTerrain())))
        ;

        verify(service).save(eq(req));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void save_bad_request() throws Exception {
        final MockHttpServletRequestBuilder request = post("/v1/planets")
                .content(gson.toJson(req(false)))
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(equalToIgnoringCase("Invalid parameters request.")))
                .andExpect(jsonPath("$.errors.name", is("may not be empty")))
                .andExpect(jsonPath("$.errors.climate", is("may not be empty")))
                .andExpect(jsonPath("$.errors.terrain", is("may not be empty")))
        ;

        verifyZeroInteractions(service);
    }

    @Test
    public void save_internal_error() throws Exception {
        doThrow(exception()).when(service).save(any(Request.class));

        final MockHttpServletRequestBuilder request = post("/v1/planets/")
                .content(gson.toJson(req()))
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(equalToIgnoringCase("errorMessage")))
        ;

        verify(service).save(any(Request.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void delete_success() throws Exception {
        final MockHttpServletRequestBuilder request = delete("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).delete(eq("id"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void delete_internal_error() throws Exception {
        doThrow(exception()).when(service).delete(anyString());

        final MockHttpServletRequestBuilder request = delete("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
        ;

        verify(service).delete(anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_findById_success() throws Exception {
        doReturn(planet(false)).when(service).findById(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planet.id", is("id")))
                .andExpect(jsonPath("$.planet.name", is("name")))
                .andExpect(jsonPath("$.planet.climate", is("climate")))
                .andExpect(jsonPath("$.planet.terrain", is("terrain")))
                .andExpect(jsonPath("$.planet.films", hasSize(1)))
                .andExpect(jsonPath("$.planet.films[0]", is("film")))
        ;


        verify(service).findById(eq("id"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_findById_error() throws Exception {
        doThrow(exception()).when(service).findById(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("errorMessage")))
        ;

        verify(service).findById(eq("id"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_findByName_success() throws Exception {
        doReturn(planets()).when(service).findByName(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets")
                .param("name", "name")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planets", hasSize(1)))
                .andExpect(jsonPath("$.planets[0].id", is("id")))
                .andExpect(jsonPath("$.planets[0].name", is("name")))
                .andExpect(jsonPath("$.planets[0].climate", is("climate")))
                .andExpect(jsonPath("$.planets[0].terrain", is("terrain")))
        ;

        verify(service).findByName(eq("name"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void search_findByName_error() throws Exception {
        doThrow(exception()).when(service).findByName(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets")
                .param("name", "name")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("errorMessage")))
        ;

        verify(service).findByName(eq("name"));
        verifyNoMoreInteractions(service);
    }
}