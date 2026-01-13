package com.hackathon.ui;

import com.hackathon.pages.DashboardPage;
import com.hackathon.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewerDashboardTest extends BaseTest {

    @Test(groups = {"ui"})
    public void viewerShouldSeeOnlyWorkflowTab() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("viewer@example.com", "viewer123");

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isDashboardTabVisible());
        Assert.assertTrue(dashboard.isWorkflowsTabVisible());
        Assert.assertFalse(dashboard.isUsersTabVisible());
        Assert.assertFalse(dashboard.isReportsTabVisible());
        Assert.assertFalse(dashboard.isSettingsTabVisible());
    }
}
