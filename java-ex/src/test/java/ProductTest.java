import model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 02.12.2016.
 */
public class ProductTest extends TestBase {

    @Test
    public void addProduct() {

        //Product data

        ArrayList<String> productCategories = new ArrayList<>();
        productCategories.add("Rubber Ducks");
        productCategories.add("Subcategory");

        ArrayList<String> productGroups = new ArrayList<>();
        productGroups.add("Female");
        productGroups.add("Unisex");

        File imageFile = getImageFile();

        LocalDate localDate = LocalDate.now();
        String startDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
        String endDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate.plusMonths(6));
        //----

        loginToAdminSection();
        navigateInMenu("Catalog");
        click(By.xpath("//a[contains(.,' Add New Product')]"));
        String timeStamp = String.valueOf(new java.util.Date().getTime());

        Product testProduct = new Product();
        Product.General productGeneralInfo = testProduct.new General().withEnabledStatus(true).withName("Test Product " + timeStamp)
                .withCode("TestCode" + timeStamp).withCategories(productCategories).withDefaultCategory(productCategories.get(0))
                .withProductGroups(productGroups).withQuantity(150).withSoldOutStatus("Temporary sold out").withQuantityUnit("pcs")
                .withDeliveryStatus("3-5 days").withImage(imageFile).withDateValidFrom(startDate).withDateValidTo(endDate);
        Product.Information productInformation = testProduct.new Information().withManufacturer("ACME Corp.")
                .withKeywords("Keyword1, Keyword2, Keyword3").withShortDescription("Test product short description")
                .withDescription("Lorem ipsum dolor sit amet, his iisque vivendo eu. Mei cu graecis docendi quaestio. " +
                        "Ea mei quod harum consul, iusto constituam nam an, assum dolore vel ei. Eos in simul iudico feugait," +
                        " duis cetero has id. Elitr labore at nec. In per paulo dignissim." +
                        " No illum inciderint consectetuer mea, mei modus eruditi voluptaria ne." +
                        " Pri duis erat similique eu, ne qui altera eloquentiam. " +
                        "Mei ferri liber accusamus ne. Vis te agam facete." +
                        " An enim quas ubique eum, et pri paulo euripidis conclusionemque, cu gubergren maiestatis appellantur sed." +
                        " Libris option ne est. Duis vocent pri ei, zril perpetua rationibus sea ne, et natum antiopam aliquando quo." +
                        " Quo dicunt numquam perfecto te. Feugait inimicus has ad, vim delenit vivendum temporibus eu. Ne eam velit ")
                .withHeadTitle("Head title Test Product" + timeStamp).withMetaDescription("Meta dscr test product");
        Product.Prices productPricesInfo = testProduct.new Prices().withPurchasePrice("28").withCurrency("US Dollars")
                .withPriceUsd("28");
        testProduct.withGeneralTabData(productGeneralInfo).withInformationTabData(productInformation).withPricesTabData(productPricesInfo);

        populateProductData(testProduct);
        click(By.xpath("//button[@name='save']"));
        navigateInMenu("Catalog");
        click(By.xpath(String.format("//a[contains(.,'%s')]", testProduct.getGeneralTabData().getCategories().get(0))));
        Assert.assertTrue("Newly created product wasn't found"
                ,isElementExists(By.xpath(String.format("//a[contains(.,'%s')]",testProduct.getGeneralTabData().getName()))));
    }

    private void populateProductData(Product product) {
        populateGeneralProductData(product.getGeneralTabData());
        click(By.xpath("//a[@href='#tab-information']"));
        populateInformationProductData(product.getInformationTabData());
        click(By.xpath("//a[@href='#tab-prices']"));
        populatePricesProductData(product.getPricesTabData());
    }

    private void populatePricesProductData(Product.Prices pricesTabData) {
        type(By.xpath("//input[@name='purchase_price']"),pricesTabData.getPurchasePrice());
        select(By.xpath("//select[@name='purchase_price_currency_code']"),pricesTabData.getCurrency());
        type(By.xpath("//input[@name='prices[USD]']"), pricesTabData.getPriceUsd());

    }

    private void populateInformationProductData(Product.Information informationTabData) {
        select(By.xpath("//select[@name='manufacturer_id']"), informationTabData.getManufacturer());
        type(By.xpath("//input[@name='keywords']"), informationTabData.getKeywords());
        type(By.xpath("//input[@name='short_description[en]']"),informationTabData.getShortDescription());
        type(By.xpath("//div[@contenteditable='true' and @class='trumbowyg-editor']"),informationTabData.getDescription());
        type(By.xpath("//input[@name='head_title[en]']"),informationTabData.getHeadTitle());
        type(By.xpath("//input[@name='meta_description[en]']"), informationTabData.getMetaDescription());

    }

    private void populateGeneralProductData(Product.General generalTabData) {

        if (generalTabData.isEnabledStatus())
            selectCheckox(By.xpath(".//label[contains(.,'Enabled')]/input[@name='status']"));
        else
            selectCheckox(By.xpath(".//label[contains(.,'Disabled')]/input[@name='status']"));
        type(By.xpath("//input[@name='name[en]']"), generalTabData.getName());
        type(By.xpath("//input[@name='code']"), generalTabData.getCode());
        selectCategories(generalTabData.getCategories());
        selectProductGroups(generalTabData.getProductGroups());
        type(By.xpath("//input[@name='quantity']"), String.valueOf(generalTabData.getQuantity()));
        select(By.xpath("//select[@name='quantity_unit_id']"), generalTabData.getQuantityUnit());
        select(By.xpath("//select[@name='delivery_status_id']"), generalTabData.getDeliveryStatus());
        select(By.xpath("//select[@name='sold_out_status_id']"), generalTabData.getSoldOutStatus());
        attach(By.xpath("//input[@name='new_images[]']"), generalTabData.getImage());
        typeMaskField(By.xpath("//input[@name='date_valid_from']"), generalTabData.getDateValidFrom());
        typeMaskField(By.xpath("//input[@name='date_valid_to']"), generalTabData.getDateValidTo());

    }

    private void selectProductGroups(ArrayList<String> productGroups) {


        WebElement groupsSection = driver.findElement(By.xpath(".//*[@id='tab-general']/table/tbody/tr[7]/td[./strong[.='Product Groups']]/div"));
        List<WebElement> checkboxWrappers = groupsSection.findElements(By.xpath("./table/tbody/tr"));
        checkboxWrappers.remove(0);
        for (WebElement checkboxWrapper : checkboxWrappers) {
            WebElement checkbox = checkboxWrapper.findElement(By.xpath("./td[1]/input"));
            if (productGroups.contains(checkboxWrapper.findElement(By.xpath("./td[2]")).getText()))
                selectCheckox(checkbox);
            else
                deselectCheckox(checkbox);
        }

    }

    private void selectCategories(ArrayList<String> categories) {
        WebElement categoriesSection = driver.findElement(By.xpath(".//*[@id='tab-general']/table/tbody/tr[4]/td[./strong[.='Categories']]/div"));
        List<WebElement> checkboxes = categoriesSection.findElements(By.xpath(".//input[@type='checkbox']"));
        for (WebElement checkbox : checkboxes) {
            if (categories.contains(checkbox.getAttribute("data-name")))
                selectCheckox(checkbox);
            else
                deselectCheckox(checkbox);
        }

    }


    private File getImageFile() {
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources/chair.jpg"));
        files.add(new File("src/test/resources/q6600.jpg"));
        files.add(new File("src/test/resources/stand.jpg"));
        return files.get(ThreadLocalRandom.current().nextInt(0, files.size()));
    }

}
