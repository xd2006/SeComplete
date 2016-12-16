package tests;

import app.Application;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by Alex on 17.11.2016.
 */
public class TestBase {

    public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;


    @Before
    public void start() throws IOException {

        if (tlApp.get() != null) {
            app = tlApp.get();
            return;
        }

        app =new Application();
        app = app==null ? new Application(): app;
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    app.quit();
                   app = null;
               }));

    }


    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                Set<String> handles = app.driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public void checkLog(boolean onlyErrors) throws Exception {

        List<LogEntry> logEntries;
        String message;

        if (onlyErrors) {
            logEntries = app.driver.manage().logs().get("browser").getAll().stream()
                    .filter(l -> l.getLevel().equals(Level.SEVERE) || l.getLevel().equals(Level.WARNING)).collect(Collectors.toList());
            message = "Some errors appeared in browser log. Only 1st one is displayed:";
        } else {
            logEntries = app.driver.manage().logs().get("browser").getAll();
            message = "Some messages appeared in browser log. Only 1st one is displayed:";
        }

        if (logEntries.size() > 0) {
            throw new Exception(message + logEntries.get(0).getMessage());
        }
    }

}







