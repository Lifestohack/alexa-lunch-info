package DBLunchInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import Models.Days;
import Models.Menus;

public class test {

	static String pdfWebsite = "https://www.ullrich-pittroff-catering.de/downloads/karten_komplett.pdf";

	public static void main(String[] args) throws IOException {
		ReadAndParsePDF a = new ReadAndParsePDF();
		String menu = a.getMenu(Menus.DESSERT, Days.MITTWOCH);
		System.out.println(menu);
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
