package com.lg.testcases;

import com.lg.base.TestBase;
import com.lg.utilities.TestUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAsBankManagerTest extends TestBase {
    @Test
    public void loginAsBankManagerTest() {
        click(By.xpath(selectors.getProperty("bankManagerLoginBtn")));
        Assert.assertTrue(isElementPresent(By.cssSelector(selectors.getProperty("addCustomerBtn"))));
    }
}
