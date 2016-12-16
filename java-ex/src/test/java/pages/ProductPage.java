package pages;

import app.Application;
import org.openqa.selenium.By;

/**
 * Created by Alex on 16.12.2016.
 */
public class ProductPage extends Page {


    public ProductPage(Application app) {
        super(app);
    }

    public void addCurrentProductToCart() {
        By addToCartButton = By.xpath("//button[@name='add_cart_product']");
        waitForElement(addToCartButton);
        By sizeSelector = By.xpath("//select[@name='options[Size]']");
        if (isElementExists(sizeSelector))
        {
            select(sizeSelector, "Small");
        }
        click(addToCartButton);
    }


}
