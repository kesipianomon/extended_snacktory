package de.jetwick.snacktory.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class JsUtil {
	
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
	
	public static String getFinalURL(String url)  {
		try {
		    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

		    con.setInstanceFollowRedirects(false);
		    con.connect();
		    con.getInputStream();
	
		    if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
		        String redirectUrl = con.getHeaderField("Location");
		        return getFinalURL(redirectUrl);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return url;
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
    
    
    public static Document getPage(String url) throws Exception {
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
    
    public static Document getJsPage(String url) throws Exception {
    	return renderPage(getPage(url));
    }
    
    
    public static void main(String[] args) {
    	String url = "https://id.techinasia.com/review-corpse-party-blood-drive";
    	System.out.println(getFullHtml(url));
    }
}