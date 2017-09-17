import java.util.ArrayList;

public class trade {
    
    public double totalMoney = 0;
    public int stocksOwn = 0;
    public double profit = 0;
    double maxProfit = -1000000;
    double maxProfitInd = 0;
    double minProfit = 1000000;
    double minProfitInd = 0;
    public int currInd = 0;
    public int totalBuys = 0;
    public int totalSells = 0;
    public double maxDebt = 0;
    public double debt;
    public double currPrice;
   
    // takes in the arraylist of stocks
    // has a buy and sell w lag time
    
    
    public void iterator(ArrayList<stock> stocks, int currInd, double lagTime, double debtLimit, boolean shouldPrint, boolean awayDebt) {
    	currPrice = stocks.get(currInd).getPrice();
    	debt = currPrice*stocksOwn;
        this.currInd = currInd;
        
        //if (currInd % 10000 == 0) System.out.println("PROGRESS: " + "currInd: " + currInd + " stocksSize: " + stocks.size() + " percent done: " + (double)currInd/(double)stocks.size());
        if (currInd != 0) {
            // buy if current price is less than previous price
            if (stocks.get(currInd).getPrice() < stocks.get(currInd - 1).getPrice()) {
                buy(stocks, currInd, lagTime, debtLimit, shouldPrint, awayDebt);
            }
            
            // sell if current price is greater than previos price
            if (stocks.get(currInd).getPrice() > stocks.get(currInd - 1).getPrice()) {
                sell(stocks, currInd, lagTime, debtLimit, shouldPrint, awayDebt);
            }
        }
        if (shouldPrint == true) {
            System.out.println("total liquid money: " + totalMoney + "  number of stocks " + stocksOwn + "  value of stocks owned " + stocks.get(currInd).getPrice()*stocksOwn);
        }
        profit = stocks.get(currInd).getPrice()*stocksOwn + (totalMoney);
        if (profit >= maxProfit) {
            maxProfit = profit;
            maxProfitInd = currInd;
        }
        if (profit <= minProfit) {
            minProfit = profit;
            minProfitInd = currInd;
        }
        if (shouldPrint == true) {
            System.out.println(profit);
        }
    }
    
    // check with lag if still available
    public void buy(ArrayList<stock> stocks, int currInd, double lagTime, double debtLimit, boolean shouldPrint, boolean awayDebt) {
        // do lag later
        int j = 0;
        while (currInd + j < stocks.size() - 1 && (stocks.get(currInd + j).getTime()) - stocks.get(currInd).getTime() < lagTime) {
            j++;
        }
        int indLagged = currInd + j;
        // buy if price is same after lag
        if (stocks.get(currInd + j).getPrice() == stocks.get(currInd).getPrice()) {
            // check to make sure you aren't in more depbt
            if ((stocks.get(currInd).getPrice())*(stocksOwn+1) < debtLimit) {
            	// see how many stocks to buy given debt
            	// if you have a lot of short stocks
            	if (stocksOwn < 0 && awayDebt == true) {
            		//buy all your shorts
            		totalMoney = totalMoney - (-1 * (stocksOwn * currPrice));
            		totalBuys = totalBuys + (-1 * stocksOwn);
            		stocksOwn = 0;
            	}
            	// just trade one
            	else {
                stocksOwn++;
                totalMoney = totalMoney - stocks.get(currInd).getPrice();
                totalBuys++;
            	}
                if (maxDebt < Math.abs(stocksOwn*currPrice)) maxDebt = Math.abs((stocks.get(currInd).getPrice())*(stocksOwn+1)); // TODO edit
            }
            else {
                if (shouldPrint == true) {
                    System.out.println("debt effected at " + currInd);
                }
            }
        }
        else {
            if (shouldPrint == true) {
                System.out.println("lag effected at " + currInd);
            }
        }
        
        
    }
    
    public void sell(ArrayList<stock> stocks, int currInd, double lagTime, double debtLimit, boolean shouldPrint, boolean awayDebt) {
        // do lag later
        int j = 0;
        while (currInd + j < stocks.size() - 1 && (stocks.get(currInd + j).getTime()) - stocks.get(currInd).getTime() < lagTime) {
            j++;
        }
        // sell if price is same after lag
        if (stocks.get(currInd + j).getPrice() == stocks.get(currInd).getPrice()) {
            // check to make sure you aren't over debt
            if ((stocks.get(currInd).getPrice())*(stocksOwn+1) > (-1*debtLimit)) {
            	
            	if (stocksOwn > 0 && awayDebt == true) {
            		totalMoney = totalMoney + (stocksOwn * currPrice);
            		totalSells = totalSells + stocksOwn;
            		stocksOwn = 0;
            	}
            	else {
                stocksOwn--;
                totalMoney = totalMoney + stocks.get(currInd).getPrice();
                totalSells++;
            	}
                if (maxDebt < Math.abs(stocksOwn*currPrice)) maxDebt = Math.abs((stocks.get(currInd).getPrice())*(stocksOwn+1)); // TODO edit
            } else {
                if (shouldPrint == true) {
                    System.out.println("debt effected at " + currInd);
                }
            }
            
        }
        else {
            if (shouldPrint == true) {
                System.out.println("lag effected at " + currInd);
            }
        }
        
    }
    
    public static void main(String[] args) {
        
    }
    
}
