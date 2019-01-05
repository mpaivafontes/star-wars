package com.labs.starwars.domain.routers;

import lombok.Data;
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

    String name;
    String climate;
    String terrain;

    List<String> films;
}
