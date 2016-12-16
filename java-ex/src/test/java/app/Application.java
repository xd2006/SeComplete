package app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.MainPage;
import pages.Page;
import pages.ProductPage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Created by Alex on 16.12.2016.
 */
public class Application {
    public WebDriver driver; //made public for compatibility with tests of old design
    public Properties properties; //put here made public for compatibility with tests of old design
    public WebDriverWait wait; //put here made public for compatibility with tests of old design

    private CartPage cartPage;
    private MainPage mainPage;
    private ProductPage productPage;
    private Page page;  //added for compatibility with tests of old design


    public Application() throws IOException {
        properties = new Properties();
        String target = System.getProperty("target", "local");
//        String target = System.getProperty("target","remote");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        DesiredCapabilities caps = new DesiredCapabilities();

        if ("".equals(properties.getProperty("selenium.server"))) {
            caps.setCapability(FirefoxDriver.MARIONETTE, true);
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Mozilla Firefox ESR\\firefox.exe"));
//        FirefoxBinary bin = new FirefoxBinary(new File("c:\\Program Files (x86)\\Nightly\\firefox.exe"));
//        driver = new FirefoxDriver(bin, new FirefoxProfile(),caps);

            //driver = new FirefoxDriver(caps);

//            Proxy proxy = new Proxy();
//            proxy.setHttpProxy("localhost:8888");
//            caps.setCapability("proxy", proxy);
//            driver = new ChromeDriver(caps);


            driver = new ChromeDriver();


        } else {
            caps.setBrowserName(System.getProperty("browser", CHROME));
            //caps.setPlatform(Platform.fromString(System.getProperty("platform","windows")));
            driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), caps);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    public void quit() {
        driver.quit();
    }



    public MainPage getMainPage() {
        return mainPage==null ? new MainPage(this) : mainPage;
    }

    public ProductPage getProductPage() {
        return productPage==null ? new ProductPage(this):productPage;
    }

    public CartPage getCartPage() {
        return cartPage==null ? new CartPage(this):cartPage;
    }

    public Page getPage() {
        return page==null ? new Page(this):page;
    }
}
