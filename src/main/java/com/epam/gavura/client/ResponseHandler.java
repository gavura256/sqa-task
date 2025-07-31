package com.epam.gavura.client;

import io.restassured.response.Response;

import java.util.Arrays;
import java.util.stream.Stream;

public class ResponseHandler {

    private final Response response;

    public ResponseHandler(Response response) {
        this.response = response;
    }

    public ResponseHandler checkStatusCode(int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            throw new IllegalArgumentException(String.format(
                    "Expected status code %d but got %d.",
                    expectedStatusCode, response.getStatusCode()));
        }

        return this;
    }

    public <T> T getBodyAs(Class<T> clazz) {
        return response.getBody().as(clazz);
    }

    public <T> Stream<T> getBodyAsStream(Class<T[]> clazz) {
        T[] bodyArray = getBodyAs(clazz);

        return Arrays.stream(bodyArray);
    }

}
