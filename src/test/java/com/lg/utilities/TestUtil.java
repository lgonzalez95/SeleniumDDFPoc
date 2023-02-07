package com.lg.utilities;

import com.lg.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;

public class TestUtil extends TestBase {
    private final static String fileName = "src/test/resources/excel/TestData.xlsx";

    public static String captureScreenshot() throws IOException {
        String screenshotName = getCurrentTimestamp() + ".jpg";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("target/surefire-reports/html/" + screenshotName));
        return screenshotName;
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @DataProvider(name = "genericDataProvider")
    public static Object[][] getData(Method method) {
        return ExcelReader.getExcelData(fileName, method.getName());
    }

    public static boolean isTestRunnable(String testName, String sheetName) {
        Object[][] data = ExcelReader.getTestSuiteData(fileName, sheetName);
        for (Object[] datum : data) {
            HashMap<String, String> details = (HashMap<String, String>) datum[0];
            if (details.get("TestName").equalsIgnoreCase(testName)) {
                if (details.get("RunMode").equalsIgnoreCase("Y")) {
                    return true;
                }
            }
        }
        return false;
    }

}
