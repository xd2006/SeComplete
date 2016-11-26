import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alex on 26.11.2016.
 */
public class CountriesTest extends TestBase {

    @Test
    public void CountriesSortingTest() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        waitForElement(By.cssSelector(".dataTable"));
        List<WebElement> tableRows = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']"));

        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> countriesWithZones = new ArrayList<>();


        for (WebElement row : tableRows) {

            String countryName = row.findElement(By.xpath(".//td[5]")).getText();
            if (!row.findElement(By.xpath(".//td[6]")).getText().equals("0")) {
                countriesWithZones.add(countryName);
            }
            countries.add(countryName);
        }

//        reverseArray(countries); //just for checking
        checkStringListIsSorted(countries);

        for (String country : countriesWithZones) {
            driver.findElement(By.xpath(String.format("//table[@class='dataTable']//tr[@class='row']//a[contains(.,'%s')]", country))).click();
            waitForElement(By.cssSelector("#table-zones"));
            List<WebElement> zones = driver.findElements(By.xpath(".//*[@id='table-zones']/tbody//td[3]/input[@type='hidden']"));
            ArrayList<String> zoneNames = new ArrayList<>();
            for (WebElement zone : zones) {
                zoneNames.add(zone.getAttribute("value"));
            }
            checkStringListIsSorted(zoneNames);
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            waitForElement(By.cssSelector(".dataTable"));
        }
    }


    private void checkStringListIsSorted(ArrayList<String> sourceList) {
        ArrayList<String> initialList = new ArrayList<>();
        initialList.addAll(sourceList);
        Collections.sort(sourceList, String::compareTo);
        Assert.assertTrue("List is not sorted alphabetically", initialList.equals(sourceList));
    }

    private void reverseArray(ArrayList<String> massive) {
        Comparator cmp = Collections.reverseOrder();
        Collections.sort(massive, cmp);
    }
}

