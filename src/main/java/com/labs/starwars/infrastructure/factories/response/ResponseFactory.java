package com.labs.starwars.infrastructure.factories.response;

import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.models.internal.entity.Planet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class ResponseFactory {
    //////////////////////////////

    public static ResponseEntity<Response> success(final Planet planet) {
        return ok(body(planet));
    }

    public static ResponseEntity<Response> success(final List<Planet> planets) {
        return ok(body(planets));
    }

    //////////////////////////////

    public static ResponseEntity<Response> created(final Planet planet) {
        return status(CREATED).body(body(planet));
    }

    public static ResponseEntity<Response> noContent() {
        return status(NO_CONTENT).body(null);
    }

    //////////////////////////////

    public static ResponseEntity<Response> badRequest(final Errors errors) {
        return badRequest("Invalid parameters request.", errors.getFieldErrors().stream()
                .collect(toMap(FieldError::getField, FieldError::getDefaultMessage)));
    }

    private static ResponseEntity<Response> badRequest(final String customMessage, final Map<String, Object> errors) {
        return response(BAD_REQUEST, customMessage, errors);
    }

    //////////////////////////////

    public static ResponseEntity<Response> internalError(final Exception ex) {
        return response(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    //////////////////////////////

    private static ResponseEntity<Response> response(final HttpStatus status, final String message) {
        return response(status, message, null);
    }

    private static ResponseEntity<Response> response(final HttpStatus status, final String message, final Map<String, Object> errors) {
        return status(status).body(body(message, errors));
    }

    //////////////////////////////

    private static Response body(final Planet planet) {
        return body(planet, null);
    }

    private static Response body(final List<Planet> planets) {
        return body(null, planets);
    }

    private static Response body(final Planet planet, final List<Planet> planets) {
        return body(planet, planets, null, null);
    }

    private static Response body(final String message, final Map<String, Object> errors) {
        return body(null, null, message, errors);
    }

    private static Response body(final Planet planet, final List<Planet> planets,
                                 final String message, final Map<String, Object> errors) {
        return Response.builder()
                .planet(planet).planets(planets)
                .message(message).errors(errors)
                .build();
    }
}
