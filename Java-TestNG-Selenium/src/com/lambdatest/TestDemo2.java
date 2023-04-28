package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestDemo2 {
    private RemoteWebDriver driver;
    boolean status = false;
    //-------------------------------------------------------------------------------
    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        EdgeOptions browserOptions = new EdgeOptions();
        caps.setCapability("platform", "macOS High Sierra");
        caps.setCapability("browser", "MicrosoftEdge");
        caps.setCapability("version","87.0");
        caps.setCapability("name", "Test-Lambda-macOS");
        caps.setCapability("tunnel", false);
        caps.setCapability("video", true);
        caps.setCapability("console", true);
        caps.setCapability("network", true);
        caps.setCapability("visual", true);
        caps.setCapability("geoLocation", "SA");
        caps.setCapability("resolution", "2560x1440");
        caps.setCapability("build", "TestLambdaDemo");
        caps.setCapability("w3c", true);
        caps.setCapability("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", caps);
        driver = new RemoteWebDriver(new URL("https://fmajrashi:MZujJtX0bBU15JGWCDUOxukjFqxDYMm2teXwgCrcwdGKwJeLz7@hub.lambdatest.com/wd/hub"),browserOptions);
    }
    //--------------------------------StartTesting-----------------------------------
    @Test(timeOut = 20000)
    public void TestDemo2() throws InterruptedException {
        System.out.println("Opening Url");
        driver.get("https://www.lambdatest.com./");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.xpath("//img[@title='Jenkins']"));
        driver.executeScript("arguments[0].scrollIntoView(true);", element);
        WebElement element1 = driver.findElement(By.xpath("//img[@title='See All Integrations']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element1)
                .keyDown(Keys.COMMAND)
                .click()
                .keyUp(Keys.COMMAND)
                .build()
                .perform();
        System.out.println("Link open in new tab");

        //--------------------------------OpenLinkNewTab-----------------------------------
        String CurrentParent = driver.getWindowHandle();
        Set<String> AllWindowHandles = driver.getWindowHandles();
        List AllWindows = new ArrayList(AllWindowHandles);
        System.out.println(AllWindows);
        for (String child_Tabs_Window : AllWindowHandles) {
            if (!CurrentParent.equals(child_Tabs_Window)) {
                System.out.println(driver.switchTo().window(CurrentParent).getTitle());
                driver.switchTo().window(child_Tabs_Window);
                System.out.println(driver.switchTo().window(child_Tabs_Window).getTitle());
                WebElement LambdaTxt = driver.findElement(By.xpath("//h1[normalize-space()='LambdaTest Integrations']"));
                Assert.assertEquals(LambdaTxt.getText(), "LambdaTest Integrations");
                String Url = driver.switchTo().window(child_Tabs_Window).getCurrentUrl();
                Assert.assertEquals(Url, "https://www.lambdatest.com/integrations");
                driver.close();
            }
        }
    }
    //----------------------------------CloseCurrentBrowserWindow---------------------------------
    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}