package com.hackathon.api;

import com.hackathon.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {
    private static volatile boolean initialized = false;

    public static synchronized void init() {
        if (initialized) return;
        String baseApi = ConfigReader.getOrThrow("apiBaseUrl"); // never null
        RestAssured.baseURI = baseApi;
        initialized = true;
    }

    public static RequestSpecification req() {
        init(); // ensures baseURI is set
        return given();
    }
}
