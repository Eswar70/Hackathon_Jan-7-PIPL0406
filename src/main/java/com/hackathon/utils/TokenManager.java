package com.hackathon.utils;

//import static io.restassured.RestAssured.given;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

import com.hackathon.api.BaseApi;
import io.restassured.response.Response;

public class TokenManager {

    public static String getToken(String role) {
        try {
            // Load users.json from classpath
            InputStream is = TokenManager.class.getClassLoader().getResourceAsStream("users.json");
            if (is == null) {
                throw new RuntimeException("users.json not found in classpath");
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject users = new JSONObject(json);
            JSONObject user = users.getJSONObject(role.toLowerCase());

            String email = user.getString("email");
            String password = user.getString("password");

            // Ensure baseURI initialized
            BaseApi.init();

            Response response = BaseApi.req()
                    .header("Content-Type", "application/json")
                    .body(new JSONObject().put("email", email).put("password", password).toString())
                    .post("/auth/login");

            System.out.println("Token response: " + response.getBody().asString());

            String token = response.jsonPath().getString("token");
            if (response.statusCode() != 200 || token == null || token.isBlank()) {
                throw new RuntimeException("Token not received. Status: " + response.statusCode() + ", Body: " + response.getBody().asString());
            }

            return token;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get token for role: " + role, e);
        }
    }
}
