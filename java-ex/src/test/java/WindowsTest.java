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

        loginToAdminSection();
        navigate("http://localhost/litecart/admin/?app=countries&doc=countries");
        click(By.xpath("//a[contains(.,' Add New Country')]"));
        By externalLinkLocator = By.xpath("//i[contains(@class,'fa fa-external-link')]");
        waitForElement(externalLinkLocator);
        List<WebElement> links = driver.findElements(externalLinkLocator);

        String currentWindowHandle = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        for (WebElement link : links) {
            link.click();
            String newWindow = wait.until(anyWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);
//            waitForElement(By.xpath("//html")); //optional: wait that some content is loaded in a new window
            driver.close();
            driver.switchTo().window(currentWindowHandle);

        }

    }


}
