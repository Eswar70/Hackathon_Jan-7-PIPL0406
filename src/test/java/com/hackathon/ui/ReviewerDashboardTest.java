package com.hackathon.ui;

import com.hackathon.pages.DashboardPage;
import com.hackathon.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReviewerDashboardTest extends BaseTest {

    @Test(groups = {"ui"})
    public void reviewerShouldSeeLimitedTabs() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("reviewer@example.com", "reviewer123");

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isDashboardTabVisible());
        Assert.assertTrue(dashboard.isWorkflowsTabVisible());
        Assert.assertFalse(dashboard.isUsersTabVisible());
        Assert.assertTrue(dashboard.isReportsTabVisible());
        Assert.assertFalse(dashboard.isSettingsTabVisible());
    }
}
