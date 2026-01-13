package com.hackathon.ui;

import com.hackathon.pages.DashboardPage;
import com.hackathon.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminDashboardTest extends BaseTest {

    @Test(groups = {"ui"})
    public void adminShouldSeeAllTabs() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isDashboardTabVisible());
        Assert.assertTrue(dashboard.isWorkflowsTabVisible());
        Assert.assertTrue(dashboard.isUsersTabVisible());
        Assert.assertTrue(dashboard.isReportsTabVisible());
        Assert.assertTrue(dashboard.isSettingsTabVisible());
    }
}
