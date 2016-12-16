package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Alex on 15.11.2016.
 */
public class SampleTest extends TestBase {




    @Test
    public void myFirstTest() {
        app.driver.navigate().to("http://www.google.com");
        app.driver.findElement(By.name("q")).sendKeys("webdriver");
        app.driver.findElement(By.name("btnG")).click();
        app.wait.until(titleIs("webdriver - Поиск в Google"));
    }




}
