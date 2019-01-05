package com.labs.starwars.domain.models.internal;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
@Data
public class Request implements Serializable {
    @NotEmpty
    String name;

    @NotEmpty
    String climate;
    @NotEmpty
    String terrain;
}
