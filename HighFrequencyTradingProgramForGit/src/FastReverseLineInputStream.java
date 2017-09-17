import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

public class FastReverseLineInputStream {
    
    
    public String readAndPrintInReverseOrder(String inS) throws IOException {
        String all = "";
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new StringReader(inS));
            Stack<String> lines = new Stack<String>();
            String line = br.readLine();
            while(line != null) {
                lines.push(line);
                line = br.readLine();
            }
            
            while(! lines.empty()) {
            	all += lines.pop() + '\n';
                
            }
            
        } finally {
            if(br != null) {
                try {
                    br.close();   
                } catch(IOException e) {
                    // can't help it
                }
            }
        }
        return all;
    }
    
    
    
    public String reverseString(String str) {
        // make a string array
        // then go backwards
        ArrayList<String> arr = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader (str));
        String s = "";
        String finalS = "";
        try {
            while ((s = br.readLine()) != null) {
                if (s.length() > 1) {
                    arr.add(s);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // reverse ArrayList
        for (int i = arr.size() - 1; i >= 0; i--) {
            finalS += arr.get(i) + '\n';
        }
        return finalS;
    }
    
    
    
    public static void main(String[] args) {
    	
    	
    	long startTime0 = 0;
    	long endTime0 = 0;
    	long startTime1 = 0;
    	long endTime1 = 0;
    	long startTime2 = 0;
    	long endTime2 = 0;
    	
    	Start st = new Start();
    	startTime0 = System.currentTimeMillis();
    	String temp = st.createString("AAPl-07062017");
        endTime0 = System.currentTimeMillis();

    	System.out.println("done creating!");
    	

        FastReverseLineInputStream f = new FastReverseLineInputStream();
        
        try {
        	
        	
        	startTime1 = System.currentTimeMillis();
            f.readAndPrintInReverseOrder(temp);
            endTime1 = System.currentTimeMillis();
            System.out.println("done with one");
            startTime2 = System.currentTimeMillis();
            f.reverseString(temp);
            endTime2 = System.currentTimeMillis();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        long duration0 = (endTime0 - startTime0);  //divide by 1000000 to get milliseconds.
        long duration1 = (endTime1 - startTime1);  //divide by 1000000 to get milliseconds.
        long duration2 = (endTime2 - startTime2);  //divide by 1000000 to get milliseconds.

        System.out.println(duration0);
        System.out.println(duration1);
        System.out.println(duration2);


    }
    
    
}