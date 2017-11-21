package DBLunchInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;

public class UtilsForPDF {

	public static Page getAreaFromFirstPage(String path, float top, float left, float bottom, float right)
			throws IOException {
		return getAreaFromPage(path, 1, top, left, bottom, right);
	}

	public static Page getAreaFromFirstPageUrl(String pathUrl, float top, float left, float bottom, float right)
			throws IOException {
		return getAreaFromPageUrl(pathUrl, 1, top, left, bottom, right);
	}

	public static Page getAreaFromPage(String path, int page, float top, float left, float bottom, float right)
			throws IOException {
		return getPage(path, page).getArea(top, left, bottom, right);
	}

	public static Page getAreaFromPageUrl(String pathUrl, int page, float top, float left, float bottom, float right)
			throws IOException {
		return getPageFromUrl(pathUrl, page).getArea(top, left, bottom, right);
	}

	public static Page getPage(String path, int pageNumber) throws IOException {
		ObjectExtractor oe = null;
		try {
			PDDocument document = PDDocument.load(new File(path));
			oe = new ObjectExtractor(document);
			Page page = oe.extract(pageNumber);
			return page;
		} finally {
			if (oe != null)
				oe.close();
		}
	}

	public static Page getPageFromUrl(String pathUrl, int pageNumber) {

		ObjectExtractor oe = null;
		InputStream input = null;
		URL url = null;
		PDDocument document = null;
		Page page = null;
		try {
			url = new URL(pathUrl);
		} catch (MalformedURLException e1) {

			e1.printStackTrace();
		}

		try {
			input = url.openStream();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			document = PDDocument.load(input);
			oe = new ObjectExtractor(document);
			page = oe.extract(pageNumber);
		} catch (InvalidPasswordException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return page;

	}

	@SuppressWarnings("rawtypes")
	public static String[][] tableToArrayOfRows(Table table) {
		List<List<RectangularTextContainer>> tableRows = table.getRows();

		int maxColCount = 0;

		for (int i = 0; i < tableRows.size(); i++) {
			List<RectangularTextContainer> row = tableRows.get(i);
			if (maxColCount < row.size()) {
				maxColCount = row.size();
			}
		}

		// Assert.assertEquals(maxColCount, table.getColCount());

		String[][] rv = new String[tableRows.size()][maxColCount];

		for (int i = 0; i < tableRows.size(); i++) {
			List<RectangularTextContainer> row = tableRows.get(i);
			for (int j = 0; j < row.size(); j++) {
				rv[i][j] = table.getCell(i, j).getText();
			}
		}

		return rv;
	}

}
