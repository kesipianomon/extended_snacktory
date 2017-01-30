package de.jetwick.snacktory.render;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by aditya on 27/01/17.
 */
public class Scraper implements IScraper {

    private String _url;
//    private WebDriver driver;
    private HtmlUnitDriver driver;

    public Scraper() {
        this(null);
    }

    public Scraper(String _url) {
        this._url = _url;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        this._url = url;
    }

    public void init() {
        if (driver == null) {
//            System.setProperty("webdriver.chrome.driver", "/home/aditya/tmp/test-scraping/driver/chromedriver");
//            driver = new ChromeDriver();
//            driver = new HtmlUnitDriver(true);
            driver = new HtmlUnitDriver(BrowserVersion.CHROME);
            driver.setJavascriptEnabled(true);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    public String scrapePage() {
        return scrapePage(this._url);
    }

    public String scrapePage(String url) {
        init();

        driver.get(url);

        String pageSource = driver.getPageSource();



        return pageSource;
    }

    public void shutdown() {
        driver.quit();
        driver.close();
        driver = null;
    }
}
