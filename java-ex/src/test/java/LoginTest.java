import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by Alex on 17.11.2016.
 */
public class LoginTest extends TestBase {

    @Test
    public void login() throws InterruptedException {
        loginToAdminSection();
        driver.findElement(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']")).click();
    }
}
