package tests;

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

        app.getPage().loginToAdminSection();
        Thread.sleep(1000); //temporary, just for stability in FF

        app.driver.findElement(By.cssSelector("#app->a")).click();
        checkHeader();

        boolean itemExist;
        do {
            boolean subItemExists;

            app.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            List<WebElement> submenuItems = app.driver.findElements(By.xpath(".//li[@id='app-' and @class='selected']//li[contains(@id,'doc-')]/a"));
            app.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
        app.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        List<WebElement> items = app.driver.findElements(referenceItemLocator);
        app.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (items.size()>0) {
            items.get(0).click();
            checkHeader();
            return true;
        }
        return false;
    }


    private void checkHeader() {
        By headerLocator = By.xpath(".//*[@id='content']/h1");
        app.getPage().waitForElement(headerLocator);
        int headersNumber = app.driver.findElements(headerLocator).size();
        Assert.assertTrue("There are more than 1 header on the page", headersNumber==1);

    }
}
