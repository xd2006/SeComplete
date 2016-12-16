package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

/**
 * Created by Alex on 07.12.2016.
 */
public class WindowsTest extends TestBase {

    @Test
    public void countriesLinks() {

        app.getPage().loginToAdminSection();
        app.getPage().navigate("http://localhost/litecart/admin/?app=countries&doc=countries");
        app.getPage().click(By.xpath("//a[contains(.,' Add New Country')]"));
        By externalLinkLocator = By.xpath("//i[contains(@class,'fa fa-external-link')]");
        app.getPage().waitForElement(externalLinkLocator);
        List<WebElement> links = app.driver.findElements(externalLinkLocator);

        String currentWindowHandle = app.driver.getWindowHandle();
        Set<String> oldWindows = app.driver.getWindowHandles();

        for (WebElement link : links) {
            link.click();
            String newWindow = app.wait.until(anyWindowOtherThan(oldWindows));
            app.driver.switchTo().window(newWindow);
//            waitForElement(By.xpath("//html")); //optional: wait that some content is loaded in a new window
            app.driver.close();
            app.driver.switchTo().window(currentWindowHandle);

        }

    }


}
