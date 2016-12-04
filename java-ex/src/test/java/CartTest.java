import com.google.common.base.Predicate;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 04.12.2016.
 */
public class CartTest extends TestBase {


    @Test
    public void addToCartTest() throws InterruptedException {

        startApp();

        for (int i=1; i<=3;i++ ){
            addProductToCart(i);
        }

        

    }

    private void addProductToCart(int quantity) throws InterruptedException {
        List<WebElement> goods = driver.findElements(By.xpath("//*[@id='box-most-popular']//li[@class='product column shadow hover-light']"));


        WebElement product = goods.get(ThreadLocalRandom.current().nextInt(0, goods.size()));
        product.click();
        By addToCartButton = By.xpath("//button[@name='add_cart_product']");
        waitForElement(addToCartButton);
        By sizeSelector = By.xpath("//select[@name='options[Size]']");
        if (isElementExists(sizeSelector))
        {
            select(sizeSelector, "Small");
        }
        click(addToCartButton);

        wait.until(waitQuantityInCartUpdated(quantity));
        navigate("http://localhost/litecart");
    }

    private Predicate<WebDriver> waitQuantityInCartUpdated(int quantity) {
        return (WebDriver d) -> d.findElement(By.xpath(".//div[@id='cart']//span[@class='quantity']")).getText().equals(String.valueOf(quantity));
    }


//    private Function<WebDriver, List<WebElement>> getWebDriverListFunction() {
//        return (WebDriver d) -> {List<WebElement> l = d.findElements(By.xpath("//td"));
//            return l.size()==10 ? l:null;};

}


