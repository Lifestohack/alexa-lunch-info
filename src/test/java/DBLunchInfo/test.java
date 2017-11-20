package DBLunchInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Days;
import Models.Menus;

public class test {

	static String pdfWebsite = "https://www.ullrich-pittroff-catering.de/downloads/karten_komplett.pdf";

	public static void main(String[] args) throws IOException {
		ReadAndParsePDF readAndParsePDF = new ReadAndParsePDF();
		String menu = readAndParsePDF.getMenu(Menus.BISTRO, Days.FREITAG);
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
