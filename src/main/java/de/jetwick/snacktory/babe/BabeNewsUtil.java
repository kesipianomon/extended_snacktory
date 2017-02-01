package de.jetwick.snacktory.babe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.jetwick.snacktory.ArticleTextExtractor;
import de.jetwick.snacktory.JResult;
import de.jetwick.snacktory.util.DistanceUtil;
import de.jetwick.snacktory.util.HttpUtil;
import de.jetwick.snacktory.util.PhantomJSUtil;
import de.jetwick.snacktory.util.Utils;
import info.debatty.java.stringsimilarity.Levenshtein;

public class BabeNewsUtil {
	
	public static void extractData() {
		String url = "http://harianbhirawa.co.id/2016/01/bfi-targetkan-penyaluran-kredit-tumbuh-10/";
		JResult res = scrapData(url);
		System.out.println(res.getTopNode());
	}

	
	public static JResult scrapData(String url) {
		ArticleTextExtractor extractor = new ArticleTextExtractor();
		JResult res = null;
		try {
			Document doc = PhantomJSUtil.getPage(url);
			
			res = extractor.extractContent(doc);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return res;
	}
	
	public static CompareResult compareData(JResult article) {
		if(article == null || article.getCanonicalUrl() == null)
			return new CompareResult(article);
		
		JResult scrapped = scrapData(article.getCanonicalUrl());
		
		if(scrapped == null) {
			System.out.println("!!!!!! - Wrong crawled");
			return new CompareResult(article);
		}
		
		return comparedArticle(article, scrapped);
		
	}
	
	public static CompareResult comparedArticle(JResult babe, JResult scrap) {
		CompareResult result = new CompareResult(babe);
		
		Levenshtein l = new Levenshtein();
		
		System.out.println(babe.getCanonicalUrl());
		System.out.println(scrap.getCanonicalUrl());
		result.lib_url = scrap.getCanonicalUrl();
		
		
		System.out.println(DistanceUtil.longestSubstr(babe.getTitle(), scrap.getTitle()) 
				+ " -- " + l.distance(babe.getTitle(), scrap.getTitle()) 
				+ " -- " + babe.getTitle().length() 
				+ " -- " + scrap.getTitle().length());
		result.lib_title = scrap.getTitle();
		result.title_distance = (int)l.distance(babe.getTitle(), scrap.getTitle());
		result.lib_title_len = scrap.getTitle().length();
		
		System.out.println(babe.getTitle() + "\n" + scrap.getTitle());
		
		System.out.println(DistanceUtil.longestSubstr(babe.getText(), scrap.getText()) 
				+ " -- " + l.distance(babe.getText(), scrap.getText()) 
				+ " -- " + babe.getText().length() 
				+ " -- " + scrap.getText().length());
		System.out.println(babe.getText());
		System.out.println(scrap.getText());
		result.content_distance = (int)l.distance(babe.getText(), scrap.getText());
		result.lib_content_len = scrap.getText().length();
		
		//System.out.println(l.distance(babe.getTopNode().html(), scrap.getTopNode().html()) + " -- " + babe.getTopNode().html().length() + " -- " + scrap.getTopNode().html().length());
		
		System.out.println("\n\n\n---------\n\n\n");
		
		return result;
	}
	
	
	public static void checkBabeData() {
		String data = BabeService.getBabeData();
		
		//System.out.println(data);
		
		List<JResult> result = Utils.json2Result(data); 
		//System.out.println(result);
		
		for(JResult article: result) {
			compareData(article);
		}
	}
	
	public static void checkBabeService() {
		String article_path = "filtered_articles.json";
		List<JResult> data = BabeService.readBabeJSON(article_path);
		
		int count = 0;
		List<CompareResult> compared = new ArrayList<>();
		for(JResult article: data) {
			CompareResult c = compareData(article);
			compared.add(c);
			count ++;
			if(count > 10)
				break;
			
		}
		String filePath = "31_2_2016_compared_result.csv";
		Utils.writeCSV(compared, filePath);
		
	}
	
	public static void checkBabeService(String article_path, String compared_path) {
		//String article_path = "/home/mainspring/tutorial/learn/content-extractor/data/articles_json.txt";
		List<JResult> data = BabeService.readBabeJSON(article_path);
		
		int count = 0;
		List<CompareResult> compared = new ArrayList<>();
		for(JResult article: data) {
			CompareResult c = compareData(article);
			compared.add(c);
			count ++;
			
			
		}
		//String filePath = "/home/mainspring/tutorial/learn/content-extractor/data/compared_result.csv";
		Utils.writeCSV(compared, compared_path);
		
	}
	
	
	
	public static void main(String[] args) {
		
		for(String arg : args) {
			System.out.println(arg);
		}
		if(args.length >= 2) {
			String article_path =args[0];
			String compared_path = args[1];
			checkBabeService(article_path, compared_path);
		}
		
//		checkBabeService();
		
		//checkBabeData();
		//extractData();
	}
	

}
