package de.jetwick.snacktory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.net.InternetDomainName;

import de.jetwick.snacktory.util.JsUtil;
import de.jetwick.snacktory.util.SHelper;

public class ArticleLinkExtractor {

	public static void main(String[] args) {
		String url = "http://www.bahasfilm.net/"; 
				//"https://chromplex.com/google-akan-matikan-google-now-launcher-dalam-beberapa-minggu-kedepan/"; 
				//"http://www.travelerien.com"; 
				//"http://www.foodgrapher.com"; 
				//"http://bola.inilah.com/read/detail/2267356/aguero-perpaduan-sempurna-dari-messi-dan-henry";
		//getHref(url);
		String ogType = getOgType(url);
		System.out.println(ogType);
	}
	
	public static List<String> getCategoryHref(String source) {
		List<String> result = new ArrayList<String>();
		
		Document doc = getDocument(source);

		if (doc == null)
			return result;

		String baseHost = rootHost(source);

		Elements links = doc.select("a[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			String url = link.attr("abs:href");
			String text = link.text();

			if (url != null && url.length() > 0) {
				String host = rootHost(url);
				//System.out.println(host);
				String type = getOgType(url);
				if (baseHost.equalsIgnoreCase(host))
					if(type != null && type.equalsIgnoreCase("website")) {
						print(" * a: %s <%s> <%s> (%s)", type, host, url, trim(text, 35));
						result.add(url);
					}
			}
		}
		
		return result;
	}
	
	public static Element getBestCategoryElement(Document doc) {
		// now remove the clutter
        HtmlExtractor.prepareDocument(doc);

        // init elements
        Collection<Element> nodes = HtmlExtractor.getNodes(doc);
        int maxWeight = 0;
        Element bestMatchElement = null;
        for (Element entry : nodes) {
            int currentWeight = HtmlExtractor.getCategoryWeight(entry);
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
                bestMatchElement = entry;
                
            }
        }

