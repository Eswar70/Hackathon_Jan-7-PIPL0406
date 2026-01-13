package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.WorkflowPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminWorkflowTest extends BaseTest {

    @Test(groups = {"ui"})
    public void adminCanCreateAndViewWorkflow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        WorkflowPage workflowPage = new WorkflowPage(driver);
        workflowPage.goToWorkflows();

        workflowPage.createWorkflow("Admin Test Workflow", "Created by Admin", "High", "Finance");

        Assert.assertTrue(workflowPage.isWorkflowContainerVisible(), "Workflow table should be visible");
    }
}
