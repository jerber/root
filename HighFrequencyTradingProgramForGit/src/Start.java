/*
 *         // CONCL: with 0 lag, awayDebt works best.  But, with larger lags, the worse it does, and the better no away does.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jaunt.ResponseException;

public class Start {
    double percentGain = 0;
    public String scrapedDate = "";
    // scrapes and prints
    public String thisDay = "";
    public String thisMonth = "";
    public String thisYear = "";
    public String currentTime = "";
    public String currentFullDay = "";
    public int nomVolume = 0;
    // TODO fix this is happening when not a trading day
    public int translateHour(String nowTime) {
    	shouldScrapeNow sN = new shouldScrapeNow();
    	if (!sN.isTodayATradeDay()) return 13;
    	if (sN.isTodayATradeDay() && Integer.parseInt(nowTime) < 300) return 13;

    	int cT = Integer.parseInt(nowTime);
    	if (cT > 1630) return 13;
    	if (cT > 1600) return 12;
    	if (cT > 1530) return 11;
    	if (cT > 1500) return 10;
    	if (cT > 1430) return 9;
    	if (cT > 1400) return 8;
    	if (cT > 1330) return 7;
    	if (cT > 1300) return 6;
    	if (cT > 1230) return 5;
    	if (cT > 1200) return 4;
    	if (cT > 1130) return 3;
    	if (cT > 1100) return 2;
    	if (cT > 1030) return 1;
    	if (cT < 900) return 0;
    	return 1;
    }
    
    public void getByScrape(String ticker, String fileOut, String currTime) {
        getStockPrices gSP = new getStockPrices(ticker);
        trade t = new trade();
        int j = 0;
        String scrapedUrl = "";
        
        
        SimpleDateFormat time_formatter = new SimpleDateFormat("MMddyyyy-HHmm");
        String allDay = time_formatter.format(System.currentTimeMillis());
        currentFullDay = allDay.substring(0, allDay.indexOf("-"));
        currentTime = allDay.substring(allDay.indexOf("-") + 1);
        
        shouldScrapeNow shouldS = new shouldScrapeNow();
        String lastTradingDay = shouldS.isTradeDay();
        
        String allScraped = createString("tickerInd-" + lastTradingDay);
        //get ind for it
        int lastInd = 0;
        int iInd = translateHour(currentTime);
        String tem = "";
        if (iInd == 0) {
        	iInd = 13;
            allScraped = createString("tickerInd-" + lastTradingDay); // last trading day
            if (allScraped.contains("^" + ticker + "-")) {
            	int ind = allScraped.lastIndexOf("^" + ticker + "-");
            	tem = allScraped.substring(ind + 1);
            	tem = tem.substring(tem.indexOf("-") + 1, tem.indexOf(";"));
            	lastInd = Integer.parseInt(tem);
            }												  
        }
        
        else if (allScraped.contains("^" + ticker + "-")) {
        	int ind = allScraped.lastIndexOf("^" + ticker + "-");
        	tem = allScraped.substring(ind + 1);
        	tem = tem.substring(tem.indexOf("-") + 1, tem.indexOf(";"));
        	lastInd = Integer.parseInt(tem);
        }
        
        JScrape jS = new JScrape();
        for (int i = iInd; i > lastInd; i--) {
        	// scrape up to the half hour before
            j = 1;
            while (true) {
                String pageSource = "";
                String url = "http://www.nasdaq.com/symbol/" + ticker + "/time-sales?time=" + i + "&pageno=" + j;
                // make string to see
                // if url exists in string created from reading in, then don't download it
                // so first make the string
                // also remember to copy from old to new
                //if (allScraped.contains(url))
                // PROBLEM: you will double scrape the last page scraped
                // INSTEAD-- create url with all of the stocks last indexes
                // but if (currtime<1630) must stop at 2nd to last one to make sure no overlap
                
                //Scraper scr = new Scraper (url);
               /* while (jS.html == null) {
                    jS = new JScrape();
                    jS.JScrape(url);
                }*/

                try {
					pageSource = jS.JScrape(url);
				} catch (ResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//scr.getWebsite();
                
                System.out.println(url);
                if (pageSource.length() < 151000) {
                    break;
                }
                else {
                    gSP.getPriceTimeShares(pageSource, i, j, fileOut.substring(fileOut.indexOf("-") + 1)+"-"+currTime);
                    //scrapedUrl += url + '\n';
                }
                System.out.println(pageSource.length());
                j++;
            } 
        }
        
        // UPDATE EACH DATE BASED ON NEWEST SCRAPE
        
        System.out.println("reversing now");
        String allOfTheStocks = gSP.reverseString(gSP.bigString);
        System.out.println("done reversing");
        
        // old things in file
        /*if (allOfTheStocks!= null && allOfTheStocks.length() > 10) {
        	String lop = createString(fileOut);
        	String oldAndNew = lop + '\n' + allOfTheStocks;
        	makeText mT = new makeText(fileOut, oldAndNew);
        }*/
        if (new rename().fileExists(fileOut)) {
        	System.out.println("appending");
        	try {
				FileWriter fw = new FileWriter(fileOut, true);
				BufferedWriter buff = new BufferedWriter(fw);
				buff.write('\n' + allOfTheStocks);
				buff.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
        	makeText mT = new makeText(fileOut, allOfTheStocks);
        }
    	// make text for already scraped too
    	// delete last one
    	//make something "tickerInd-" + currFullDay
    	String oldInd = createString("tickerInd-" + lastTradingDay);
    	makeText makeT = new makeText("tickerInd-" + lastTradingDay, oldInd + "^" + ticker + "-" + Integer.toString(iInd) + ";");
    	//makeText makeT = new makeText(ticker + "urls", scrapedUrl);
    	
    	
    }
    
    public String createString(String filename) {
    	String s = "";
    	String all = "";
    	if (!new rename().fileExists(filename)) return "";
    	try {
    	BufferedReader br = new BufferedReader(new FileReader (filename));
			while ((s = br.readLine()) != null) {
				all += s + '\n';
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return all;
    }
    

    public boolean isDuplicate(String allOfTheStocks, String oldStocks) {
    	if (oldStocks.equals("")) return false;
    	if (!oldStocks.contains(",")) return false;
    	BufferedReader newS = new BufferedReader(new StringReader(allOfTheStocks));
    	BufferedReader oldS = new BufferedReader(new StringReader(oldStocks));
    	String newStr = "";
    	String oldStr = "";
    	String newT = "";
    	String oldT = "";

    	for (int i = 0; i < 30; i++) {
    		try {
				newStr = newS.readLine();
				oldStr = oldS.readLine();
				newT = newStr.substring(0, newStr.lastIndexOf(','));
				oldT = oldStr.substring(0, oldStr.lastIndexOf(','));
				if (!newT.equals(oldT)) return false;
    		
    	}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (StringIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("less than 30 trades, delete this stock");
			return false;
		}}
    	return true;
    }
    
    public String transfer(String ticker) {
    	String s = "";
    	String returnString = "";

    	try {
            BufferedReader br = new BufferedReader(new FileReader(ticker));
        	System.out.println(s + " AHHHHHHHHH");

            while ((s = br.readLine()) != null) { // TODO I think this is wrong
            	returnString += s + '\n';
            }
            return returnString;
            
    	} catch (FileNotFoundException e) {
    		return "";
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    }
    
    public void getVolume() {
    	
    }
    
    public void getByRead(String ticker, ArrayList<String> fileNames, int lag, double debtLimit, boolean shouldPrint, boolean awayDebt) {
        getStockPrices gSP = new getStockPrices(ticker);
        ArrayList<stock> stocksNew = new ArrayList<stock>();
        // read in the list of filenames
        for (int i = 0; i < fileNames.size(); i++) {
        	// start with old, then append on new
        	stocksNew.addAll(gSP.readInStocks(fileNames.get(i)));
        	//stocksNew = gSP.readInStocks(fileName);
        }
        nomVolume = stocksNew.size();
        
        trade t = new trade();
        //String allStocks = "";
        for (int k = 0; k < stocksNew.size(); k++) {
            //allStocks += gSP.stocks.get(k).getPrice() + "," + gSP.stocks.get(k).getTime() + "," + gSP.stocks.get(k).getNumShares() + "," + gSP.stocks.get(k).getTicker() + '\n';
            t.iterator(stocksNew, k, lag, debtLimit, shouldPrint, awayDebt);
        }
        percentGain = t.profit / t.maxDebt;
        
        
        
        /*
        System.out.println('\n' + "********************" + '\n');
        System.out.println(ticker);
        System.out.println("max: " + t.maxProfit + " min: " + t.minProfit+ " max IND: " + t.maxProfitInd + " min IND: " + t.minProfitInd);
        System.out.println("total buys: " + t.totalBuys + ", total sells: " + t.totalSells);
        System.out.println("total liquid money: " + t.totalMoney + "  number of stocks " + t.stocksOwn + "  value of stocks owned " );//+ gSP.stocks.get(t.currInd).getPrice()*t.stocksOwn);
        System.out.println("max debt: " + t.maxDebt);
        System.out.println('\n' + "********************" + '\n');
        System.out.println("profit by end per share: " + t.profit + ", final share price: " );//+ gSP.stocks.get(t.currInd).getPrice());
        System.out.println("Percent gain: " + percentGain );
        System.out.println("Date Scraped: " + gSP.scrapeDate); */
    }
    
    public boolean shouldScrape(String ticker, boolean everyDay, boolean everyHour) {
        try {
            BufferedReader brd = new BufferedReader(new FileReader(ticker));
            String str = "";
            String scrapedDate = "";
            // get date
            while ((str = brd.readLine()) != null && str.equals("")) {
            	scrapedDate = brd.readLine();
            	System.out.println(scrapedDate + " FD");
            }
            // hour or day
            // first day
            
        	if (!Character.isDigit(scrapedDate.charAt(0))) return true; // make sure it starts with stock price
        	
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            
    		String fullScrapedDate = scrapedDate.substring(scrapedDate.lastIndexOf(",") + 1);
            int scrapedDay = Integer.parseInt(fullScrapedDate.substring(12, 14));
            int today = d.getDate();
            int scrapedHour = Integer.parseInt(fullScrapedDate.substring(15, 17));
            int hour = d.getHours();
            String dayOfWeek = d.toString().substring(0, 3);
            String scrapedDayOfWeek = fullScrapedDate.substring(0, 3);
            int scrapedMonth = Integer.parseInt(fullScrapedDate.substring(9, 11));
            int month = cal.get(Calendar.MONTH) + 1;
            //int month = d.getMonth();
            int year = cal.get(Calendar.YEAR);
            int scrapedYear = Integer.parseInt(fullScrapedDate.substring(4, 8));
            
            System.out.println(month + "*****");
            System.out.println(scrapedDay + " " + today);
            System.out.println(scrapedHour + " " + hour);
            System.out.println(scrapedDayOfWeek + " " + dayOfWeek);
            System.out.println(scrapedMonth + " " + month);
            System.out.println(scrapedYear + " " + year);
            System.out.println(d.toString());

            System.out.println();

            // if month, day, year all are same
            // and trading day, and time is < 16, scrape
            if (month == scrapedMonth && year == scrapedYear && today == scrapedDay) {
            	if (isTradingDay(dayOfWeek, hour)) {
            		if (hour - scrapedHour > 1) return true;
            		//if (scrapedHour < 16) return true;
            	}
            	return false;
            }
            
            if (month == scrapedMonth && year == scrapedYear && today != scrapedDay) {
            	if (isTradingDay(dayOfWeek, hour)) return true;
            	else if (today - scrapedDay > 2) return true;
            	else if (scrapedDayOfWeek.equals("Fri") && scrapedHour < 16) return true;
            	return false;
            }
            
            if (year == scrapedYear && month != scrapedMonth) {
            	if (isTradingDay(scrapedDayOfWeek, scrapedHour)) return true;
            	if (month - scrapedMonth > 1) return true;
            	if (isTradingDay(dayOfWeek, hour)) return true;
            	if (scrapedDay < 28);
            	if (today > 2) return true;
            	return false;
            }
            System.out.println("MISSED SOMETHING HERE, CHECK");
            return true; // just to be safe
            
           /* if (dayOfWeek.equals("Sat") || dayOfWeek.equals("Sun")) {
                // if scraped day is friday, hour >= 16, and currDay - scraped < 3, return false
                if (((scrapedDayOfWeek.equals("Fri") && scrapedHour >= 16) || scrapedDayOfWeek.equals("Sat") || scrapedDayOfWeek.equals("Sun") && today - scrapedDay <= 2) ) { // TODO make for month switch//&& today - scrapedDay < 4) {
                	return false;
                }
            }
            
            if (today - scrapedDay >= 1) {
                if (everyDay) return true;
            }
            // now check hour // every 1 hours
            if (hour - scrapedHour >= 1 && scrapedHour < 16) {
                if (everyHour) return true;
            }
            
            return false;*/
            
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return true;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return true;
    	} catch (NullPointerException e) {
    		return true;
    	}
    }
    
    public boolean isTradingDay(String dayOfTheWeek, int hour) {
    	if (dayOfTheWeek.equals("Sat") || dayOfTheWeek.equals("Sun")) {
    		return false;
    	}
    	if (dayOfTheWeek.equals("Mon") && hour < 9) {
    		return false;
    	}
    	if (dayOfTheWeek.equals("Fri") && hour >= 16) {
    		return false;
    	}
    	return true;
    }
    
    public void startScrape(ArrayList<String> tickers) {
     String ticker = "";
     String inFile = "";
     String fileOut = "";
     shouldScrapeNow sSN = new shouldScrapeNow();
     for (int i = 0; i < tickers.size(); i++) {
            ticker = tickers.get(i);
            System.out.println("Downloading: " + ticker);
            inFile = ticker;
            fileOut = ticker; // only for scraper-- file to write to
            // make function that checks if it should scrape
            if (sSN.shouldI(ticker)) {
                getByScrape(ticker, sSN.lastTradeDayFileName, sSN.currTime); // for scraping
            }
        }
    }
    
    public ArrayList<String> startToEnd(String ticker, String start, String end) {
    	ArrayList<String> aL = new ArrayList<String>();
    	ArrayList<String> ret = new ArrayList<String>();
    	int st = Integer.parseInt(start);
    	int nd = Integer.parseInt(end);
    	// make an ArrayList<int> with all of them
    	 try {
    	        BufferedReader br = new BufferedReader(new FileReader("datesOpen"));
    	        String s = "";
    	        int line = 0;
    			while ((s = br.readLine()) != null) {
    				aL.add(s);
    			}
    	 } catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	rename r = new rename();
    	// now you have it
    	 for (int i = 0; i < aL.size(); i++) {
    		 int ind = Integer.parseInt(aL.get(i));
    		 if (ind > nd) return ret;
    		 if (Integer.parseInt(aL.get(i)) >= st) {
    			 // check if it's a file
    			 if (r.fileExists(ticker + "-" + aL.get(i))) ret.add(aL.get(i));
    		 }
    	 }

    	return ret;
    	
    }
    
    public static void main(String[] args) {
    	String tickersChoice = "tickers";

        // stocks you want to scrape:
        ArrayList<String> tickers = new ArrayList<String>();
        try {
        BufferedReader br = new BufferedReader(new FileReader(tickersChoice));
        String s = "";
			while ((s = br.readLine()) != null) {
				if (!s.equals("")) {
				s = s.replaceAll(" ", "");
				tickers.add(s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // insert current day and day before
        
        double gains = 0;
        
        Start st = new Start();
        String ticker = "";
        String fileOut = ticker;
        boolean shouldPrint = false;
        boolean awayDebt = true;
        double debtLimit = 1000000;
        int lag = 1;
        //st.startScrape(tickers); // scrapes them all  // TODO usually on
        //st.getByRead("amd", "amd", lag, debtLimit, shouldPrint, awayDebt); // for reading-- way faster, scrape first
        System.out.println(ticker + " % Gain: " + st.percentGain * 100);
        
        // go thru the dates you want-- for now just do 1
        // make the dates
        //String[] dates = {"", "-07032017"};
        for (int i = 0; i < tickers.size(); i++) {
            ArrayList<String> inFiles = new ArrayList<String>();
            ticker = tickers.get(i);
            ArrayList startEnd = st.startToEnd(ticker, "07022017", "07052017");
            for (int j = 0; j < startEnd.size(); j++) {
            	inFiles.add(ticker + "-" + startEnd.get(j));
            }
            st.getByRead(ticker, inFiles, lag, debtLimit, shouldPrint, awayDebt); // for reading-- way faster, scrape first
            System.out.println(ticker + " NomVolume: " + st.nomVolume);
            System.out.println(ticker + " % Gain: " + st.percentGain * 100);
            if (st.percentGain * 100 < 10000) gains += (st.percentGain * 100); // TODO
            //st.getByRead(ticker, inFiles, lag, debtLimit, shouldPrint, !awayDebt); // for reading-- way faster, scrape first
            //System.out.println(ticker + " % Gain: " + st.percentGain * 100);
        }
        System.out.println(gains/tickers.size());
    }
}
