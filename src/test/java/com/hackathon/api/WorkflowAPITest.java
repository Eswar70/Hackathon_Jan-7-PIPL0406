package com.hackathon.api;

import com.hackathon.utils.JsonUtil;
import com.hackathon.utils.TokenManager;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class WorkflowAPITest {

    @BeforeClass
    public void setup() {
        BaseApi.init();
    }

    @Test(groups = {"api"})
    public void adminCanCreateWorkflow() {
        String token = TokenManager.getToken("admin");

        JSONObject payload = new JSONObject()
                .put("title", "API Workflow - Admin")
                .put("description", "Created via API by Admin")
                .put("priority", "High")
                .put("category", "Finance");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/workflows");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("id"));
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
    public void createWorkflowsFromJson_shouldReturn200() {
        String token = TokenManager.getToken("admin");
        JSONArray workflows = JsonUtil.readArray("workflows.json");

        for (int i = 0; i < workflows.length(); i++) {
            JSONObject payload = workflows.getJSONObject(i);

            Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
            .when()
                .post("/workflows");
            Assert.assertEquals(response.getStatusCode(), 200);
        }
    }

    @Test(groups = {"api"})
    public void viewerCanOnlyViewWorkflows() {
        String token = TokenManager.getToken("viewer");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .get("/workflows");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("title"));
    }

    @Test(groups = {"api"})
    public void reviewerCannotApproveWithoutPermission() {
        String token = TokenManager.getToken("reviewer");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/12345/approve");

        Assert.assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 401,
                "Expected 403 Forbidden or 401 Unauthorized");
    }

    @Test(groups = {"api"})
    public void createWorkflowWithMissingFields_shouldReturn400() {
        String token = TokenManager.getToken("manager");
        JSONObject payload = new JSONObject();

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/workflows");

        Assert.assertEquals(response.getStatusCode(), 400);
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
    public void viewerCannotCreateWorkflow_shouldReturn403() {
        String token = TokenManager.getToken("viewer");

        JSONObject payload = new JSONObject()
                .put("title", "Viewer Attempt")
                .put("description", "Should be forbidden")
                .put("priority", "Low")
                .put("category", "Test");

        BaseApi.req()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/workflows")
        .then()
            .statusCode(403);
    }

    @Test(groups = {"security"})
    public void createWorkflow_withSQLInjection_shouldReturn400OrSanitize() {
        String token = TokenManager.getToken("admin");

        JSONObject payload = new JSONObject()
                .put("title", "1'; DROP TABLE users; --")
                .put("description", "Injection test")
                .put("priority", "High")
                .put("category", "QA");

        BaseApi.req()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/workflows")
        .then()
            .statusCode(anyOf(is(400), is(422), is(200)));
    }

    @Test(groups = {"security"})
    public void createWorkflow_withXSSPayload_shouldBeSanitizedOrRejected() {
        String token = TokenManager.getToken("admin");

        JSONObject payload = new JSONObject()
                .put("title", "<script>alert('XSS')</script>")
                .put("description", "XSS test")
                .put("priority", "Medium")
                .put("category", "Security");

        BaseApi.req()
            .header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
            .body(payload.toString())
        .when()
            .post("/workflows")
        .then()
            .statusCode(anyOf(is(400), is(201)))
            .body("title", not(containsString("<script>")));
    }
}
