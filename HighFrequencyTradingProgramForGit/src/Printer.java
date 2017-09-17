/*
 * Prints to a text file
 */

import java.io.IOException;
import java.io.PrintWriter;

public class Printer {

	public void print(String fileName, String s) {
	try{
	    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
	    writer.println(s);
	    writer.close();
	} catch (IOException e) {
	   // do something
	}
	}
}
