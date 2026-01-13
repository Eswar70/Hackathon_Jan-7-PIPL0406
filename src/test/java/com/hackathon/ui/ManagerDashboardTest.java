package com.hackathon.ui;

import com.hackathon.pages.DashboardPage;
import com.hackathon.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManagerDashboardTest extends BaseTest {

    @Test(groups = {"ui"})
    public void managerShouldSeeLimitedTabs() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("manager@example.com", "manager123");

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isDashboardTabVisible());
        Assert.assertTrue(dashboard.isWorkflowsTabVisible());
        Assert.assertTrue(dashboard.isUsersTabVisible());
        Assert.assertTrue(dashboard.isReportsTabVisible());
        Assert.assertFalse(dashboard.isSettingsTabVisible());
    }
}
