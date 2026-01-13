package com.hackathon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By usersTab = By.xpath("//span[text()='Users']");
    private By createUserButton = By.xpath("//button[contains(text(),'Create')]");
    private By firstName = By.xpath("(//div[@role='dialog']//input[@type='text'])[1]");
    private By lastName = By.xpath("(//div[@role='dialog']//input[@type='text'])[2]");
    private By emailField = By.xpath("(//div[@role='dialog']//input[@type='text'])[3]");
    private By roleDropdown = By.xpath("//div[@role='dialog']//div[@role='combobox']");
    private By dropdownOptions = By.xpath("//ul//li[contains(@role,'option')]");
    private By passwordField = By.xpath("//input[@type='password']");
    private By department = By.xpath("(//div[@role='dialog']//input[@type='text'])[4]");
    private By submitButton = By.xpath("//div[@role='dialog']//button[contains(text(),'User')]");
    private By EditSubmitBtn = By.xpath("//div[@role='dialog']//button[contains(text(),'Save')]");
    private By userListContainer = By.tagName("table");

    public void goToUsersTab() {
        driver.findElement(usersTab).click();
    }

    public void createUser(String first, String last, String email, String role, String password, String dept) {
        driver.findElement(createUserButton).click();
        driver.findElement(firstName).sendKeys(first);
        driver.findElement(lastName).sendKeys(last);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(department).sendKeys(dept);
        selectFromCustomDropdown(roleDropdown, role);
        driver.findElement(submitButton).click();
    }

    private void selectFromCustomDropdown(By dropdown, String visibleText) {
        driver.findElement(dropdown).click();
        List<WebElement> options = driver.findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(visibleText)) {
                option.click();
                break;
            }
        }
    }

    public boolean isUserListVisible() {
        return !driver.findElements(userListContainer).isEmpty();
    }

    public boolean isUserPresent(String email) {
        return driver.getPageSource().contains(email);
    }

    public void deleteUser(String email) {
        By deleteButton = By.xpath("//tr[td//span[contains(text(),'" + email + "')]]//td[5]//button[2]");
        driver.findElement(deleteButton).click();
        driver.switchTo().alert().accept();
    }

    // Updated editUser method: changes both first name and department
    public void editUser(String email, String newFirstName, String newDepartment) {
        By editButton = By.xpath("//tr[td//span[contains(text(),'" + email + "')]]//td[5]//button[1]");
        driver.findElement(editButton).click();

        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(newFirstName);

        driver.findElement(department).clear();
        driver.findElement(department).sendKeys(newDepartment);

        //driver.findElement(EditSubmitBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(EditSubmitBtn));
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(EditSubmitBtn));
        submit.click();

    }
}
