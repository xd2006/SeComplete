import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
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

    protected void waitForElement(final By locator, int timeToWaitSec){
        WebDriverWait waitCustom = new WebDriverWait(driver,timeToWaitSec);
        waitCustom.until((WebDriver d)->d.findElements(locator).size()>0);
    }

    protected void loginToAdminSection() {
        navigate("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        waitForElement(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']"));
    }

    protected void loginToUserSection(String email, String password){
        type(By.xpath("//input[@name='email']"),email);
        type(By.xpath("//input[@name='password']"), password);
        click(By.xpath("//button[@name='login']"));
        waitForElement(By.xpath("//a[contains(@href,'/logout')]"));
    }

    protected void selectCheckox(By locator) {
        WebElement element = driver.findElement(locator);
        if (!element.isSelected()) element.click();
    }

    protected void selectCheckox(WebElement element) {
        if (!element.isSelected()) element.click();
    }

    protected void deselectCheckox(By locator) {
        WebElement element = driver.findElement(locator);
        if (element.isSelected()) element.click();
    }

    protected void deselectCheckox(WebElement element) {
        if (element.isSelected()) element.click();
    }

    protected void type(By locator, String text) {
        if (text != null) {
            String currentText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(currentText)) {
                click(locator);
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void typeMaskField(By locator, String text) {
        if (text != null) {
            String currentText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(currentText)) {
                click(locator);
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void attach(By locator, File file) {
        if (file != null) {

            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected Boolean isElementExists(By locator){
        try {
            setTimeout(1);
            List<WebElement> elements = driver.findElements(locator);
            return (elements.size() > 0);
        }finally{
            setTimeout(10);
        }
    }

    protected String getValue(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }

    protected void select(By selectLocator, String itemText) {
        if (itemText != null) {
            Select select = new Select(driver.findElement(selectLocator));
            if (select.getFirstSelectedOption().getText() != itemText) {
                new Select(driver.findElement(selectLocator)).selectByVisibleText(itemText);
            }
        }
    }

    protected void setTimeout(int timeoutSeconds) {

        driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
    }

    protected void navigate(String address) {
        driver.get(address);
    }

    protected void startApp() {
        navigate("http://localhost/litecart/");
        By goodLocator = By.xpath("//li[@class='product column shadow hover-light']");
        waitForElement(goodLocator);
    }

    protected void navigateInMenu(String section) {
        click(By.xpath(String.format(".//li[@id='app-']/a/span[.='%s']",section)));
    }
}
