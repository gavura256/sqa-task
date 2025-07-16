package com.epam.gavura.client;

import io.restassured.response.Response;

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

    public String getHeaderValue(String headerName) {
        return response.getHeaders().getValue(headerName);
    }
}
