package com.hackathon.api;

import com.hackathon.utils.TokenManager;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ErrorHandlingAPITest {

    @BeforeClass
    public void setup() {
        BaseApi.init();
    }

    @Test(groups = {"api"})
    public void requestWithoutToken_shouldReturn401() {
        Response response = BaseApi.req()
                .header("Content-Type", "application/json")
                .get("/workflows");

        // If your backend returns 403, make it flexible:
        Assert.assertTrue(response.getStatusCode() == 401 || response.getStatusCode() == 403,
                "Expected 401 Unauthorized or 403 Forbidden");
    }

    @Test(groups = {"api"})
    public void requestWithInvalidToken_shouldReturn401() {
        Response response = BaseApi.req()
                .header("Authorization", "Bearer invalid.token.123")
                .get("/workflows");

        Assert.assertTrue(response.getStatusCode() == 401 || response.getStatusCode() == 403,
                "Expected 401 Unauthorized or 403 Forbidden");
    }

    @Test(groups = {"api"})
    public void createWorkflowWithMalformedJson_shouldReturn400() {
        String token = TokenManager.getToken("admin");
        String malformedJson = "{ \"title\": \"Missing quote, \"description\": \"Oops\" }";

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(malformedJson)
                .post("/workflows");

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request");
    }

    @Test(groups = {"api"})
    public void viewerTryingToCreateWorkflow_shouldReturn403() {
        String token = TokenManager.getToken("viewer");

        JSONObject payload = new JSONObject()
                .put("title", "Viewer Attempt")
                .put("description", "Should not be allowed")
                .put("priority", "Low")
                .put("category", "HR");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/workflows");

        Assert.assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 401,
                "Expected 403 Forbidden or 401 Unauthorized");
    }
}
