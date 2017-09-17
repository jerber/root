import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Takes in apple prices and turns it into trinary--
 * 0- down, 1- stay, 2- up
 * 
 */
public class makeTrinary {

	
	public String makeTrinaryFromFile(String fileName) {
		
		// make string reader
		// iterate thru
		String s = "";
		double currPrice = 0;
		double prevPrice = 0;
		String binaryString = "";
		try {
			
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			while ((s = br.readLine()) != null) {
				if (!s.equals("")) {
				currPrice = Double.parseDouble(s);
				if (currPrice < prevPrice) {
					binaryString += "0";
				}
				else if (currPrice == prevPrice) {
					binaryString += "1";
				}
				else if (currPrice > prevPrice) {
					binaryString += "2";
				}
				
				prevPrice = currPrice;
				
			}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// delete first trinary since was first
		return binaryString.substring(1);
	}
	
	public static void main(String[] args) {
		makeTrinary mT = new makeTrinary();
		String all = mT.makeTrinaryFromFile("aaplPrices");
		System.out.println(all);
	}
	
}
