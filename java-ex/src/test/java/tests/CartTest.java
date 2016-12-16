package tests;

import com.google.common.base.Predicate;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 04.12.2016.
 */
public class CartTest extends TestBase {


    @Test
    public void addToCartTest() throws InterruptedException {

        app.getPage().startApp();

        for (int i=1; i<=3;i++ ){
            addProductToCart(i);
        }
        app.getPage().click(By.xpath("//a[contains(.,'Checkout')]"));
        By table = By.xpath(".//div[@id='box-checkout-summary']");
        app.getPage().waitForElement(table);

        removeAllItemsFromCart();

    }

    private void removeAllItemsFromCart() {
        int items;
        do {
            By itemShortcut = By.xpath(".//div[@id='box-checkout-cart']//ul[@class='shortcuts']/li/a");

            app.getPage().setTimeout(1);
            List<WebElement> productsInCart = app.driver.findElements(itemShortcut);
            app.getPage().setTimeout(10);
            items = productsInCart.size();
            if (items > 0) {
                productsInCart.get(0).click();

                WebElement removeButton = app.driver.findElement(By.xpath("//button[@name='remove_cart_item']"));
                app.wait.until(ExpectedConditions.elementToBeClickable(removeButton));

                By firstQuantityTableCell = By.xpath(".//*[@id='order_confirmation-wrapper']/table/tbody/tr[2]/td[1]");
                WebElement cell = app.driver.findElement(firstQuantityTableCell);
                removeButton.click();
                app.wait.until(ExpectedConditions.stalenessOf(cell));
            }
        }while (items!=0);
    }

    private void addProductToCart(int quantity) throws InterruptedException {

        List<WebElement> goods = app.driver.findElements(By.xpath("//*[@id='box-most-popular']//li[@class='product column shadow hover-light']"));

        WebElement product = goods.get(ThreadLocalRandom.current().nextInt(0, goods.size()));
        product.click();
        By addToCartButton = By.xpath("//button[@name='add_cart_product']");
        app.getPage().waitForElement(addToCartButton);
        By sizeSelector = By.xpath("//select[@name='options[Size]']");
        if (app.getPage().isElementExists(sizeSelector))
        {
            app.getPage().select(sizeSelector, "Small");
        }
        app.getPage().click(addToCartButton);

        app.wait.until(waitQuantityInCartUpdated(quantity));
        app.getPage().navigate(app.properties.getProperty("web.baseUrl"));
    }

    private Predicate<WebDriver> waitQuantityInCartUpdated(int quantity) {
        return (WebDriver d) -> d.findElement(By.xpath(".//div[@id='cart']//span[@class='quantity']")).getText().equals(String.valueOf(quantity));
    }
}


