package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettingsPage {
    private WebDriver driver;

    public SettingsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By settingsTab = By.xpath("//span[text()='Settings']");

    // Theme toggle (on main settings page)
    private By themeToggle = By.xpath("//input[@type='checkbox']");

    // Tabs inside settings
    private By passwordTab = By.xpath("//button[contains(text(),'Security')]");
    private By notificationTab = By.xpath("//button[contains(text(),'Notifications')]");

    // Password fields
    private By currentPassword = By.xpath("(//input[@type='password'])[1]");
    private By newPassword = By.xpath("(//input[@type='password'])[2]");
    private By confirmPassword = By.xpath("(//input[@type='password'])[3]");
    private By updatePasswordButton = By.xpath("//button[contains(text(),'Update Password')]");

    // Notification toggles
    private By emailNotificationToggle = By.xpath("(//input[@type='checkbox'])[1]");
    private By systemAlertToggle = By.xpath("(//input[@type='checkbox'])[2]");

    public void goToSettings() {
        driver.findElement(settingsTab).click();
    }

    public void toggleTheme() {
        driver.findElement(themeToggle).click();
    }

    public void goToPasswordTab() {
        driver.findElement(passwordTab).click();
    }

    public void updatePassword(String current, String newPass, String confirm) {
        goToPasswordTab();
        driver.findElement(currentPassword).sendKeys(current);
        driver.findElement(newPassword).sendKeys(newPass);
        driver.findElement(confirmPassword).sendKeys(confirm);
        driver.findElement(updatePasswordButton).click();
    }

    public void goToNotificationTab() {
        driver.findElement(notificationTab).click();
    }

    public void toggleNotifications() {
        goToNotificationTab();
        driver.findElement(emailNotificationToggle).click();
        driver.findElement(systemAlertToggle).click();
    }
}
