import org.junit.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 02.12.2016.
 */
public class ProductTest extends TestBase{

    @Test
    public void addProduct(){

        loginToAdminSection();
        navigateInMenu("Catalog");
        click(By.xpath("//a[contains(.,' Add New Product')]"));

        getImageFile();


        LocalDate localDate = LocalDate.now();
        String startDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
        String endDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate.plusMonths(6));




    }

    private File getImageFile() {
        List<File> files = new ArrayList<File>();
        files.add(new File("src/test/resources/chair.jpg"));
        files.add(new File("src/test/resources/q6600.jpg"));
        files.add(new File("src/test/resources/stand.jpg"));
        return files.get(ThreadLocalRandom.current().nextInt(0,files.size()));
    }

}
