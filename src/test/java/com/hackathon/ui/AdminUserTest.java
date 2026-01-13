package com.hackathon.ui;

import com.hackathon.pages.LoginPage;
import com.hackathon.pages.UserPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminUserTest extends BaseTest {

    @Test(groups = {"ui"})
    public void adminCanCreateEditAndDeleteUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("admin@example.com", "admin123");

        UserPage userPage = new UserPage(driver);
        userPage.goToUsersTab();

        String email = "testuser" + System.currentTimeMillis() + "@example.com";

        // Create
        userPage.createUser("Test", "User", email, "viewer", "test123", "QA");
        Assert.assertTrue(userPage.isUserPresent(email), "User should be present after creation");

        // Edit (first name + department) and Verify changes
        userPage.editUser(email, "Updated", "Engineering");
        Assert.assertTrue(driver.getPageSource().contains("Updated"), "User first name should be updated");
        Assert.assertTrue(driver.getPageSource().contains("Engineering"), "User department should be updated");

        // Delete
        userPage.deleteUser(email);
        Assert.assertTrue(userPage.isUserPresent(email), "User should be deleted");
    }
}
