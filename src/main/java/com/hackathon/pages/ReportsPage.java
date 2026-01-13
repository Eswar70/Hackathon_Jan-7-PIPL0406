package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReportsPage {
    private WebDriver driver;

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By reportsTab = By.xpath("//span[text()='Reports']");

    private By exportUsersButton = By.xpath("//button[contains(text(),'Export Users')]");
    private By exportWorkflowsButton = By.xpath("//button[contains(text(),'Export Workflows')]");

    public void goToReportsTab() {
        driver.findElement(reportsTab).click();
    }

    public boolean isExportUsersVisible() {
        return !driver.findElements(exportUsersButton).isEmpty();
    }

    public boolean isExportWorkflowsVisible() {
        return !driver.findElements(exportWorkflowsButton).isEmpty();
    }

    public void exportUsers() {
        if (isExportUsersVisible()) {
            driver.findElement(exportUsersButton).click();
        }
    }

    public void exportWorkflows() {
        if (isExportWorkflowsVisible()) {
            driver.findElement(exportWorkflowsButton).click();
        }
    }
}
