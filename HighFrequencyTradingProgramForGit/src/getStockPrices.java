import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class getStockPrices {
	public String bigString = "";
	public String scrapeDate = "";
	private static String ticker;
	public String scrDate = "";
	public String scrDay = "";
	public String scrMonth = "";
	public String scrYear = "";
	public String scrHour = "";
	//private TreeMap<Double, stock> stocksTree = new TreeMap<Double, stock>();
	public ArrayList<stock> stocks = new ArrayList<stock>();
	
	public getStockPrices(String ticker) {
		this.ticker = ticker;
	}
	
	public ArrayList<stock> readInStocks(String fileName) {
		String s = "";
		double price = 0;
		String ticker = "";
		double time = 0;
		int numShares = 0;
		int tradeNumber = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			scrapeDate = reader.readLine();
			while ((s = reader.readLine()) != null) {
				if (!s.equals("")) {
					price = Double.parseDouble(s.substring(0, s.indexOf(",")));
					s = s.substring(s.indexOf(",") + 1);
					time = Double.parseDouble(s.substring(0, s.indexOf(",")));
					s = s.substring(s.indexOf(",") + 1);
					numShares = Integer.parseInt(s.substring(0, s.indexOf(",")));
					s = s.substring(s.indexOf(",") + 1);
					ticker = s;
					tradeNumber++;
					stocks.add(new stock(ticker, price, time, tradeNumber, numShares));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}
	
	
	// read from top to bottom then arrange in time order
	
	public String changeMonth(String m) {
		if (m.equals("Jan")) return "01";
		if (m.equals("Feb")) return "02";
		if (m.equals("Mar")) return "03";
		if (m.equals("Apr")) return "04";
		if (m.equals("May")) return "05";
		if (m.equals("Jun")) return "06";
		if (m.equals("Jul")) return "07";
		if (m.equals("Aug")) return "08";
		if (m.equals("Sep")) return "09";
		if (m.equals("Oct")) return "10";
		if (m.equals("Nov")) return "11";
		if (m.equals("Dec")) return "12";
		return "00";
	}
	
	public String getPriceTimeShares(String pageSource, int indI, int indJ, String scrapeDated) {
		String useThis = scrapeDated;
		double price = 0;
		double time = 0;
		int tradeNumber = 0;
		int numShares = 0;
		String timeS = "";
		boolean isOne = false;
		
		
		// cut string to only parts you care about
		
		// make stock objects
		
		String date = pageSource.substring(pageSource.indexOf("data as of") + 41, pageSource.indexOf("data as of") + 62);
		scrDate = date;
		scrDay = scrDate.substring(scrDate.indexOf('.') + 1, scrDate.indexOf(',')).replace(" ", "");
		if (scrDay.length() < 2) scrDay = "0" + scrDay;
		scrMonth = changeMonth(scrDate.substring(0, 3));
		scrYear = scrDate.substring(scrDate.indexOf(',') + 2, scrDate.indexOf(',') + 6);
		scrHour = scrDate.substring(scrDate.indexOf(',') + 7, scrDate.indexOf(',') + 12).replace(":", "").replace(" ", "");
		if (scrHour.length() < 4) scrHour = "0" + scrHour;
		
		String begIndS = "<th>NLS Time (ET)</th>";
		int begInd = pageSource.indexOf(begIndS);
		String endIndS = "<div id=\"quotes_content_left__panelLowerNav\">";
		int endInd = pageSource.indexOf(endIndS);
		
		pageSource = pageSource.substring(begInd, endInd);

		
		BufferedReader reader = new BufferedReader(new StringReader(pageSource));
        String line = "";
        
        try {
        	
			while ((line = reader.readLine()) != null) {
				
				if (line.contains(":")) {
					//System.out.println(line); // should be time
					//System.out.println(line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>")));
					timeS = line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>"));
					timeS = timeS.replaceAll(":", "").replaceAll(" ", "");
					time = Integer.parseInt(timeS);
					
				}
				if (line.contains("$&nbsp;")) {
					//System.out.println(line); // should be price
					
					//System.out.println(line.substring(line.indexOf("<td>$&nbsp;") + 11, line.indexOf("<td>$&nbsp;") + 16));
					price = Double.parseDouble(line.substring(line.indexOf("<td>$&nbsp;") + 11, line.lastIndexOf("&nbsp")).replace(" ", "").replace(",", ""));
					//System.out.println(price);
					
					line = reader.readLine();
					
					if (line.contains("<td>")) {
						//System.out.println(line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>")));
						numShares = Integer.parseInt(line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>")).replace(",", ""));
						//System.out.println(numShares);
						//System.out.println(line); // should be number of shares
						//stocks.add(new stock(ticker, price, time, tradeNumber, numShares));
						bigString += price + "," + time + "," + numShares + "," + ticker + "," + useThis + "," + indI + "," + indJ + '\n';
					}
					
				}
				
				// make stock object
				//public stock(String ticker, double price, double time, int tradeNumber, int numShares) {

				//stocks.add(new stock(ticker, price, time, tradeNumber, numShares));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bigString;
	}
	
	// makes list, then iterates thru chopping it off
	public void newReverseString(String str) {
		
	}
	
	public ArrayList<stock> reverseList(ArrayList<stock> list) {
		ArrayList<stock> newList = new ArrayList<stock>();
		int ind = list.size();
		for (int i = ind - 1; i >= 0; i--) {
			newList.add(list.get(i));
		}
		return newList;
	}
	
	public String reverseString(String str) {
		// make a string array
		// then go backwards
		ArrayList<String> arr = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new StringReader (str));
		String s = "";
		String finalS = "";
		try {
			while ((s = br.readLine()) != null) {
				if (s.length() > 1) {
					arr.add(s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// reverse ArrayList
		for (int i = arr.size() - 1; i >= 0; i--) {
			if (i % 10000 == 0) System.out.println("reversing on " + i);
			finalS += arr.get(i) + '\n';
		}
		return finalS;
	}
	
	/*public static void main(String[] args) {
		
		String tester = "fskhjfd" + '\n' + "ffffff" + '\n' + "zzzzzz";
		getStockPrices g = new getStockPrices("kl");
		System.out.println(g.reverseString(tester));
		/*
		// iterate thru== stop when getting nonsence back
		// make sure to reverse after all appended
		ticker = "amd";
		int lag = 10;
		double debtLimit = 100000;
		getStockPrices gSP = new getStockPrices(ticker);
		trade t = new trade();
		int j = 0;
		
		for (int i = 13; i > 0; i--) { // should be i>0
			j = 1;
			while (true) {
			//for (int j = 1; j < 50; j++) { // shoudl be 50
				String pageSource = "";
				String url = "http://www.nasdaq.com/symbol/" + ticker + "/time-sales?time=" + i + "&pageno=" + j;
				Scraper scr = new Scraper (url);
				while (scr.getWebsite() == null) {
					scr = new Scraper (url);
				}
				pageSource = scr.getWebsite();
				System.out.println(url);
				if (pageSource.length() < 135000) {
					//i--;
					//j = 0;
					break;
				}
				else {
					gSP.getPriceTimeShares(pageSource);
				}
				//System.out.println(url);
				System.out.println(pageSource.length());
				j++;
			}
			
		}
		
		gSP.stocks = gSP.reverseList(gSP.stocks);
		String allStocks = "";
		for (int k = 0; k < gSP.stocks.size(); k++) {
        	//System.out.println(gSP.stocks.size());
			allStocks += gSP.stocks.get(k).getPrice() + "," + gSP.stocks.get(k).getTime() + "," + gSP.stocks.get(k).getNumShares() + "," + gSP.stocks.get(k).getTicker() + '\n';
        	//System.out.println(gSP.stocks.get(k).getPrice() + " " + gSP.stocks.get(k).getTime());
			t.iterator(gSP.stocks, k, lag, debtLimit, false);
        }
		//Printer printer = new Printer();
		//printer.print("amd", allStocks);
		System.out.println("max: " + t.maxProfit + " min: " + t.minProfit);
		System.out.println("max IND: " + t.maxProfitInd + " min IND: " + t.minProfitInd);

	}*/
	
}
