package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.SettingsPage;
import org.testng.annotations.Test;

public class SettingsTest extends BaseTest {

    @Test(groups = {"ui"})
    public void adminCanToggleThemeAndNotifications() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        SettingsPage settingsPage = new SettingsPage(driver);
        settingsPage.goToSettings();

        settingsPage.toggleTheme();         // Toggle dark/light mode
        settingsPage.toggleNotifications(); // Toggle email + system alerts
    }

    @Test(groups = {"ui"})
    public void adminCanUpdatePassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        SettingsPage settingsPage = new SettingsPage(driver);
        settingsPage.goToSettings();

        // Demo: using same password to avoid breaking login
        settingsPage.updatePassword("admin123", "admin123", "admin123");
    }
}
