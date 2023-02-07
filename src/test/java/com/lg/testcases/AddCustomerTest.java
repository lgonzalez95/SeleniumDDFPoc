package com.lg.testcases;

import com.lg.base.TestBase;
import com.lg.utilities.TestUtil;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AddCustomerTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "genericDataProvider")
    public void addCustomerTest(HashMap<String, String> data) throws InterruptedException {
        skipTestIfTestDataIsDisabled(data);
        click(By.cssSelector(selectors.getProperty("addCustomerBtn")));
        type((By.cssSelector(selectors.getProperty("firstName"))), data.get("firstName"));
        type(By.cssSelector(selectors.getProperty("lastName")), data.get("lastName"));
        type(By.cssSelector(selectors.getProperty("postCode")), data.get("postcode"));
        Thread.sleep(1000);
        click(By.cssSelector(selectors.getProperty("createCustomerBtn")));
        validateAndAcceptAlert(data.get("alertText"));
    }
}
