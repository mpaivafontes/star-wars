package com.labs.starwars.domain.models.internal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.labs.starwars.domain.models.internal.entity.Planet;
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
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response implements Serializable {
    String message;
    Map<String, Object> errors;

    Planet planet;
    List<Planet> planets;
}
