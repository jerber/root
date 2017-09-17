import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/*
 * takes in ticker. Goes in to the most recent
 * file for that ticker if one exists and determines
 * if it is up to date.  If most current doesn't exist,
 * will also tell you to scrape
 */
public class shouldScrapeNow {

	public String currFullDay = "";
	public String currTime = "";
	public String lastTradeDayFileName = "";
	// if mod date is less than today's date..
	
	public shouldScrapeNow() {
		 SimpleDateFormat time_formatter = new SimpleDateFormat("MMddyyyy-HHmm");
	        String allDay = time_formatter.format(System.currentTimeMillis());
	        currFullDay = allDay.substring(0, allDay.indexOf("-"));
	        currTime = allDay.substring(allDay.indexOf("-") + 1);
	}
	
	public boolean shouldI(String ticker) {
        SimpleDateFormat time_formatter = new SimpleDateFormat("MMddyyyy-HHmm");
        String allDay = time_formatter.format(System.currentTimeMillis());
        currFullDay = allDay.substring(0, allDay.indexOf("-"));
        currTime = allDay.substring(allDay.indexOf("-") + 1);
        
        // get most recent trading day:
        String lastTradingDay = isTradeDay();
        System.out.println("last trade day" + lastTradingDay);
        
        rename r = new rename();
        lastTradeDayFileName = ticker+'-'+lastTradingDay;
        int modTime = 0;
        
        if (isTodayATradeDay()) {
			if (Integer.parseInt(currTime) < 1030) return false;
		}
        
        // if last trading day exists, see if time > 18
        if (r.fileExists(lastTradeDayFileName)) {
        	// make sure mod time is > 18-- if not, scrape
        	String mod = r.getLastMod(lastTradeDayFileName);
        	String modDate = mod.substring(0, mod.indexOf("-"));
			modTime = Integer.parseInt(mod.substring(mod.indexOf("-") + 1));
			// Integer.parseInt(currFullDay) <= Integer.parseInt(lastTradingDay)
			
			System.out.println(modDate);
			System.out.println(lastTradingDay);
			
			
			// if scraped in the last 30 mins and trading day is still going, don't scrape  
			// TODO change to intervals of scraping
			if (Integer.parseInt(modDate) == Integer.parseInt(lastTradingDay) && Integer.parseInt(currTime) < 1630 && Integer.parseInt(currTime) - modTime <= 0) {
				System.out.println("scraped less than 2 hrs ago");
				return false;
			}

			//if (modTime >= 1615) return false;
			if (modTime < 1630 && Integer.parseInt(modDate) <= Integer.parseInt(lastTradingDay)) return true;
			//if (Integer.parseInt(modDate) > Integer.parseInt(lastTradingDay)) return false;
			else return false;
        }
        // hasn't been scraped for the last day, so scrape now
        else return true;
	}
	
	public boolean isTodayATradeDay() {
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("datesOpen"));
				while ((s = br.readLine()) != null) {
					if (s.equals(currFullDay)) {
						return true;
					}
				}} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return false;
	}
	
	public String isTradeDay() {
		// get list of file names for the ticker
		String s = "";
		String prev = "";
		try {
		BufferedReader br = new BufferedReader(new FileReader("datesOpen"));
			while ((s = br.readLine()) != null) {
				if (s.equals(currFullDay)) {
					return s;
				}
				if (Integer.parseInt(currFullDay) < Integer.parseInt(s)) { // wont work for new year
					return prev;
				}
				prev = s;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; // something went wrong if this happens
	}
	
	public static void main(String[] args) {
		
		shouldScrapeNow ss = new shouldScrapeNow();
		System.out.println(ss.shouldI("goog"));
		
	}
	
	
}
