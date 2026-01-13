package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.WorkflowPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReviewerWorkflowTest extends BaseTest {

    @Test(groups = {"ui"})
    public void reviewerCanCreateAndViewWorkflow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("reviewer@example.com", "reviewer123");

        WorkflowPage workflowPage = new WorkflowPage(driver);
        workflowPage.goToWorkflows();

        workflowPage.createWorkflow("Reviewer Test Workflow", "Created by Reviewer", "Low", "Compliance");

        Assert.assertTrue(workflowPage.isWorkflowContainerVisible(), "Workflow table should be visible");
    }
}
