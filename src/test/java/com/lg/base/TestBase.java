package com.lg.base;

import com.aventstack.extentreports.Status;
import com.lg.listeners.CustomListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;

public abstract class TestBase {
    public static WebDriver driver;
    public static Properties configProperties = new Properties();
    public static Properties selectors = new Properties();
    public static Logger logger = LogManager.getRootLogger();
    public static WebDriverWait wait;

    /**
     * WebDriver
     * Properties
     * Logs
     * ExtentReports
     * DB
     * Excel
     * Mail
     * ReportNG, ExtentReports
     * Jenkins
     */

    @BeforeSuite
    public void setUp() throws IOException {
        FileInputStream filePropertiesData;
        if (driver == null) {
            filePropertiesData = new FileInputStream("src/test/resources/properties/Config.properties");
            configProperties.load(filePropertiesData);
            filePropertiesData = new FileInputStream("src/test/resources/properties/OR.properties");
            selectors.load(filePropertiesData);

            if (configProperties.getProperty("browser").equals("chrome")) {
                logger.info("Chrome browser will be used for this execution");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            } else if (configProperties.getProperty("browser").equals("firefox")) {
                logger.info("Firefox browser will be used for this execution");
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless=new");
                driver = new FirefoxDriver(options);
            }
        }
        logger.info("Loading the base url");
        driver.get(configProperties.getProperty("baseUrl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(configProperties.getProperty("implicitWaitDurationSeconds"))));
        logger.info("The page was loaded successfully!");
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(configProperties.getProperty("explicitWaitDurationSeconds"))));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            logger.warn("Removing driver instance!");
            driver.quit();
            logger.info("Driver instance removed!");
        }
    }

    public void skipTestIfTestDataIsDisabled(HashMap<String, String> data){
        if(!data.get("RunMode").equals("Y")){
            throw new SkipException("Skipping the test case as the Run mode for data set is NO");
        }
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void click(By by) {
        CustomListeners.testReport.get().log(Status.INFO, "Clicking element <b>" + by.toString() + "</b>");
        driver.findElement(by).click();
    }

    public void type(By by, String value) {
        CustomListeners.testReport.get().log(Status.INFO, "Typing the text <b>" + value + "</b> into the element "+ by.toString());
        driver.findElement(by).sendKeys(value);
    }

    public void select(By by, String visibleText){
        CustomListeners.testReport.get().log(Status.INFO, "Selecting <b>" + visibleText + "</b> from the dropdown "+ by.toString());
        Select select = new Select(driver.findElement(by));
        select.selectByVisibleText(visibleText);
    }

    public void validateAndAcceptAlert(String expectedAlertText){
        CustomListeners.testReport.get().log(Status.INFO, "Validating alert with the text <b>" + expectedAlertText + "</b>");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(expectedAlertText));
        alert.accept();
    }
}
