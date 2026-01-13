package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"ui"})
    public void loginAsAdmin_shouldRedirectToDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("dashboard"), "Expected to be on dashboard, but was: " + currentUrl);
    }
}
