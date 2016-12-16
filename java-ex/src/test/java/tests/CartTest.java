package tests;

import org.junit.Test;

/**
 * Created by Alex on 04.12.2016.
 */
public class CartTest extends TestBase {


    @Test
    public void addToCartTest() throws InterruptedException {

        app.getPage().openMainPage();
        app.getMainPage().addRandomProductsToCart(3);
        app.getMainPage().clickCheckoutLink();
        app.getCartPage().removeAllItemsFromCart();

    }
}


