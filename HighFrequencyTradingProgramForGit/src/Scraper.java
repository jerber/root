/*
 * receives website and produces the source code as a string
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	private String website;
	private String hiLows;
	private Document unchangedDoc;
	
	public Scraper(String html) {
            Document doc = null;
			try {

			    System.setProperty("http.proxyHost", "192.168.5.1");
			    System.setProperty("http.proxyPort", "1080");
				doc = Jsoup.connect(html)
					.ignoreContentType(true)
				    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				    .referrer("http://www.google.com") 
				    //.timeout(1000*5) //it's in milliseconds, so this means 5 seconds.              
				    .get();

			unchangedDoc = doc;

			website = doc.toString();

			hiLows = doc.getElementsByTag("strong").toString();
			
			//parseMore(hiLows);
			} catch (IOException e) {
				System.out.println("there has been an error");
				System.out.println("with: " + html);
				website = null;
			}
			
	}
	

	public String getWebsite() {
		if (website == null) return null;
		return website;
	}
	
	
	public Document getUnchangedDoc() {
		if (hiLows == null) return null;
		return unchangedDoc;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 30; i++) {
			System.out.println("downloading..." + i);
			Scraper ss = new Scraper ("http://www.nasdaq.com/symbol/aapl/time-sales?time=1&pageno="+i);
		}
		//System.out.println(ss.website);
	}
	
}
