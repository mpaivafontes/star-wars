package com.labs.starwars.application.controllers;

import com.labs.starwars.domain.services.PlanetsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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


}