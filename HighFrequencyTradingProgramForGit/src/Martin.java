public class Martin {
  
  private int bit;
  private int count0;
  private int count1;
  private String bits = "";
  private int zeros;
  private int ones;
  private int firstZero;
  private int firstOne;
  public int finalCountZero;
  public int  finalCountOne;
  private int zeroTotal;
  private int oneTotal;
  static private char[] bits1;
  static private char[] bits2;
  static private int bitSize1;
  static private int bitSize2;
  static private int stringLength;
  private int timesSeen1;
  private int timesSeen2;
  private int newInd1;
  private int newInd2;
  private int disc1;
  private int disc2;
  private int totalDisc1;
  private int totalDisc2;
  
  // makes the random binary String
  public void execute() {  
    bits = "";
    newInd1 = -1;
    newInd2 = -1;
    firstZero = stringLength;
    firstOne = stringLength;
    for (int i = 0; i < stringLength; i++) {
      if (Math.random() < .5) {
        bit = 0;
      }
      else {
        bit = 1;
      }
      bits += bit;
    }
    ones = 0;
    zeros = 0;
    //iterate(bits);
    // counts the amount of 0 and 1 combos
    //System.out.println(" count 0: " + zeros + " " + "count 1: " + ones); 
  }
  
  public void iterate(String bits, char[] arr0, char[] arr1) {
	  bits1 = arr0;
	  bits2 = arr1;
    for (int i = 0; i < bits.length() - 9; i++) {
      if (bits.charAt(i) == bits1[0]) { //'1') {
        redoCheck1(bits, i); // THIS WAS CHANGED
      }
      if (bits.charAt(i) == bits2[0]) {
        redoCheck2(bits, i);
      }
    }
    
    timesSeen1 += ones;
    timesSeen2 += zeros;
    
  }
  
  public void redoCheck1(String s, int i) {
    boolean isMatch = true;
    for (int j = 1; j < bitSize1; j++) {
      if ( s.charAt(i + j) != bits1[j]) {
    	  //System.out.println("HERLKJRE" + bits.charAt(i + j));
        isMatch = false;
      }
    }
    if (isMatch == true) {
    	
    	if (i - newInd1 == 1) {
    		newInd1 = i;
    		disc1++;
    	}
    	else {
    		totalDisc1++;
    	}
    	
    	
      if (ones == 0) {
        firstOne = i;
      }
      ones++;
    }
  }
  
  
  public void redoCheck2(String s, int i) {
    boolean isMatch = true;
    for (int j = 1; j < bitSize2; j++) {
      if ( s.charAt(i + j) != bits2[j]) {
        isMatch = false;
      }
    }
    if (isMatch == true) {
    	
    	if (i - newInd2 == 1) {
    		newInd2 = i;
    		disc2++;
    	}
    	else {
    		totalDisc2++;
    	}
    	
      if (zeros == 0) {
        firstZero = i;
      }
      zeros++;
      
    }
  }
  
  public void compare() {
    zeroTotal += firstZero;
    oneTotal += firstOne;
    if (firstZero < firstOne) {
      finalCountZero++;
    }
    else if (firstZero > firstOne) {
      finalCountOne++;
    }
    // they are even
    //System.out.println("final zero: " + finalCountZero + " final one: " + finalCountOne); 
  }
  
  public static void main(String args[]) {
	  // if there is a string of 10 and 4 bits-- what is the prob those 4 bits appear in the right order?
	  // depends on the bits!
	  // odds for 0000: 
    stringLength = 100;
    bits1 = new char[] {'1','1','1','0'}; // = 1/16
    bits2 = new char[] {'0','0','0','0', '0', '0', '0'}; // 2^4 + 2^3 + 2^2 +2 = 1/30
    bitSize1 = bits1.length;
    bitSize2 = bits2.length;
    int numTimes = 10;
    Martin h = new Martin();
    for (int i = 0; i < numTimes; i++) {
      //h.execute();
      //h.iterate(h.bits);
    	analyzeTrinary aT = new analyzeTrinary("aaplPrices");
    	String fullBi = aT.makeBinary();
    	h.iterate(fullBi, bits1, bits2);
      h.compare();
    }
    System.out.println("1 appears first: " + h.finalCountOne + "; 2 appears first: " + h.finalCountZero); // should be more for less symetry
    System.out.println("1 total wait time given num times: " + h.oneTotal + "; 2 total wait time given num times: " + h.zeroTotal); // should be less for less symetry
    System.out.println("1 avg wait time: " + h.oneTotal/numTimes + "; 2 avg wait time: " + h.zeroTotal/numTimes); // should be less for less symetry
    System.out.println("1 times seen " + h.timesSeen1 + "; 2 times seen " + h.timesSeen2); // should be the same
    System.out.println("disc 1: " + h.disc1 + " disk 2: " + h.disc2);
    System.out.println("Totaldisc 1: " + h.totalDisc1 + " Totaldisc 2: " + h.totalDisc2);

  }
}
