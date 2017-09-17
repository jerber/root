/*
 * Parses the prices from Scraper.java
 * and prints to a aaplPrices
 * 
 * 
 * each price should have a time associated with it
 * make a class called stock-- has price and time
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class getPrices {
	
	public static String textFile;
    
    public String allPrices = "";
    

    
    
    public void getPricesOnly(String fileName, String str) {
    	textFile = fileName;
        
        BufferedReader reader = new BufferedReader(new StringReader(str));
        String s = "";
        String finalPrice = "";
        try {
            while ((s = reader.readLine()) != null) {
                
                
                // get times
                if (s.contains(":")) {
                    int ind = s.indexOf(":");
                    if (s.length() > ind + 3 && s.charAt(ind + 3) == ':' && s.contains(">") && s.contains("/")) {
                        //System.out.println(s.substring(16, s.lastIndexOf(':') + 3)); // prints time
                        // get prices
                        String price = reader.readLine();
                        if (price.contains(";")) {
                            finalPrice = price.substring(price.indexOf(';') + 1, price.lastIndexOf(';') - 5);
                            //System.out.println(finalPrice);
                            allPrices += finalPrice + "\n";
                        }
                    }
                }
                
                //get prices
                
                
            }
        } catch (IOException e) {
            System.out.println("something wrong here!");
            e.printStackTrace();
        }  
        
    }
    
    // TODO impliment
    public void getPricesAndTimes(String fileName, String str) {
    	textFile = fileName;
        
        BufferedReader reader = new BufferedReader(new StringReader(str));
        String s = "";
        String finalPrice = "";
        try {
            while ((s = reader.readLine()) != null) {
                
                
                // get times
                if (s.contains(":")) {
                    int ind = s.indexOf(":");
                    if (s.length() > ind + 3 && s.charAt(ind + 3) == ':' && s.contains(">") && s.contains("/")) {
                        System.out.println(s.substring(16, s.lastIndexOf(':') + 3)); // prints time
                        // get prices
                        String price = reader.readLine();
                        if (price.contains(";")) {
                            finalPrice = price.substring(price.indexOf(';') + 1, price.lastIndexOf(';') - 5);
                            //System.out.println(finalPrice);
                            allPrices += finalPrice + "\n";
                        }
                    }
                }
                
                //get prices
                
                
            }
        } catch (IOException e) {
            System.out.println("something wrong here!");
            e.printStackTrace();
        }  
    }
    
    public static void main(String[] args) {
        Printer pr = new Printer();
        String all = "";
        String temp = "";
        String url = "";
        String ticker = "ko";
        String outFile = "koTimes";
        int numTimes = 14;
        int numPages = 36;
        
        // LOOPfor (int i = )
        //try {
        for (int i = 1; i < numTimes; i++) {
            for (int j = numPages; j > 0; j--) {
                try {
                    
                    url = "http://www.nasdaq.com/symbol/" + ticker + "/time-sales?time=" + i + "&pageno=" + j;
                    System.out.println(url);
                    Scraper scr = new Scraper (url);
                    temp = scr.getWebsite();
                    getPrices gP = new getPrices();
                    gP.getPricesAndTimes(outFile, temp);
                    
                    //gP.getPricesOnly(ticker, temp);
                    System.out.println(gP.allPrices);
                    all += gP.allPrices;
                    
                } catch (NullPointerException e) {
                    System.out.println("something wrong here!");
                    //e.printStackTrace();
                    j++;
                    continue;
                    
                } 
                
            }
        }
        
        /*} catch (NullPointerException e) {
         System.out.println("something wrong here!");
         //e.printStackTrace();
         
         } */ 
        
        pr.print(textFile, all);
        
    }
    
}
