package com.labs.starwars.domain.models.internal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/4/19
 **/
@Data
public class Planet {
    @Id
    String id;

    @NotEmpty
    String name;
    @NotEmpty
    String climate;
    @NotEmpty
    String terrain;

    List<String> films;
}
