import com.jaunt.*;

public class JScrape {
	public String html = "";

	public String JScrape(String url) throws ResponseException {
			  UserAgent userAgent = new UserAgent();  
			  userAgent.settings.autoSaveAsHTML = true;//create new userAgent (headless browser).
			  userAgent.visit(url); 

			  String temp = userAgent.doc.innerHTML(); 
			  html = temp;
			  return temp;              //print the document as HTML
	}
	
	public static void main(String[] args){
		JScrape j = new JScrape();
		try {
			System.out.println(j.JScrape("http://www.nasdaq.com/g00/symbol/nke/time-sales?time=1&pageno=3"));
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
