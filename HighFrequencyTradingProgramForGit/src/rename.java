import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class rename {

	
	public String getLastMod(String filename) {
		
		File file = new File (filename);
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy-HHmm");
		String lastMod = sdf.format(file.lastModified());
		return lastMod;
		
	}
	
	public boolean fileExists(String filename) {
		
		File file = new File(filename);
		return (file.exists());
		
	}
	
	public void renameFile(String oldFile, String newFile) throws IOException {
		
		// File (or directory) with old name
		File file = new File(oldFile);

		// File (or directory) with new name
		File file2 = new File(newFile);
		
		
		if (file2.exists())
			   throw new java.io.IOException("file exists");

			// Rename file (or directory)
			boolean success = file.renameTo(file2);

			if (!success) {
			   System.out.println("failed renaming");
			}
			
	}
	
	public static void main(String[] args) {
		rename r = new rename();

		ArrayList<String> tickers = new ArrayList<String>();
        try {
        BufferedReader br = new BufferedReader(new FileReader("tickers"));
        String s = "";
			while ((s = br.readLine()) != null) {
				if (!s.equals("")) {
				s = s.replaceAll(" ", "");
				tickers.add(s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for (int i = 0; i < tickers.size(); i++) {
            if (r.fileExists(tickers.get(i)))
				try {
					r.renameFile(tickers.get(i), tickers.get(i) + "-07022017");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
		
		
		
		
		/*
		r.getLastMod("GOOG-070320171226");
		try {
			r.renameFile("GOOG-070320171226", "GOOG-07032017");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/	
	}
	
}
