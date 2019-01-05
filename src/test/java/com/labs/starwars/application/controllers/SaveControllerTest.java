package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.models.internal.Request;
import com.labs.starwars.domain.services.PlanetsService;
import com.google.gson.Gson;
import org.junit.Before;
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
import static com.labs.starwars.TestFactory.req;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(SaveController.class)
@TestPropertySource(properties = {"logging.path /tmp/b2w"})
public class SaveControllerTest {
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
    public void success() throws Exception {
        final Request req = req();

        final MockHttpServletRequestBuilder request = post("/v1/planets")
                .content(gson.toJson(req))
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(service).save(eq(req));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void bad_request() throws Exception {
        final MockHttpServletRequestBuilder request = post("/v1/planets")
                .content(gson.toJson(req(false)))
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(equalToIgnoringCase("Invalid parameters request.")))
                .andExpect(jsonPath("$.errors.name").value(equalToIgnoringCase("may not be empty")))
                .andExpect(jsonPath("$.errors.climate").value(equalToIgnoringCase("may not be empty")))
                .andExpect(jsonPath("$.errors.terrain").value(equalToIgnoringCase("may not be empty")))
        ;

        verifyZeroInteractions(service);
    }

    @Test
    public void internal_error() throws Exception {
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
}