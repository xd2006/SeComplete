import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Alex on 25.11.2016.
 */
public class StickersTest extends TestBase  {

    @Test
    public void goodsStickersTest(){

        navigate("http://localhost/litecart/");
        By goodLocator = By.xpath("//li[@class='product column shadow hover-light']");
        waitForElement(goodLocator);

        List<WebElement> goods = driver.findElements(goodLocator);

        for (WebElement good : goods){
            List<WebElement> stickers = good.findElements(By.xpath(".//div[contains(@class,'sticker' )]"));
            Assert.assertTrue("There should be 1 sticker per good", stickers.size()==1);
        }


    }

}
