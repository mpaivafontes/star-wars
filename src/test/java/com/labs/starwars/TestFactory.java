package com.labs.starwars;

import com.labs.starwars.domain.models.internal.Request;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class TestFactory {
    public static Request req() {
        return req(true);
    }

    public static Request req(boolean isValid) {
        final Request req = new Request();
        if (isValid) {
            req.setName("name");
            req.setClimate("climate");
            req.setTerrain("terrain");
        }
        return req;
    }

    ///////////////////////////////////

    public static Throwable exception() {
        return new RuntimeException("errorMessage");
    }
}
