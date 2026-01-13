package com.hackathon.utils;

import com.hackathon.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.FileWriter;
//import java.util.Map;

public class TokenCsvGenerator {

    public static void main(String[] args) {
        try {
            String json = java.nio.file.Files.readString(java.nio.file.Paths.get("src/test/resources/users.json"));
            JSONObject users = new JSONObject(json);

            FileWriter writer = new FileWriter("Performance/jmeter/tokens.csv");
            writer.write("token\n");

            for (String role : users.keySet()) {
                JSONObject user = users.getJSONObject(role);
                String email = user.getString("email");
                String password = user.getString("password");

                JSONObject payload = new JSONObject();
                payload.put("email", email);
                payload.put("password", password);

                Response response = RestAssured.given()
                        .header("Content-Type", "application/json")
                        .body(payload.toString())
                        .post(ConfigReader.get("loginApi"));

                String token = response.jsonPath().getString("token");
                writer.write(token + "\n");
            }

            writer.close();
            System.out.println("Tokens written to tokens.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
