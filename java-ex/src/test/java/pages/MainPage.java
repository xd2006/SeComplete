package pages;

import app.Application;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 16.12.2016.
 */
public class MainPage extends Page {

    public MainPage(Application app) {
        super(app);
    }


    public void addRandomProductsToCart(int numberOfProducts ) {
        for (int i=1; i<=numberOfProducts;i++){
            addProductToCart(i);
        }
    }

    public void clickCheckoutLink(){
        click(By.xpath("//a[contains(.,'Checkout')]"));
        app.getCartPage().waitPageIsReady();
    }


    private void addProductToCart(int productNumber) {

        List<WebElement> goods = app.driver.findElements(By.xpath("//*[@id='box-most-popular']//li[@class='product column shadow hover-light']"));

        WebElement product = goods.get(ThreadLocalRandom.current().nextInt(0, goods.size()));
        product.click();
        app.getProductPage().addCurrentProductToCart();
        wait.until(waitQuantityInCartUpdated(productNumber));
        navigate(app.properties.getProperty("web.baseUrl"));
    }


    private Predicate<WebDriver> waitQuantityInCartUpdated(int quantity) {
        return (WebDriver d) -> d.findElement(By.xpath(".//div[@id='cart']//span[@class='quantity']")).getText().equals(String.valueOf(quantity));
    }
}
