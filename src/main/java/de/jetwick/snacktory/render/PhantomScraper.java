package de.jetwick.snacktory.render;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Created by aditya on 27/01/17.
 */
public class PhantomScraper implements IScraper {

    private WebDriver driver;

    public PhantomScraper() { }

    public void init() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/share/phantomjs/bin/phantomjs");

        driver = new PhantomJSDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public String scrapePage(String url) {
        driver.get(url);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) { e.printStackTrace(); }

        String pageSource = driver.getPageSource();

        return pageSource;
    }

    public void shutdown() {
        driver.close();
        driver.quit();
        driver = null;
    }
}
