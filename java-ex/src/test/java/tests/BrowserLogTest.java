package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Alex on 14.12.2016.
 */
public class BrowserLogTest extends TestBase {

    @Test
    public void productsCheckTest() throws Exception {
        String productsPage = app.properties.getProperty("web.baseUrl")+"/admin/?app=catalog&doc=catalog&category_id=1";
        By productItemLocator = By.xpath(".//form/table/tbody//td[./img]/a");

        app.getPage().loginToAdminSection();
        app.getPage().navigate(productsPage);
                List<WebElement> products = app.driver.findElements(productItemLocator);
        for (int i=0; i<products.size();i++){
            WebElement currentProduct =  products.get(i);
            currentProduct.click();
            app.wait.until(ExpectedConditions.stalenessOf(currentProduct));
            app.getPage().checkLog(false);
            app.getPage().navigate(productsPage);
            products = app.driver.findElements(productItemLocator);
            app.getPage().checkLog(false);
        }

    }

}
