package de.jetwick.snacktory.render;

/**
 * Created by aditya on 27/01/17.
 */
public class JsRender {
	
	

    public static void main(String[] args) {
    	
    	String url = "http://www.bongda.com.vn/goc-arsenal-lucas-perez-xung-dang-da-chinh-d378389.html"; 
    			//"https://id.techinasia.com/review-corpse-party-blood-drive";
    	renderPage(url);
    }
    
    public static void renderPage(String url) {
        IScraper scraper = new PhantomScraper();
        scraper.init();
        long startTime = System.currentTimeMillis();
        System.out.println(scraper.scrapePage(url));
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println((double)duration * 1.0 / 1000);
        System.out.println("========================================================================================");

        //System.out.println(scraper.scrapePage("https://id.techinasia.com/review-corpse-party-blood-drive"));

        scraper.shutdown();
    }
}
