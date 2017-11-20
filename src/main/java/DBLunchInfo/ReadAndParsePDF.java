package DBLunchInfo;

import java.io.IOException;

import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import Models.Days;
import Models.Menus;

public class ReadAndParsePDF {

	static String pdfWebsite = "https://www.ullrich-pittroff-catering.de/downloads/karten_komplett.pdf";
	private Table table = null;

	public ReadAndParsePDF() {
		initialize();
	}

	private void initialize() {
		Page page = null;
		try {
			page = UtilsForPDF.getAreaFromPageUrl(pdfWebsite, 1, 31.567f,
					8.418f, 477.721f, 821.807f);
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

	private static String beautifyText(String text) {
		String returnText = text;
		try {
			String replaceCh = text.substring(text.indexOf('(') + 1,
					text.lastIndexOf(')'));
			if (replaceCh.length() < 1) {
				returnText = text.replace('(' + replaceCh + ')', "");
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}

		return returnText;

	}
}
