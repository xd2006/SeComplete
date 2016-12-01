import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by Alex on 01.12.2016.
 */
public class RegistrationTests extends TestBase {

    @Test
    public void CreateUser(){

        driver.get("http://localhost/litecart/");
        By goodLocator = By.xpath("//li[@class='product column shadow hover-light']");
        waitForElement(goodLocator);

        driver.findElement(By.xpath("//a[contains(.,'New customers click here')]")).click();



    }
}
