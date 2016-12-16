package tests;

import model.Customer;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by Alex on 01.12.2016.
 */
public class RegistrationTests extends TestBase {

    @Test
    public void createUser() {

        app.getPage().startApp();
        app.getPage().click(By.xpath("//a[contains(.,'New customers click here')]"));

        String email = new java.util.Date().getTime() + "mail@test.com";
        Customer testCustomer = new Customer().withAddress1("Test address").withAddress2("Test address 2")
                .setFirstName("UserTest").withLastName("Lasttest").withCity("lepel").withCompany("Horns and Hoofs")
                .withCountry("United States").withProvince("California").withEmail(email).withPassword("superSecretPassword")
                .withPhone("+375299997788").withPostcode("22000-8766").withTaxId("12BT").withNewsletterSubscribed(true);
        populateCustomerData(testCustomer);
        app.getPage().click(By.xpath("//button[@name='create_account']"));

        By logOffLinkLocator = By.xpath("//a[contains(@href,'/logout')]");
        app.getPage().waitForElement(logOffLinkLocator);
        app.getPage().click(logOffLinkLocator);
        app.getPage().loginToUserSection(testCustomer.getEmail(), testCustomer.getPassword());
        app.getPage().click(logOffLinkLocator);

    }

    private void populateCustomerData(Customer testCustomer) {
        app.getPage().type(By.xpath("//input[@name='tax_id']"), testCustomer.getTaxId());
        app.getPage().type(By.xpath("//input[@name='company']"), testCustomer.getCompany());
        app.getPage().type(By.xpath("//input[@name='firstname']"), testCustomer.getFirstName());
        app.getPage().type(By.xpath("//input[@name='lastname']"), testCustomer.getLastName());
        app.getPage().type(By.xpath("//input[@name='address1']"), testCustomer.getAddress1());
        app.getPage().type(By.xpath("//input[@name='address2']"), testCustomer.getAddress2());
        app.getPage().type(By.xpath("//input[@name='postcode']"), testCustomer.getPostcode());
        app.getPage().type(By.xpath("//input[@name='city']"), testCustomer.getCity());
        app.driver.findElement(By.xpath("//span[@class='select2-selection__rendered']")).click();
        app.driver.findElement(By.xpath(
                String.format(".//ul[@class='select2-results__options']/li[@role='treeitem' and .='%s']",
                        testCustomer.getCountry()))).click();
        app.getPage().select(By.xpath("//select[@name='zone_code']"), testCustomer.getProvince());
        app.getPage().type(By.xpath("//input[@name='email']"), testCustomer.getEmail());
        app.getPage().type(By.xpath("//input[@name='phone']"), testCustomer.getPhone());
        app.getPage().type(By.xpath("//input[@name='password']"), testCustomer.getPassword());
        app.getPage().type(By.xpath("//input[@name='confirmed_password']"), testCustomer.getPassword());
        By newsletterCheckboxLocator = By.xpath("//input[@name='newsletter']");

        if (testCustomer.isNewsletterSubscribed()) {
            app.getPage().selectCheckox(newsletterCheckboxLocator);
        } else
            app.getPage().deselectCheckox(newsletterCheckboxLocator);
    }
}

