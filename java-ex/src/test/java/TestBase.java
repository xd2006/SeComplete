import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 17.11.2016.
 */
public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void start()
    {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, true);
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Mozilla Firefox ESR\\firefox.exe"));
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Nightly\\firefox.exe"));
//        driver = new FirefoxDriver(bin, new FirefoxProfile(),caps);


       //driver = new FirefoxDriver(caps);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void stop(){
        driver.quit();
        driver=null;
    }


    protected void waitForElement(final By locator){
        wait.until((WebDriver d)->d.findElements(locator).size()>0);
    }

}
