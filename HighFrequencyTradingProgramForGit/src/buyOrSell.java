import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class buyOrSell {
	
	private double holdings;
	private double liquid;
	int numHoldings;
	private double total;
	private double lastPrice;
	private double totalBought;
	private double totalSold;

    private int buyCount;
    private int sellCount;
	
	
	// take in a stream of prices.  if last is a 0, buy at the current price.  If last is a 1, short the current price.
	public void decide(String fileName) {
		
		String s = "";
		double currPrice = 0;
		double prevPrice = 0;
		int count = 0;

		try {
			
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			
			while ((s = br.readLine()) != null && count < 10000000) {
				count++;
				System.out.println(numHoldings);
				if (!s.equals("")) {
					
				currPrice = Double.parseDouble(s);

				// if stock went down, buy it at curr price
				
				if (currPrice < prevPrice)  {
					//if (numHoldings == 0) {
					buy (currPrice);
					//}
					prevPrice = currPrice;
					
				}
				
				// if stock is same, don't do anything and keep prev price
				else if (currPrice == prevPrice) {
				}
				
				// if stock went up, sell all holdings
				else if (currPrice > prevPrice) {
					//if (numHoldings == 1) {
					sell (currPrice);
					//}
					prevPrice = currPrice;
					
				}
				
				
				
				
			}
				lastPrice = currPrice;
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void buy(double price) {
		
		holdings = holdings + price;
		liquid = liquid - price;
		
		numHoldings++;
		totalBought = totalBought + price;
		buyCount++;
		
	}
	
	public void sell(double price) {
		
		holdings = holdings - price;
		liquid = liquid + price;
		numHoldings --;
		
		totalSold = totalSold + price;
		sellCount++;
	}
	

	
	public static void main(String args[]) {
		int numShares = 1;
		
		buyOrSell bS = new buyOrSell();
		bS.decide("goog");
		/*
		System.out.println(bS.holdings + " " + bS.liquid);
		System.out.println(bS.lastPrice * bS.numHoldings);
		// could be negative or positive
		double outstanding = bS.lastPrice * bS.numHoldings;
		double totalMade = (bS.holdings - outstanding) * numShares;
		System.out.println(totalMade);
		*/
		
		System.out.println(bS.totalBought + " " + bS.totalSold);
		double leftOver = bS.numHoldings * bS.lastPrice;
		double totalEarned = leftOver + bS.totalSold - bS.totalBought;
		System.out.println(totalEarned);
		System.out.println("BUY: " + bS.buyCount + " SELL: " + bS.sellCount);
		// 4437 trades
		// 7 * 60 = 420 mins 420 * 60 = 25,200
		// trade every 5.7 secs
		
		
	}

}
