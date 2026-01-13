package com.hackathon.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.hackathon.reports.ExtentManager;
import com.hackathon.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.util.concurrent.ConcurrentHashMap;

public class ExtentTestListener implements ITestListener, ISuiteListener {

    private static final ConcurrentHashMap<String, ExtentTest> testMap = new ConcurrentHashMap<>();

    @Override
    public void onStart(ISuite suite) {
        ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTest test = ExtentManager.getInstance().createTest(testName);
        testMap.put(testName, test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testMap.get(result.getMethod().getMethodName()).log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        String screenshotPath = driver != null ? ScreenshotUtil.captureScreenshot(driver, testName) : null;

        ExtentTest test = testMap.get(testName);
        test.fail(result.getThrowable());
        if (screenshotPath != null) {
            try {
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                test.warning("Screenshot could not be attached: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testMap.get(result.getMethod().getMethodName()).log(Status.SKIP, "Test skipped");
    }
}
