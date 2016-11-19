import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by Alex on 17.11.2016.
 */
public class LoginTest extends TestBase {

    @Test
    public void Login() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath(".//*[@id='sidebar']//i[@class='fa fa-sign-out fa-lg']")).click();
    }
}
