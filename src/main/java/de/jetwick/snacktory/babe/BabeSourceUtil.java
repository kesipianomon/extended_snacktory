package de.jetwick.snacktory.babe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.jetwick.snacktory.ArticleLinkExtractor;
import de.jetwick.snacktory.model.JResult;
import de.jetwick.snacktory.util.JsUtil;
import de.jetwick.snacktory.util.Utils;

public class BabeSourceUtil {
	
	public static String checkOgType(String url) {
		String ogType = ArticleLinkExtractor.getOgType(url);
		if(ogType == null || ogType.isEmpty()) 
			ogType = "";
		return ogType;
	}

	public static void checkBabeService() {
		String article_path = "filtered_articles.json";
		List<JResult> data = BabeService.readBabeJSON(article_path);
		
		int count = 0;
		List<String> result = new ArrayList<String>();
		List<String> article_result = new ArrayList<String>();
		List<String> other_result = new ArrayList<String>();
		for(JResult article: data) {
//			CompareResult c = compareData(article);
//			compared.add(c);
			String ogType = checkOgType(article.getCanonicalUrl());
			System.out.println(article.getId() + "  --  " + ogType + "  --  " + article.getCanonicalUrl());
			if(!ogType.isEmpty()) {
				result.add(article.getId());
				if(ogType.equalsIgnoreCase("article"))
					article_result.add(article.getId() + "\t" + ogType + "\t" + article.getCanonicalUrl());
				else 
					other_result.add(article.getId() + "\t" + ogType + "\t" + article.getCanonicalUrl());
					
			}
			
			
//			count ++;
//			if(count > 50)
//				break;
			
		}
		String filePath = "og_article_ids.txt";
		Utils.writeLine(result, filePath);
		
		filePath = "og_articles.txt";
		Utils.writeLine(article_result, filePath);
		
		filePath = "og_others.txt";
		Utils.writeLine(other_result, filePath);
		
	}
	
	
	public static List<String> readOgIds() {
		String filePath = "og_articles.txt";
		List<String> article_ids = Utils.readLine(filePath);
		filePath = "og_others.txt";
		List<String> other_ids = Utils.readLine(filePath);
		article_ids.addAll(other_ids);
		List<String> reduced = new ArrayList<String>();
		
		for(String article : article_ids) {
			String[] splits = article.split("\t");
			reduced.add(splits[0]);
		}
		
		return reduced;
	}
	
	public static List<String> readOgUrls() {
		String filePath = "og_articles.txt";
		List<String> article_ids = Utils.readLine(filePath);
		filePath = "og_others.txt";
		List<String> other_ids = Utils.readLine(filePath);
		article_ids.addAll(other_ids);
		List<String> reduced = new ArrayList<String>();
		
		for(String article : article_ids) {
			String[] splits = article.split("\t");
			reduced.add(splits[2]);
		}
		
		return reduced;
	}
	
	public static List<String> readExceptUrls() {
		String filePath = "og_except.txt";
		List<String> article_ids = Utils.readLine(filePath);
		List<String> reduced = new ArrayList<String>();
		
		for(String article : article_ids) {
			String[] splits = article.split("\t");
			reduced.add(splits[1]);
		}
		
		return reduced;
	}
	
	public static void writeOgExcept() {
		String article_path = "filtered_articles.json";
		List<JResult> data = BabeService.readBabeJSON(article_path);
		
		List<String> ogArticle = readOgIds();
		HashSet<String> ogs = new HashSet<>();
		ogs.addAll(ogArticle);
		int count = 0;
		List<String> except = new ArrayList<>();
		for(JResult article: data) {
			String id = article.getId();
			if(!ogs.contains(id)) {
				except.add(id + "\t" + article.getCanonicalUrl());
			}
			count ++;
			//if(count > 10)
				//break;
			
		}
		String filePath = "og_except.txt";
		Utils.writeLine(except, filePath);
	}
	
	public static Set<String> getSourceList(List<String> articles) {
		Set<String> result = new HashSet<>();
		
		for(String a : articles) {
			String source = ArticleLinkExtractor.getHost(a);
			result.add(source);
		}
		
		return result;
	}
	
	public static void writeOgSource() {
		List<String> ogUrls= readOgUrls();
		
		HashSet<String> source = new HashSet<>();
		int count = 0;
		for(String url : ogUrls) {
			String s = ArticleLinkExtractor.getHost(url);
			count ++;
			if(s != null && !s.isEmpty()) {
				source.add(s);
			}
		}
		System.out.println(count);
		List<String> result = new ArrayList<>();
		result.addAll(source);
		String filePath = "og_source.txt";
		Utils.writeLine(result, filePath);
	}
	
	public static void writeOgSources() {
		List<String> og_ss = Utils.readLine("og_source.txt");
		
		HashSet<String> ss = new HashSet<>();
		for(String s: og_ss) {
			String og = ArticleLinkExtractor.getHost(JsUtil.getFinalURL(Utils.url(s)));
			ss.add(s);
		}
		
		List<String> result = new ArrayList<>();
		result.addAll(ss);
		String filePath = "og_sources.txt";
		Utils.writeLine(result, filePath);
	}
	
	public static void writeExceptSource() {
		List<String> ogUrls= readExceptUrls();
		
		HashSet<String> source = new HashSet<>();
		int count = 0;
		for(String url : ogUrls) {
			String s = ArticleLinkExtractor.getHost(url);
			count ++;
			if(s != null && !s.isEmpty()) {
				source.add(s);
			}
		}
		System.out.println(count);
		List<String> result = new ArrayList<>();
		result.addAll(source);
		String filePath = "except_source.txt";
		Utils.writeLine(result, filePath);
	}
	
	
	public static List<String> readSource(String filePath) {
		List<String> result = Utils.readLine(filePath);
		return result;
	}
	
	public static void parseSourceCategory() {
		String filePath = "og_source.txt";
		List<String> ogSource = readSource(filePath);
		List<String> out = new ArrayList<>();
		for(int i = 3 ; i < ogSource.size() ; i ++) {
			String source = ogSource.get(i);
			//source = "bogorpos.com";
			System.out.println("\n\n");
			System.out.println(source);
			List<String> catList = ArticleLinkExtractor.printCategoryHref(Utils.url(source));
			out.addAll(catList);
			if(i > 100)
				break;
		}
		
		Utils.writeLine(out, "og_source_cat.txt");
	}
	
	public static void parseSourceArticle() {
		String filePath = "og_source.txt";
		List<String> ogSource = readSource(filePath);
		List<String> out = new ArrayList<>();
		for(int i = 3 ; i < ogSource.size() ; i ++) {
			String source = ogSource.get(i);
			//source = "bogorpos.com";
			System.out.println("\n\n");
			System.out.println(source);
			List<String> catList = ArticleLinkExtractor.printArticleHref(Utils.url(source));
			out.addAll(catList);
			if(i > 10)
				break;
		}
		
		Utils.writeLine(out, "og_source_article.txt");
	}
	
	public static void parseSource() {
		String source = "http://bogorpos.com";
		try {
			String filePath = "sample.html";
			Utils.writeString(JsUtil.getPage(source).outerHtml(), filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		//checkBabeService();
		//writeOgSource();
		//writeOgSources();
		//writeExceptSource();
		//parseSourceCategory();
		parseSourceArticle();
		//parseSource();
	}
}
