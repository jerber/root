import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class updateShouldScrape {
	public String scrFullDay;
	// scrape first page, find the last updated
	// sees when file was last modified
	// uses today's date and hour to determine if it should scrape again
	public boolean shouldScrapeNow(String filename) {
		
		// if same day, look to hour
		// if diff day, look to site's data as of day
		String ticker = filename.substring(0, filename.indexOf("-"));
		Scraper sc = new Scraper("http://www.nasdaq.com/symbol/" + ticker + "/time-sales?time=1");
		String pageSource = sc.getWebsite();
		
		String temp = pageSource.substring(pageSource.indexOf("itemprop=\"dateModified\" content=\"20"));
		temp = temp.substring(0, temp.indexOf("</td>"));
		temp = temp.substring(temp.indexOf("content=\"") + 9);
		String scrYear = temp.substring(0, 4);
		String scrMonth = temp.substring(5, 7);
		String scrDay = temp.substring(8, 10);
		int scrTime = Integer.parseInt(temp.substring(11, 16).replaceAll(":", ""));
		// as long as mod is after scr, that's it
		rename r = new rename();
		String mod = "";
		String modDay = "";
		int modTime = 0;
		int modMonth = 0;
		int intScrMonth = 0;
		int modDayOf = 0;
		int modYear = 0;
		int intScrDay = Integer.parseInt(scrDay);
		scrFullDay = scrMonth + scrDay + scrYear;
		//if times are different, scrape
		if (r.fileExists(filename)) {
			mod = r.getLastMod(filename);
			modDay = mod.substring(0, mod.indexOf("-"));
			modTime = Integer.parseInt(mod.substring(mod.indexOf("-") + 1));
			modMonth = Integer.parseInt(mod.substring(0, 2));
			intScrMonth = Integer.parseInt(scrMonth);
			modDayOf = Integer.parseInt(mod.substring(2, 4));
			modYear = Integer.parseInt(mod.substring(4, 8));
			// if last mod is after scr
			if (modDay.equals(scrFullDay)) {
				// check hour
				if (modTime >= scrTime) {
					System.out.println("HERE");
					return false;
				}
				else {
					System.out.println(modTime + " " + scrTime);
					return true;
				}
			}
			// if mod day and month are more, return false-- else return true
			else if (modYear == Integer.parseInt(scrYear) && modMonth == intScrMonth) {
				if (intScrDay > modDayOf) {
					return true;
				}
				else return false;
			}
			// if mod month is more-- check
			else if (modYear == modYear && modMonth != intScrMonth) {
				if (modMonth > intScrMonth) return false;
				else return true;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		updateShouldScrape uSS = new updateShouldScrape();
		System.out.println(uSS.shouldScrapeNow("GOOG-07032017"));
		
	}
	
	
}
