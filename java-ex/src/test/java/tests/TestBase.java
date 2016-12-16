package tests;

import app.Application;
import org.junit.Before;

import java.io.IOException;

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

//    @After
//    public void quit() {
////        if (tlApp.get() != null) {
//            app.quit();
//            app=null;
////        }

//    }
}







