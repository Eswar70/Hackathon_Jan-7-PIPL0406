package com.hackathon.api;

//import com.hackathon.config.ConfigReader;
import com.hackathon.utils.TokenManager;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;

public class AuthAPITest {

    @BeforeClass
    public void setup() {
        BaseApi.init(); // ensures RestAssured.baseURI is set once
    }

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][]{
                {"admin@example.com", "admin123"},
                {"manager@example.com", "manager123"},
                {"reviewer@example.com", "reviewer123"},
                {"viewer@example.com", "viewer123"}
        };
    }

    @Test(dataProvider = "validUsers", groups = {"api"})
    public void loginWithValidCredentials_shouldReturnToken(String email, String password) {
        JSONObject payload = new JSONObject().put("email", email).put("password", password);

        BaseApi.req()
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/auth/login") // use relative path with baseURI
        .then()
            .statusCode(200)
            .assertThat()
            .body(matchesJsonSchemaInClasspath("schemas/login_response_schema.json"));
    }

    @Test(groups = {"api"})
    public void createWorkflow_withInvalidToken_shouldReturn401() {
        JSONObject payload = new JSONObject()
                .put("title", "Unauthorized Workflow")
                .put("description", "Simulate invalid token")
                .put("priority", "High")
                .put("category", "QA");

        BaseApi.req()
            .header("Authorization", "Bearer invalid_token")
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/workflows")
        .then()
            .statusCode(401)
            .body("error", containsString("Unauthorized"));
    }

    @Test(groups = {"api"})
    public void loginWithInvalidPassword_shouldReturn401() {
        JSONObject payload = new JSONObject().put("email", "admin@example.com").put("password", "wrongpass");

        Response response = BaseApi.req()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/auth/login");

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Unauthorized");
    }

    @Test(groups = {"api"})
    public void loginWithMissingFields_shouldReturn400() {
        JSONObject payload = new JSONObject(); // empty payload

        Response response = BaseApi.req()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/auth/login");

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request");
    }

    @Test(groups = {"api"})
    public void getWorkflows_withoutAuthHeader_shouldReturn401() {
        BaseApi.req()
        .when()
            .get("/workflows")
        .then()
            .statusCode(401)
            .body("error", containsString("Unauthorized"));
    }

    @Test(groups = {"api"})
    public void callInvalidEndpoint_shouldReturn404() {
        String token = TokenManager.getToken("admin");
        BaseApi.req()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/invalid-endpoint")
        .then()
            .statusCode(404);
    }

    @Test(groups = {"api"})
    public void createWorkflow_withMalformedPayload_shouldReturn400() {
        String token = TokenManager.getToken("admin");
        String malformedJson = "{ \"title\": \"Missing closing brace\" ";

        BaseApi.req()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(malformedJson)
        .when()
            .post("/workflows")
        .then()
            .statusCode(400);
    }

    @Test(groups = {"security"})
    public void loginWithInvalidCredentials_shouldReturn401() {
        JSONObject payload = new JSONObject().put("email", "fake@example.com").put("password", "wrongpass");

        BaseApi.req()
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/auth/login")
        .then()
            .statusCode(401)
            .body("error", containsString("Invalid"));
    }

    @Test(groups = {"security"})
    public void loginWithMissingCredentials_shouldReturn400() {
        JSONObject payload = new JSONObject();

        BaseApi.req()
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/auth/login")
        .then()
            .statusCode(400);
    }
}
