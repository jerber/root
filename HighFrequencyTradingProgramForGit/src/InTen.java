
public class InTen {
	private int count;
	private int count2;
	public void iterate() {
		
		//int b = Integer.parseInt(s, 2);
		//int c = Integer.parseInt("1", 2);
		
		for (int i = 0; i < 1024; i++) {
			
			String a = Integer.toBinaryString(i);
			//int sum = (b + c);
			while (a.length() != 10) {
				a = "0" + a;
			}
			if (a.contains("0000")) {
				System.out.println(a);
				count++;
			}
			
			//System.out.println(a);
		}
		
		
	}
	// get all combos of 10 long binary
	// get those that have 4 0s in it
	// count them
	
	// takes in previus string and adds 1 in binary
	/*public char[] getCombos(char[] arr) {
		
		int mostRight0 = 1000;
		int mostRight1 = 1000;
		
		for (int i = arr.length - 1; i >= 0; i--) {
			
			// if there's a 1 to left of 0, fill most right 0
			// get most right 0 position
			// most right 1 position
			// if 0 more right than 1 then make that 0 position a  1
			// if not, go on
			
			if (arr[i] == '0') {
				if (mostRight0 == 1000) mostRight0 = i;
			}
			if (arr[i] == '1') {
				if (mostRight1 == 1000) mostRight1 = i;
			}
		}
			// if 0 is to right of 1
			if (mostRight0 > mostRight1) {
				arr[mostRight0] = '1';
				return arr;
			}
			
			// all are 1s
			else if () {
				
			}
			else if (mostRight1 > mostRight0) {
				
			}
		
		return new char[10]; // TODO blank
		
	}
	*/
	//
	// print all in 3 digits
	// 000
	// 001
	// 010
	// 011
	// 100
	// 101
	// 110
	// 111
	
	// if there is a 1 to left of 0 , fill the most right 0
	// else if there's all 1s, youre done
	// else if there are no 0s to the left of a 1, make digit after the 1 a 1 and everything else a 0
	
	
	public static void main(String[] args) {

		InTen it = new InTen();
		it.iterate();
		System.out.println(it.count);
		
	}

}
