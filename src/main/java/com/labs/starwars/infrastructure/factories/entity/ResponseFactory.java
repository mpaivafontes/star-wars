package com.labs.starwars.infrastructure.factories.entity;

import com.labs.starwars.domain.models.internal.Response;
import com.labs.starwars.domain.routers.Planet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.*;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public final class ResponseFactory {

    public static ResponseEntity<Response> ok(final List<Planet> planets) {
        return response(OK);
    }

    public static ResponseEntity<Response> created() {
        return response(CREATED);
    }

    public static ResponseEntity<Response> badRequest(final Errors errors) {
        return badRequest("Invalid parameters request.", errors.getFieldErrors().stream()
                .collect(toMap(FieldError::getField, FieldError::getDefaultMessage)));
    }

    private static ResponseEntity<Response> badRequest(final String customMessage, final Map<String, Object> errors) {
        return response(BAD_REQUEST, customMessage, errors);
    }

    public static ResponseEntity<Response> internalError(final Exception ex) {
        return response(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private static ResponseEntity<Response> response(final HttpStatus status) {
        return response(status, null);
    }

    private static ResponseEntity<Response> response(final HttpStatus status, final String errorMessage) {
        return response(status, errorMessage, null);
    }

    private static ResponseEntity<Response> response(final HttpStatus status, final String message, final Map<String, Object> errors) {
        return ResponseEntity.status(status).body(responseBody(message, errors));
    }

    private static Response responseBody(final String message, final Map<String, Object> errors) {
        return Response.builder()
                .message(message).errors(errors)
                .build();
    }
}
