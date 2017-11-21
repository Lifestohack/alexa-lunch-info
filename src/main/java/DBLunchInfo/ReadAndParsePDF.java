
package DBLunchInfo;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import Models.Days;
import Models.Menus;

public class ReadAndParsePDF {

	static String pdfWebsite = "https://www.ullrich-pittroff-catering.de/downloads/karten_komplett.pdf";

	private Table table = null;

	
	private static ReadAndParsePDF instance = null;
	
	public static ReadAndParsePDF getInstance(){
		if(instance == null){
			instance = new ReadAndParsePDF();
		}
		return instance;
	}
	
	public ReadAndParsePDF() {
		initialize();
	}

	private void initialize() {
		Page page = null;
		try {
			page = UtilsForPDF.getAreaFromPageUrl(pdfWebsite, 1, 31.567f, 8.418f, 477.721f, 821.807f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
		table = sea.extract(page).get(0);
	}

	public String getMenu(Menus menus, Days days) {
		if (table == null) {
			initialize();
		}
		int row = Menus.valueOf(menus.toString()).ordinal();
		int col = Days.valueOf(days.toString()).ordinal() + 1;
		return beautifyText(UtilsForPDF.tableToArrayOfRows(table)[row][col]);

	}

	private String beautifyText(String text) {
		String returnText = text;
		try {
			String replaceCh = text.substring(text.indexOf('(') + 1, text.lastIndexOf(')'));
			if (replaceCh.length() <= 1) {
				returnText = text.replace('(' + replaceCh + ')', "");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		Matcher matcher = null;
		matcher = ignoreWithRegex(returnText, "[A-Za-z0-9]{1},");

		returnText = replaceTextEmpty(matcher, returnText);
		matcher = ignoreWithRegex(returnText, "[A-Z]{2}");
		returnText = replaceCapitalText(matcher, returnText);
		returnText = replacePriceEmpty(returnText);
		return returnText;

	}

	private Matcher ignoreWithRegex(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(text);
	}

	private String replaceTextEmpty(Matcher matcher, String text) {
		while (matcher.find()) {
			text = text.replace(matcher.group(0), "");
		}
		return text;
	}
	
	private String replaceCapitalText(Matcher matcher, String text) {
		String match = null;
		while (matcher.find()) {
			match = matcher.group(0);
			text = text.replace(match.substring(0,1), "");
		}
		return text;
	}
	
	private String replacePriceEmpty(String text) {
		text = text.replace(text.substring(text.indexOf("€")), "");
		return text;
	}
}
