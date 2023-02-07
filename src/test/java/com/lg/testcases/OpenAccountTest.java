package com.lg.testcases;

import com.lg.base.TestBase;
import com.lg.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "genericDataProvider")
    public void openAccountTest(HashMap<String, String> data) {
        skipTestIfTestDataIsDisabled(data);
        click(By.cssSelector(selectors.getProperty("openAccountBtn")));
        select(By.id(selectors.getProperty("customerNameDropdown")), data.get("customer"));
        select(By.id(selectors.getProperty("currencyDropdown")), data.get("currency"));
        click(By.cssSelector(selectors.getProperty("processAccountCreationBtn")));
        validateAndAcceptAlert("Account created successfully");
    }
}
