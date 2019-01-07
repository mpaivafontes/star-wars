package com.labs.starwars.domain.services;

import com.labs.starwars.domain.models.external.StarWarsResponse;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public interface StarWarsService {
    StarWarsResponse execute(String name);
}
