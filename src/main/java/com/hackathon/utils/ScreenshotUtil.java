package com.hackathon.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Path.of("test-output/reports/screenshots", testName + ".png");
            Files.createDirectories(dest.getParent());
            Files.copy(src.toPath(), dest);
            return dest.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
