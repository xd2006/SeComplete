package tests;

import model.SaleItem;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Alex on 27.11.2016.
 */
public class SaleItemInfoTest extends TestBase {


    @Test
    public void checkItemDataTest() {

        app.getPage().navigate("http://localhost/litecart/");
        By campaignGoodLocator = By.xpath("//div[@id='box-campaigns']//li[@class='product column shadow hover-light']");
        app.getPage().waitForElement(campaignGoodLocator);
        WebElement item = app.driver.findElement(campaignGoodLocator);

        SaleItem testItemData = populateItemDataFromMainPage(item);
        item.click();
        app.getPage().waitForElement(By.xpath("//h1[contains(@class,'title')]"));
        SaleItem testItemDataDetails = populateItemDataFromDetailsPage();
        Assert.assertTrue("There are differences in sale item's data on details and main pages",
                testItemData.equals(testItemDataDetails));


    }


    private SaleItem populateItemDataFromMainPage(WebElement item) {

        SaleItem testItem = new SaleItem();
        testItem.setName(item.findElement(By.xpath(".//div[@class='name']")).getText());
        testItem.setManufacturer((item.findElement(By.xpath(".//div[@class='manufacturer']")).getText()));
        testItem.setRegularPrice((item.findElement(By.xpath(".//div[@class='price-wrapper']/s[@class='regular-price']")).getText()));
        //style check is in the locator itself -s[@class='regular-price']
        testItem.setCampaignPrice((item.findElement(By.xpath(".//div[@class='price-wrapper']/strong[@class='campaign-price']")).getText()));
        //style check is in the locator itself - strong[@class='campaign-price']
        return testItem;
    }


    private SaleItem populateItemDataFromDetailsPage() {

        SaleItem testItem = new SaleItem();
        testItem.setName(app.driver.findElement(By.xpath("//h1[contains(@class,'title')]")).getText());
        testItem.setManufacturer((app.driver.findElement(By.xpath("//div[@id='box-product']//div[@class='manufacturer']/a/img")).getAttribute("title")));
        testItem.setRegularPrice((app.driver.findElement(By.xpath("//div[@id='box-product']//div[@class='price-wrapper']/s[@class='regular-price']")).getText()));
        //style check is in the locator itself -s[@class='regular-price']
        testItem.setCampaignPrice((app.driver.findElement(By.xpath("//div[@id='box-product']//div[@class='price-wrapper']/strong[@class='campaign-price']")).getText()));
        //style check is in the locator itself - strong[@class='campaign-price']
        return testItem;
    }

}
