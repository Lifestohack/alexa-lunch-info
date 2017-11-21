package DBLunchInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Models.Days;
import Models.Menus;

public class test {

	static String pdfWebsite = "https://www.ullrich-pittroff-catering.de/downloads/karten_komplett.pdf";

	private static final Logger logger = LoggerFactory
			.getLogger(test.class);
	
	public static void main(String[] args) throws IOException {
		logger.info("There was a Error.");
		String menu = null;
		try{
			menu =ReadAndParsePDF.getInstance().getMenu(Menus.BISTRO, Days.FREITAG);
		}catch(Exception e){
			logger.error(e.toString());
			menu = "Sorry, No food for you.";
		}
		
		
		
		System.out.println("***********");
		System.out.println(menu);
		System.out.println("***********");
		
		
		
	
		
	}

	public static void saveFile(String text) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("pdftoText.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		writer.print(text);
		writer.close();
	}
}
