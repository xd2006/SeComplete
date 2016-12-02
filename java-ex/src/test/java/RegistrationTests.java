import model.Customer;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by Alex on 01.12.2016.
 */
public class RegistrationTests extends TestBase {

    @Test
    public void CreateUser() {

        startApp();
        click(By.xpath("//a[contains(.,'New customers click here')]"));

        String email = new java.util.Date().getTime() + "mail@test.com";
        Customer testCustomer = new Customer().withAddress1("Test address").withAddress2("Test address 2")
                .setFirstName("UserTest").withLastName("Lasttest").withCity("lepel").withCompany("Horns and Hoofs")
                .withCountry("United States").withProvince("California").withEmail(email).withPassword("superSecretPassword")
                .withPhone("+375299997788").withPostcode("22000-8766").withTaxId("12BT").withNewsletterSubscribed(true);
        populateCustomerData(testCustomer);
        click(By.xpath("//button[@name='create_account']"));

        By logOffLinkLocator = By.xpath("//a[contains(@href,'/logout')]");
        waitForElement(logOffLinkLocator);
        click(logOffLinkLocator);
        loginToUserSection(testCustomer.getEmail(), testCustomer.getPassword());
        click(logOffLinkLocator);

    }

    private void populateCustomerData(Customer testCustomer) {
        type(By.xpath("//input[@name='tax_id']"), testCustomer.getTaxId());
        type(By.xpath("//input[@name='company']"), testCustomer.getCompany());
        type(By.xpath("//input[@name='firstname']"), testCustomer.getFirstName());
        type(By.xpath("//input[@name='lastname']"), testCustomer.getLastName());
        type(By.xpath("//input[@name='address1']"), testCustomer.getAddress1());
        type(By.xpath("//input[@name='address2']"), testCustomer.getAddress2());
        type(By.xpath("//input[@name='postcode']"), testCustomer.getPostcode());
        type(By.xpath("//input[@name='city']"), testCustomer.getCity());
        driver.findElement(By.xpath("//span[@class='select2-selection__rendered']")).click();
        driver.findElement(By.xpath(
                String.format(".//ul[@class='select2-results__options']/li[@role='treeitem' and .='%s']",
                        testCustomer.getCountry()))).click();
        select(By.xpath("//select[@name='zone_code']"), testCustomer.getProvince());
        type(By.xpath("//input[@name='email']"), testCustomer.getEmail());
        type(By.xpath("//input[@name='phone']"), testCustomer.getPhone());
        type(By.xpath("//input[@name='password']"), testCustomer.getPassword());
        type(By.xpath("//input[@name='confirmed_password']"), testCustomer.getPassword());
        By newsletterCheckboxLocator = By.xpath("//input[@name='newsletter']");

        if (testCustomer.isNewsletterSubscribed()) {
            selectCheckox(newsletterCheckboxLocator);
        } else
            deselectCheckox(newsletterCheckboxLocator);
    }
}

