import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 24.11.2016.
 */
public class MainMenuTest extends TestBase {


    @Test
    public void mainMenuCheckTest() throws InterruptedException {

        loginToAdminSection();
        Thread.sleep(1000); //temporary, just for stability in FF

        driver.findElement(By.cssSelector("#app->a")).click();
        checkHeader();

        boolean itemExist;
        do {
            boolean subItemExists;

            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            List<WebElement> submenuItems = driver.findElements(By.xpath(".//li[@id='app-' and @class='selected']//li[contains(@id,'doc-')]/a"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            if (submenuItems.size()>0) {
                submenuItems.get(0).click();
                checkHeader();
                do {
                    subItemExists = checkMenuItems(
                            By.xpath(".//li[@id='app-' and @class='selected']//li[contains(@id,'doc-') and @class='selected']/following-sibling::li[contains(@id,'doc-')]/a"));
                }while (subItemExists);
                }
            itemExist = checkMenuItems(By.xpath(".//li[@id='app-' and @class='selected']/following-sibling::li[@id='app-']/a"));
        }while (itemExist);

    }

    private boolean checkMenuItems(By referenceItemLocator) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> items = driver.findElements(referenceItemLocator);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (items.size()>0) {
            items.get(0).click();
            checkHeader();
            return true;
        }
        return false;
    }


    private void checkHeader() {
        By headerLocator = By.xpath(".//*[@id='content']/h1");
        waitForElement(headerLocator);
        int headersNumber = driver.findElements(headerLocator).size();
        Assert.assertTrue("There are more than 1 header on the page", headersNumber==1);

    }
}
