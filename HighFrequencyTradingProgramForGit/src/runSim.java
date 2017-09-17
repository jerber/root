
public class runSim {

	private int count = 0;
	private int ind = -1;
	
	public void indivGo(String bits, String s) {
		count = 0;
		ind = bits.length() + 1;
		for (int i = 0; i < bits.length() + 1 - s.length(); i++) {
			if (bits.substring(i, i + s.length()).equals(s)) {
				count++;
				if (i < ind) ind = i;
			}
		}
	}
	
	public static void main(String[] args) {
		
		int sizeOfPieces = 100;
		String bits1 = "10";
		String bits2 = "0";
		String textFile = "feye";
		
		analyzeTrinary aT = new analyzeTrinary(textFile);
    	String fullBi = aT.makeBinary();
    	System.out.println(fullBi);
    	
		runSim rS = new runSim();
		
		
		String temp = fullBi;
		String newHund = "";
		int ind1 = 0;
		int ind2 = 0;
		int first1 = 0;
		int first2 = 0;
		int niether = 0;
		
	
		while (temp.length() > sizeOfPieces + 1) {

			newHund = temp.substring(0, sizeOfPieces);
        	temp = temp.substring(sizeOfPieces);
        	
        	rS.indivGo(newHund, bits1);
    		ind1 = rS.ind;
    		
    		rS.indivGo(newHund, bits2);
    		ind2 = rS.ind;
    		
    		if (ind1 < ind2) first1++;
    		else if (ind2 < ind1) first2++;
    		else niether++;
    	
		}
		System.out.println("1 came first: " + first1 + " -- 2 came first: " + first2 + " -- niether came first: " + niether);
	}
	
}
