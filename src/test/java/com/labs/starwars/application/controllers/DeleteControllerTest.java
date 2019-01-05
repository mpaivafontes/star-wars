package com.labs.starwars.application.controllers;

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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(DeleteController.class)
@TestPropertySource(properties = {"logging.path /tmp/b2w"})
public class DeleteControllerTest {
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
        final MockHttpServletRequestBuilder request = delete("/v1/planets/{name}", "planet")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        verify(service).delete(eq("planet"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void internal_error() throws Exception {
        doThrow(exception()).when(service).delete(anyString());

        final MockHttpServletRequestBuilder request = delete("/v1/planets/{name}", "planet")
                .contentType(APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isInternalServerError())
        ;

        verify(service).delete(anyString());
        verifyNoMoreInteractions(service);
    }
}