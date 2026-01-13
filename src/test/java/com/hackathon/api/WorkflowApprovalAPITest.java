package com.hackathon.api;

import com.hackathon.utils.TokenManager;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WorkflowApprovalAPITest {

    @BeforeClass
    public void setup() {
        BaseApi.init();
    }

    private String createWorkflowAndGetId(String role) {
        String token = TokenManager.getToken(role);

        JSONObject payload = new JSONObject()
                .put("title", "Approval Test - " + role)
                .put("description", "Workflow for approval test")
                .put("priority", "Medium")
                .put("category", "Operations");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("/workflows");

        Assert.assertEquals(response.getStatusCode(), 200);
        return response.jsonPath().getString("id");
    }

    @Test(groups = {"api"})
    public void adminCanApproveWorkflow() {
        String workflowId = createWorkflowAndGetId("admin");
        String token = TokenManager.getToken("admin");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/" + workflowId + "/approve");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(groups = {"api"})
    public void managerCanRejectWorkflow() {
        String workflowId = createWorkflowAndGetId("manager");
        String token = TokenManager.getToken("manager");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/" + workflowId + "/reject");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(groups = {"api"})
    public void reviewerCannotApproveWorkflow() {
        String workflowId = createWorkflowAndGetId("reviewer");
        String token = TokenManager.getToken("reviewer");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/" + workflowId + "/approve");

        Assert.assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 401);
    }

    @Test(groups = {"api"})
    public void viewerCannotRejectWorkflow() {
        String workflowId = createWorkflowAndGetId("viewer");
        String token = TokenManager.getToken("viewer");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/" + workflowId + "/reject");

        Assert.assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 401);
    }

    @Test(groups = {"api"})
    public void invalidWorkflowIdShouldReturn404() {
        String token = TokenManager.getToken("admin");

        Response response = BaseApi.req()
                .header("Authorization", "Bearer " + token)
                .post("/workflows/invalid-id-123/approve");

        Assert.assertEquals(response.getStatusCode(), 404);
    }
}
