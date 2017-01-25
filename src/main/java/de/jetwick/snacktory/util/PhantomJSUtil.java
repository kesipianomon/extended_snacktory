package de.jetwick.snacktory.util;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class PhantomJSUtil {
	
	public static String getFullHtml(String url) {
		// Init driver
		// TODO
		HtmlUnitDriver driver;
		
		
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		driver.setJavascriptEnabled(true);
		
		
		driver.get(url);
		
		
		return driver.getPageSource();
		
//		return "";
	}
	
	
	
	
    private static String filePath = "./data/temp/";

    public static Document renderPage(String url) {
        WebDriver ghostDriver = new PhantomJSDriver();
        try {
            ghostDriver.get(url);
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }
    }
    
    
    public static Document getPage(String url) throws IOException {
    	Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
			    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
			    .maxBodySize(0)
			    .timeout(50000)
			    .get();
    	
    	return doc;
    }

    public static Document renderPage(Document doc) {
        String tmpFileName = filePath + Calendar.getInstance().getTimeInMillis() + ".html";
        Utils.string2File(tmpFileName, doc.toString());
        return renderPage(tmpFileName);
    }
    
    public static Document getJsPage(String url) throws IOException {
    	return renderPage(getPage(url));
    }
    
    
    public static void main(String[] args) {
    	String url = "https://id.techinasia.com/review-corpse-party-blood-drive";
    	System.out.println(getFullHtml(url));
    }
}