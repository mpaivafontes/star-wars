package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.services.PlanetsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.labs.starwars.TestFactory.exception;
import static com.labs.starwars.TestFactory.planets;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
@TestPropertySource(properties = {"logging.path /tmp/b2w"})
public class SearchControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanetsService service;

    @Test
    public void findById_success() throws Exception {
        doReturn(planets()).when(service).findById(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planets").isArray())
        ;

        verify(service).findById(eq("id"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findById_error() throws Exception {
        doThrow(exception()).when(service).findById(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets/{id}", "id")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(equalToIgnoringCase("errorMessage")))
        ;

        verify(service).findById(eq("id"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findByName_success() throws Exception {
        doReturn(planets()).when(service).findByName(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets")
                .param("name", "name")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planets").isArray())
        ;

        verify(service).findByName(eq("name"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findByName_error() throws Exception {
        doThrow(exception()).when(service).findByName(anyString());

        final MockHttpServletRequestBuilder request = get("/v1/planets")
                .param("name", "name")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(equalToIgnoringCase("errorMessage")))
        ;

        verify(service).findByName(eq("name"));
        verifyNoMoreInteractions(service);
    }
}