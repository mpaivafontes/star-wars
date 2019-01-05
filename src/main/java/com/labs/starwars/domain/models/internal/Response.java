package com.labs.starwars.domain.models.internal;

import com.labs.starwars.domain.routers.Planet;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@Data
@Builder
public class Response implements Serializable {
    String message;
    Map<String, Object> errors;

    List<Planet> planets;
}
