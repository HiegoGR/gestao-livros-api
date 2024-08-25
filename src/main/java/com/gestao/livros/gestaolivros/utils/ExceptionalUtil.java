package com.gestao.livros.gestaolivros.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionalUtil {

    public static void badRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public static void acceptedRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.ACCEPTED, msg);
    }

    public static void notFoundRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
    }

    public static void forbiddenException(String msg) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
    }

    public static String ok(String msg) {
        throw new ResponseStatusException(HttpStatus.OK, msg);
    }

    public static void internalServerErroException(String msg) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }
}
