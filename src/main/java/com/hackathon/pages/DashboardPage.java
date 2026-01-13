package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // Common tab locators
    private By dashboardTab = By.xpath("//span[text()='Dashboard']");
    private By workflowsTab = By.xpath("//span[text()='Workflows']");
    private By usersTab = By.xpath("//span[text()='Users']");
    private By reportsTab = By.xpath("//span[text()='Reports']");
    private By settingsTab = By.xpath("//span[text()='Settings']");

    public boolean isDashboardTabVisible() {
        return isElementPresent(dashboardTab);
    }

    public boolean isWorkflowsTabVisible() {
        return isElementPresent(workflowsTab);
    }

    public boolean isUsersTabVisible() {
        return isElementPresent(usersTab);
    }

    public boolean isReportsTabVisible() {
        return isElementPresent(reportsTab);
    }

    public boolean isSettingsTabVisible() {
        return isElementPresent(settingsTab);
    }

    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }
}
