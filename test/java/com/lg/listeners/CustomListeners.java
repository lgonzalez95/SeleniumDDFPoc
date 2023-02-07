package com.lg.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.lg.base.TestBase;
import com.lg.utilities.ExtentManager;
import com.lg.utilities.TestUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;


public class CustomListeners extends TestBase implements ITestListener {
    private static final ExtentReports extent = ExtentManager.createInstance("target/surefire-reports/html/extended-report.html");
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(result.getTestClass().getName() + "     @TestCase : " + testName);
        boolean isTestEnabled = TestUtil.isTestRunnable(testName, "TestSuites");
        if(!isTestEnabled){
            result.setStatus(ITestResult.SKIP);
        }
        testReport.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "TEST CASE: " + methodName + " PASSED" + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().pass(m);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
                + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>" + " \n");

        try {
            String screenshotName = TestUtil.captureScreenshot();
            testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotName)
                            .build());
        } catch (IOException ignored) {

        }

        String failureLogg = "TEST CASE FAILED";
        Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
        testReport.get().log(Status.FAIL, m);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        testReport.get().skip(m);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext result) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
