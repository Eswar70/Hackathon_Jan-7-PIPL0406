package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.WorkflowPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewerWorkflowTest extends BaseTest {

    @Test(groups = {"ui"})
    public void viewerCanOnlyViewWorkflows() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("viewer@example.com", "viewer123");

        WorkflowPage workflowPage = new WorkflowPage(driver);
        workflowPage.goToWorkflows();

        Assert.assertTrue(workflowPage.isWorkflowContainerVisible(), "Workflow table should be visible");
    }
}
