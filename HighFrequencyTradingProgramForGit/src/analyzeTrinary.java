/*
 * Takes in string of Trinary and gets size, repeats, and patterns
 */
public class analyzeTrinary {
	public String fullTri = "";
	
	public analyzeTrinary(String fileName) {
		String tri = new makeTrinary().makeTrinaryFromFile(fileName);
		fullTri = tri;
	}
	
	
	public void countPieces() {
		String s = fullTri;
		int count0 = 0;
		int count1 = 0;
		int count2 = 0;
		
		char curr = ' ';
		
		for (int i = 0; i < s.length(); i++) {
			curr = s.charAt(i);
			if (curr == '0') {
				count0++;
			}
			else if (curr == '1') {
				count1++;
			}
			else if (curr == '2') {
				count2++;
			}
		}
		
		System.out.println("Count 0: " + count0 + " Count 1: " + count1 + " Count 2: " + count2);
		
	}
	
	// makes gains 1, losses 0, and deletes same
	public String makeBinary() {
		String s = fullTri;
		s = s.replace("1", "");
		s = s.replace("2", "1");
		return s;
		
	}

	
	public static void main(String[] args) {
		String tri = new makeTrinary().makeTrinaryFromFile("aaplPrices");
		analyzeTrinary aT = new analyzeTrinary("aaplPrices");
		aT.countPieces();
		String bi = aT.makeBinary();
		System.out.println(bi);
		// do iterate(bi).
		
		
	}
	
	
}
