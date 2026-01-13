package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WorkflowPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public WorkflowPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigation
    private By workflowsTab = By.xpath("//span[text()='Workflows']");

    // Create workflow elements
    private By createButton = By.xpath("//button[contains(text(),'New')]");
    private By titleField = By.xpath("//input[@type='text']");
    private By descriptionField = By.xpath("//textarea[@placeholder='Describe the workflow details...']");

    // Custom dropdowns
    private By priorityDropdown = By.xpath("(//form//div[@role='combobox'])[1]");
    private By categoryDropdown = By.xpath("(//form//div[@role='combobox'])[2]");
    private By dropdownOptions = By.xpath("//ul//li[contains(@role,'option')]");

    private By submitButton = By.xpath("//button[contains(text(),'Create')]");

    // Workflow list container (not a table)
    private By workflowContainer = By.xpath("//div[contains(@class,'MuiDataGrid-virtualScroller')]");

    public void goToWorkflows() {
        wait.until(ExpectedConditions.elementToBeClickable(workflowsTab)).click();
    }

    public void createWorkflow(String title, String description, String priority, String category) {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleField)).sendKeys(title);
        driver.findElement(descriptionField).sendKeys(description);

        selectFromCustomDropdown(priorityDropdown, priority);
        selectFromCustomDropdown(categoryDropdown, category);

        // Use JS click for reliability
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
    }

    private void selectFromCustomDropdown(By dropdown, String visibleText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();

        // Scoped search for options inside the dropdown
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(visibleText)) {
                option.click();
                break;
            }
        }
    }

    public boolean isWorkflowContainerVisible() {
        return !driver.findElements(workflowContainer).isEmpty();
    }
}
