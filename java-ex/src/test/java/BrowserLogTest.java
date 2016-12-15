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
        String productsPage = properties.getProperty("web.baseUrl")+"/admin/?app=catalog&doc=catalog&category_id=1";
        By productItemLocator = By.xpath(".//form/table/tbody//td[./img]/a");

        loginToAdminSection();
        navigate(productsPage);
                List<WebElement> products = driver.findElements(productItemLocator);
        for (int i=0; i<products.size();i++){
            WebElement currentProduct =  products.get(i);
            currentProduct.click();
            wait.until(ExpectedConditions.stalenessOf(currentProduct));
            checkLog(false);
            navigate(productsPage);
            products = driver.findElements(productItemLocator);
            checkLog(false);
        }

    }

}
