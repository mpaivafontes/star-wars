package com.labs.starwars.domain.models.external;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@Data
public class PlanetResponse implements Serializable {
    List<String> films;
}
