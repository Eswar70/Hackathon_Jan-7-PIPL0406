package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.WorkflowPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManagerWorkflowTest extends BaseTest {

    @Test(groups = {"ui"})
    public void managerCanCreateAndViewWorkflow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("manager@example.com", "manager123");

        WorkflowPage workflowPage = new WorkflowPage(driver);
        workflowPage.goToWorkflows();

        workflowPage.createWorkflow("Manager Test Workflow", "Created by Manager", "Medium", "Operations");

        Assert.assertTrue(workflowPage.isWorkflowContainerVisible(), "Workflow table should be visible");
    }
}
