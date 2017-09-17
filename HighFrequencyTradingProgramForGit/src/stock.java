
public class stock {

	private double price;
	private double time;
	private int tradeNumber;
	private String ticker;
	private int numShares;
	
	public stock(String ticker, double price, double time, int tradeNumber, int numShares) {
		this.ticker = ticker;
		this.price = price;
		this.time = time;
		this.tradeNumber = tradeNumber;
		this.numShares = numShares;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setTime(double time) {
		this.time =  time;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public void setTradeNumber(int tradeNumber) {
		this.tradeNumber =  tradeNumber;
	}
	
	public void setNumShares(int numShares) {
		this.numShares = numShares;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getTime() {
		return time;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public int getTradeNumber() {
		return tradeNumber;
	}
	
	public int getNumShares() {
		return numShares;
	}
	
}
