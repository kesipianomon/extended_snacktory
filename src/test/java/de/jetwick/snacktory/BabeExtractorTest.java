package de.jetwick.snacktory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import de.jetwick.snacktory.util.PhantomJSUtil;

/**
 * @author Alex P, (ifesdjeen from jreadability)
 * @author Peter Karich
 */
public class BabeExtractorTest {

    ArticleTextExtractor extractor;
    Converter c;

    @Before
    public void setup() throws Exception {
        c = new Converter();
        extractor = new ArticleTextExtractor();
    }
    
    public void testUrl(String url, ArticleTextExtractor extractor) throws Exception {
    	Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
    		    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
    		    .maxBodySize(0)
    		    .timeout(60000)
    		    .get();
        JResult res = extractor.extractContent(doc);
        
        System.out.println(res.getTitle());
        System.out.println();
        System.out.println(res.getText());
        System.out.println();
        System.out.println(res.getKeywords());
        System.out.println();
        System.out.println(res.getImageUrl());
        System.out.println();
        System.out.println(res.getImages());
        System.out.println();
        System.out.println(res.getTextList());
        
        System.out.println();
        System.out.println(res.getTopNode().html());
    }
    
    public void extractTitle(String url, ArticleTextExtractor extractor) throws Exception {
    	Document doc = PhantomJSUtil.renderPage(url);
    	
        JResult res = extractor.extractContent(doc);
        
        System.out.println(res.getTitle());
    }
 
    //@Test
    public void testData1() throws Exception {
        // ? http://www.npr.org/blogs/money/2010/10/04/130329523/how-fake-money-saved-brazil
//    	String url = "http://www.rmolsumsel.com/read/2016/01/17/43807/1/Aswari-Menunggu-Sinyal-Alex-Noerdin";
//    	String url1 = "http://news.okezone.com/read/2017/01/18/65/1594733/unpar-capai-usia-62-tahun-rektor-terima-kasih-para-pendiri";
//    	String url2 = "http://www.antaranews.com/berita/529841/baasyir-tidak-hadiri-sidang-peninjauan-kembali";
        String url1 = "http://www.casaindonesia.com/article/read/1/2017/137/9-Arsitek-dan-Desainer-Indonesia-Sukses-di-Luar-Negeri";
    	testUrl(url1, extractor);
//        assertEquals("How Fake Money Saved Brazil : Planet Money : NPR", res.getTitle());
//        assertTrue(res.getText(), res.getText().startsWith("This is a story about how an economist and his buddies tricked the people of Brazil into saving the country from rampant inflation. They had a crazy, unlikely plan, and it worked. Twenty years ago, Brazil's"));
//        assertTrue(res.getText(), res.getText().endsWith("\"How Four Drinking Buddies Saved Brazil.\""));
//        assertEquals("http://media.npr.org/assets/img/2010/10/04/real_wide.jpg?t=1286218782&s=3", res.getImageUrl());
//        assertTrue(res.getKeywords().isEmpty());
    }
    
    @Test
    public void testTitle() throws Exception {
    	String url0 = "https://id.techinasia.com/review-corpse-party-blood-drive";
    	extractTitle(url0, extractor);
    }

}
