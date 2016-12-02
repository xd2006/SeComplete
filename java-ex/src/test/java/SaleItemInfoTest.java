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

        navigate("http://localhost/litecart/");
        By campaignGoodLocator = By.xpath("//div[@id='box-campaigns']//li[@class='product column shadow hover-light']");
        waitForElement(campaignGoodLocator);
        WebElement item = driver.findElement(campaignGoodLocator);

        SaleItem testItemData = populateItemDataFromMainPage(item);
        item.click();
        waitForElement(By.xpath("//h1[contains(@class,'title')]"));
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
        testItem.setName(driver.findElement(By.xpath("//h1[contains(@class,'title')]")).getText());
        testItem.setManufacturer((driver.findElement(By.xpath("//div[@id='box-product']//div[@class='manufacturer']/a/img")).getAttribute("title")));
        testItem.setRegularPrice((driver.findElement(By.xpath("//div[@id='box-product']//div[@class='price-wrapper']/s[@class='regular-price']")).getText()));
        //style check is in the locator itself -s[@class='regular-price']
        testItem.setCampaignPrice((driver.findElement(By.xpath("//div[@id='box-product']//div[@class='price-wrapper']/strong[@class='campaign-price']")).getText()));
        //style check is in the locator itself - strong[@class='campaign-price']
        return testItem;
    }

}
