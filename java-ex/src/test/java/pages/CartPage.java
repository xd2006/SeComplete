package pages;

import app.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Alex on 16.12.2016.
 */
public class CartPage extends Page {

    public CartPage(Application app) {
        super(app);
    }

    public void waitPageIsReady(){
        By table = By.xpath(".//div[@id='box-checkout-summary']");
        waitForElement(table);
    }

    public void removeAllItemsFromCart() {
        int items;
        do {
            By itemShortcut = By.xpath(".//div[@id='box-checkout-cart']//ul[@class='shortcuts']/li/a");

            setTimeout(1);
            List<WebElement> productsInCart = driver.findElements(itemShortcut);
            setTimeout(10);
            items = productsInCart.size();
            if (items > 0) {
                productsInCart.get(0).click();

                WebElement removeButton = driver.findElement(By.xpath("//button[@name='remove_cart_item']"));
                wait.until(ExpectedConditions.elementToBeClickable(removeButton));

                By firstQuantityTableCell = By.xpath(".//*[@id='order_confirmation-wrapper']/table/tbody/tr[2]/td[1]");
                WebElement cell = driver.findElement(firstQuantityTableCell);
                removeButton.click();
                wait.until(ExpectedConditions.stalenessOf(cell));
            }
        }while (items!=0);
    }
}
