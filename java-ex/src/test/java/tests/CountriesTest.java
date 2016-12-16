package tests;

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

        app.getPage().loginToAdminSection();
        app.getPage().navigate(app.properties.getProperty("web.baseUrl")+"/admin/?app=countries&doc=countries");
        app.getPage().waitForElement(By.cssSelector(".dataTable"));
        List<WebElement> tableRows = getTableRows();

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
            clickOnCountryLink(country);
            app.getPage().waitForElement(By.cssSelector("#table-zones"));
            List<WebElement> zones = app.driver.findElements(By.xpath(".//*[@id='table-zones']/tbody//td[3]/input[@type='hidden']"));
            ArrayList<String> zoneNames = new ArrayList<>();
            for (WebElement zone : zones) {
                zoneNames.add(zone.getAttribute("value"));
            }
            checkStringListIsSorted(zoneNames);
            app.getPage().navigate(app.properties.getProperty("web.baseUrl")+"/admin/?app=countries&doc=countries");
            app.getPage().waitForElement(By.cssSelector(".dataTable"));
        }
    }




    @Test
    public void GeoZonesSortingTest(){

        app.getPage().loginToAdminSection();
        app.getPage().navigate("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        app.getPage().waitForElement(By.cssSelector(".dataTable"));

        List<WebElement> tableRows = getTableRows();

        ArrayList<String> countries = new ArrayList<>();
        for (WebElement row:tableRows){
            countries.add(row.findElement(By.xpath("./td[3]/a")).getText());
        }

        for (String country:countries){
            clickOnCountryLink(country);
            app.getPage().waitForElement(By.cssSelector("#table-zones"),20);
            List<WebElement> zoneSelects = app.driver.findElements(By.xpath("//table[@id='table-zones']//td[3]/select/option[@selected='selected']"));
            ArrayList<String> zoneNames = new ArrayList<>();
            for (WebElement select:zoneSelects){
                zoneNames.add(select.getText());
            }

            checkStringListIsSorted(zoneNames);

            app.getPage().navigate("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            app.getPage().waitForElement(By.cssSelector(".dataTable"));
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

    private List<WebElement> getTableRows() {
        return app.driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']"));
    }

    private void clickOnCountryLink(String country) {
        app.driver.findElement(By.xpath(String.format("//table[@class='dataTable']//tr[@class='row']//a[contains(.,'%s')]", country))).click();
    }
}

