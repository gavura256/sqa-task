package com.epam.gavura.client;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

class RestClient {

    private final RequestSpecification specification;

    protected RestClient() {
        specification = RestAssured.given()
            .filter(new ResponseLoggingFilter(LogDetail.ALL, true, System.out))
            .filter(new RequestLoggingFilter(LogDetail.ALL, true, System.out))
            .contentType("application/json; charset=UTF-8");
    }

    protected ResponseHandler defaultGet(String url) {
        return new ResponseHandler(specification.get(url));
    }

    protected ResponseHandler defaultPut(String url, Object body) {
        return new ResponseHandler(specification.body(body).put(url));
    }

    protected ResponseHandler defaultPost(String url, Object body) {
        return new ResponseHandler(specification.body(body).post(url));
    }

    protected ResponseHandler defaultDelete(String url) {
        return new ResponseHandler(specification.delete(url));
    }

}