        if (bestMatchElement != null) {
        	return bestMatchElement;
        }
        return doc;
	}
	
	public static List<String> printCategoryHref(String source) {
		List<String> result = new ArrayList<String>();
		result.add("\n\n");
		result.add(source);
		
		Document doc = getDocument(source);
		//System.out.println(doc.html());
		if (doc == null)
			return result;

		String baseHost = rootHost(source);

		//Element bestMatch = getBestCategoryElement(doc);
		Elements links = doc.select("li a[href]");

		print("\nLinks: (%d)", links.size());
		
		HashSet<String> ss = new HashSet<>();
		int count = 0;
		for (Element link : links) {
			String url = link.attr("abs:href");
			String text = link.text();

			if (url != null && url.length() > 0) {
				if(!ss.contains(url)) {
					String host = rootHost(url);
					//System.out.println(host);
					String type = getOgType(url);
					if (baseHost.equalsIgnoreCase(host))
						if(type == null || !type.equalsIgnoreCase("article")) {
							System.out.println(count++);
							
							String data = print("<%s> <%s> (%s)", host, url, text);
							System.out.println(link.outerHtml());
							result.add(data);
								
						}
					ss.add(url);
				}
			}
		}
		
		return result;
	}
	
	
	
	public static List<String> printArticleHref(String source) {
		List<String> result = new ArrayList<String>();
		result.add("\n\n");
		result.add(source);
		
		
		Document doc = getDocument(source);
		//System.out.println(doc.html());
		if (doc == null)
			return result;

		String baseHost = rootHost(source);

		//Element bestMatch = getBestCategoryElement(doc);
		Elements links = doc.select("a[href]");

		print("\nLinks: (%d)", links.size());
		
		HashSet<String> ss = new HashSet<>();
		int count = 0;
		for (Element link : links) {
			String url = link.attr("abs:href");
			String text = link.text();

			if (url != null && url.length() > 0) {
				if(!ss.contains(url)) {
					String host = rootHost(url);
					//System.out.println(host);
					String type = getOgType(url);
					if (baseHost.equalsIgnoreCase(host))
						if(type != null && type.equalsIgnoreCase("article")) {
							System.out.println(count++);
							
							String data = print("<%s> <%s> (%s)", host, url, trim(text,20));
							System.out.println(link.outerHtml());
							result.add(data);
								
						}
					ss.add(url);
				}
			}
		}
		
		return result;
	}

	public static List<String> getHref(String pageUrl) {
		List<String> result = new ArrayList<String>();

		Document doc = getDocument(pageUrl);
		

		if (doc == null)
			return result;

		String baseHost = rootHost(pageUrl);

		Elements links = doc.select("a[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			String url = link.attr("abs:href");
			String text = link.text();

			if (url != null && url.length() > 0) {
				String host = rootHost(url);
				//System.out.println(host);
				String type = getOgType(url);
				if (baseHost.equalsIgnoreCase(host)) {
					print(" * a: %s <%s> <%s> (%s)", type, host, link.attr("abs:href"), trim(link.text(), 35));
					
				}
			}
		}

		return result;
	}

	private static String print(String msg, Object... args) {
		String data = String.format(msg, args);
		System.out.println(data);
		return data;
	}

	public static String rootHost(String url) {
		try {
			return getTopPrivateDomain(url);
		} catch (Exception e) {
			System.err.println(url);
			//e.printStackTrace();
		}
		return null;

	}

	private static String getTopPrivateDomain(String url) throws URISyntaxException {
		String host = new URI(url).getHost();
		InternetDomainName domainName = InternetDomainName.from(host);
		return domainName.topPrivateDomain().toString();
	}

	public static String getHost(String url) {
		String result = null;

		URL aURL;
		try {
			aURL = new java.net.URL(url);
			return aURL.getHost();
		} catch (Exception e) {
			System.err.println("Get host error: " + url);
		}

		return result;
	}
	
	public static String getOgType(String url) {
		return getOgType(getDocument(url));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	public static Document getDocument(String pageUrl) {
		Document doc = null;
		try {
			doc = JsUtil.getPage(pageUrl);
		} catch (Exception e) {
			System.err.println("Get document exception: " + e.getMessage() + pageUrl);
			//e.printStackTrace();
		}

		return doc;
	}
	
	public static Document getJsDocument(String pageUrl) {
		Document doc = null;
		try {
			doc = JsUtil.getPage(pageUrl);
		} catch (Exception e) {
			System.err.println("Get document exception: " + e.getMessage() + pageUrl);
			//e.printStackTrace();
		}

		return doc;
	}

	/**
	 * Tries to extract an image url from metadata if determineImageSource
	 * failed
	 *
	 * @return image url or empty str
	 */
	public static String extractLinkUrl(Document doc) {
		// use open graph tag to get image
		String imageUrl = SHelper.replaceSpaces(doc.select("head meta[property=og:image]").attr("content"));
		if (imageUrl.isEmpty()) {
			imageUrl = SHelper.replaceSpaces(doc.select("head meta[name=twitter:image]").attr("content"));
			if (imageUrl.isEmpty()) {
				// prefer link over thumbnail-meta if empty
				imageUrl = SHelper.replaceSpaces(doc.select("link[rel=image_src]").attr("href"));
				if (imageUrl.isEmpty()) {
					imageUrl = SHelper.replaceSpaces(doc.select("head meta[name=thumbnail]").attr("content"));
				}
			}
		}
		return imageUrl;
	}
	
	public static String getOgType(Document doc) {
		if(doc == null) 
			return null;
		
		String ogType = SHelper.replaceSpaces(doc.select("head meta[property=og:type]").attr("content"));
		
		return ogType;
	}

	public static double evalArticleScore(String linkUrl, String sourceUrl) {
		double result = 0;

		return result;
	}

	public static double evalCategoryScore(String linkUrl, String sourceUrl) {
		double result = 0;

		return result;
	}

}
