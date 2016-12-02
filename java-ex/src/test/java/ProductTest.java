import org.junit.Test;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Alex on 02.12.2016.
 */
public class ProductTest extends TestBase{

    @Test
    public void addProduct(){

        loginToAdminSection();
        navigateInMenu("Catalog");
        click(By.xpath("//a[contains(.,' Add New Product')]"));



        LocalDate localDate = LocalDate.now();
        String startDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
        String endDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate.plusMonths(6));




    }

}
