package pages;

import app.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by Alex on 16.12.2016.
 */
public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Application app;

    public Page(Application app) {
        this.app = app;
        this.driver = app.driver;
        this.wait = app.wait;


    }

    public void waitForElement(final By locator) {
        wait.until((WebDriver d) -> d.findElements(locator).size() > 0);
    }

    public void waitForElement(final By locator, int timeToWaitSec) {
        WebDriverWait waitCustom = new WebDriverWait(app.driver, timeToWaitSec);
        waitCustom.until((WebDriver d) -> d.findElements(locator).size() > 0);
    }

    public void loginToAdminSection() {
        navigate(app.properties.getProperty("web.baseUrl") + "/admin/");
        if (!isElementExists(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']"))) {
            app.driver.findElement(By.name("username")).sendKeys("admin");
            app.driver.findElement(By.name("password")).sendKeys("admin");
            app.driver.findElement(By.name("login")).click();
            waitForElement(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']"));
        }
    }

    public void loginToUserSection(String email, String password) {
        if (!isElementExists(By.xpath("//a[contains(@href,'/logout')]"))) {
            type(By.xpath("//input[@name='email']"), email);
            type(By.xpath("//input[@name='password']"), password);
            click(By.xpath("//button[@name='login']"));
            waitForElement(By.xpath("//a[contains(@href,'/logout')]"));
        }
    }

    public void selectCheckox(By locator) {
        WebElement element = app.driver.findElement(locator);
        if (!element.isSelected()) element.click();
    }

    public void selectCheckox(WebElement element) {
        if (!element.isSelected()) element.click();
    }

    public void deselectCheckox(By locator) {
        WebElement element = app.driver.findElement(locator);
        if (element.isSelected()) element.click();
    }

    public void deselectCheckox(WebElement element) {
        if (element.isSelected()) element.click();
    }

    public void type(By locator, String text) {
        if (text != null) {
            String currentText = app.driver.findElement(locator).getAttribute("value");
            if (!text.equals(currentText)) {
                click(locator);
                app.driver.findElement(locator).clear();
                app.driver.findElement(locator).sendKeys(text);
            }
        }
    }

    public void typeMaskField(By locator, String text) {
        if (text != null) {
            String currentText = app.driver.findElement(locator).getAttribute("value");
            if (!text.equals(currentText)) {
                click(locator);
                app.driver.findElement(locator).sendKeys(text);
            }
        }
    }

    public void click(By locator) {
        app.driver.findElement(locator).click();
    }

    public void attach(By locator, File file) {
        if (file != null) {

            app.driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }
    public Boolean isElementExists(By locator) {
        try {
            setTimeout(1);
            List<WebElement> elements = app.driver.findElements(locator);
            return (elements.size() > 0);
        } finally {
            setTimeout(10);
        }
    }

    protected String getValue(By locator) {
        return app.driver.findElement(locator).getAttribute("value");
    }

    public void select(By selectLocator, String itemText) {
        if (itemText != null) {
            Select select = new Select(app.driver.findElement(selectLocator));
            if (select.getFirstSelectedOption().getText() != itemText) {
                new Select(app.driver.findElement(selectLocator)).selectByVisibleText(itemText);
            }
        }
    }

    public void setTimeout(int timeoutSeconds) {

        app.driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
    }

    public void navigate(String address) {
        app.driver.get(address);
    }

    public void startApp() {
        navigate(app.properties.getProperty("web.baseUrl"));
        By goodLocator = By.xpath("//li[@class='product column shadow hover-light']");
        waitForElement(goodLocator);
    }

    public void navigateInMenu(String section) {
        click(By.xpath(String.format(".//li[@id='app-']/a/span[.='%s']", section)));
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                Set<String> handles = app.driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public void checkLog(boolean onlyErrors) throws Exception {

        List<LogEntry> logEntries;
        String message;

        if (onlyErrors) {
            logEntries = app.driver.manage().logs().get("browser").getAll().stream()
                    .filter(l -> l.getLevel().equals(Level.SEVERE) || l.getLevel().equals(Level.WARNING)).collect(Collectors.toList());
            message = "Some errors appeared in browser log. Only 1st one is displayed:";
        } else {
            logEntries = app.driver.manage().logs().get("browser").getAll();
            message = "Some messages appeared in browser log. Only 1st one is displayed:";
        }

        if (logEntries.size() > 0) {
            throw new Exception(message + logEntries.get(0).getMessage());
        }
    }


}
