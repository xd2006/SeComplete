import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Created by Alex on 17.11.2016.
 */
public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties properties;

    @Before
    public void start() throws IOException {

        properties = new Properties();
        String target = System.getProperty("target", "local");
//        String target = System.getProperty("target","remote");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));


        DesiredCapabilities caps = new DesiredCapabilities();

        if ("".equals(properties.getProperty("selenium.server"))) {
            caps.setCapability(FirefoxDriver.MARIONETTE, true);
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Mozilla Firefox ESR\\firefox.exe"));
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Nightly\\firefox.exe"));
//        driver = new FirefoxDriver(bin, new FirefoxProfile(),caps);

            //driver = new FirefoxDriver(caps);

            driver = new ChromeDriver();
        } else {
            caps.setBrowserName(System.getProperty("browser", CHROME));
            //caps.setPlatform(Platform.fromString(System.getProperty("platform","windows")));
            driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), caps);
        }
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


    protected void waitForElement(final By locator) {
        wait.until((WebDriver d) -> d.findElements(locator).size() > 0);
    }

    protected void waitForElement(final By locator, int timeToWaitSec) {
        WebDriverWait waitCustom = new WebDriverWait(driver, timeToWaitSec);
        waitCustom.until((WebDriver d) -> d.findElements(locator).size() > 0);
    }

    protected void loginToAdminSection() {
        navigate(properties.getProperty("web.baseUrl") + "/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        waitForElement(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']"));
    }

    protected void loginToUserSection(String email, String password) {
        type(By.xpath("//input[@name='email']"), email);
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

    protected Boolean isElementExists(By locator) {
        try {
            setTimeout(1);
            List<WebElement> elements = driver.findElements(locator);
            return (elements.size() > 0);
        } finally {
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
        navigate(properties.getProperty("web.baseUrl"));
        By goodLocator = By.xpath("//li[@class='product column shadow hover-light']");
        waitForElement(goodLocator);
    }

    protected void navigateInMenu(String section) {
        click(By.xpath(String.format(".//li[@id='app-']/a/span[.='%s']", section)));
    }

    protected ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    protected void checkLog(boolean onlyErrors) throws Exception {

        List<LogEntry> logEntries;
        String message;

        if (onlyErrors) {
            logEntries = driver.manage().logs().get("browser").getAll().stream()
                    .filter(l -> l.getLevel().equals(Level.SEVERE) || l.getLevel().equals(Level.WARNING)).collect(Collectors.toList());
            message = "Some errors appeared in browser log. Only 1st one is displayed:";
        } else {
            logEntries = driver.manage().logs().get("browser").getAll();
            message = "Some messages appeared in browser log. Only 1st one is displayed:";
        }

        if (logEntries.size() > 0) {
            throw new Exception(message + logEntries.get(0).getMessage());
        }
    }
}
