import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class makeText {
	
	public makeText(String ticker, String input) {
		/*

		final File parentDir = new File("aaplAllFOIDJLFK");
		parentDir.mkdir();
		final String hash = "abcd";
		final String fileName = hash + ".txt";
		final File file = new File(parentDir, fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Creates file crawl_html/abc.txt
		*/
		//List<String> lines = Arrays.asList("The first line", "The second line");
		
		
		
		
		
		/*
		Path file = Paths.get("the-file-name.txt");
		byte[] b = 
		try {
			Files.write(file, input, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND); */
		
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(ticker), "utf-8"))) {
	   writer.write(input);
	   writer.close();

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	public static void main(String[] args) {
		
		makeText mT = new makeText("koir", "hello");
		
	}


}
