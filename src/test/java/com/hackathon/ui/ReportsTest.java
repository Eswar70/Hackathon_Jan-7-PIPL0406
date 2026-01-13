package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.ReportsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReportsTest extends BaseTest {

    @Test(groups = {"ui"})
    public void adminCanExportUsersAndWorkflows() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.goToReportsTab();

        Assert.assertTrue(reportsPage.isExportUsersVisible(), "Admin should see Export Users");
        Assert.assertTrue(reportsPage.isExportWorkflowsVisible(), "Admin should see Export Workflows");

        reportsPage.exportUsers();
        reportsPage.exportWorkflows();
    }

    @Test(groups = {"ui"})
    public void managerCanExportUsersAndWorkflows() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("manager@example.com", "manager123");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.goToReportsTab();

        Assert.assertTrue(reportsPage.isExportUsersVisible(), "Manager should see Export Users");
        Assert.assertTrue(reportsPage.isExportWorkflowsVisible(), "Manager should see Export Workflows");

        reportsPage.exportUsers();
        reportsPage.exportWorkflows();
    }

    @Test(groups = {"ui"})
    public void reviewerCanExportOnlyWorkflows() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("reviewer@example.com", "reviewer123");

        ReportsPage reportsPage = new ReportsPage(driver);
        reportsPage.goToReportsTab();

        Assert.assertTrue(reportsPage.isExportUsersVisible(), "Reviewer should see Export Users");
        Assert.assertTrue(reportsPage.isExportWorkflowsVisible(), "Reviewer should see Export Workflows");
        
        reportsPage.exportUsers();
        reportsPage.exportWorkflows();
    }

    @Test(groups = {"ui"})
    public void viewerCannotExportAnything() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("viewer@example.com", "viewer123");
    }
}
