package DBLunchInfoTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DBLunchInfo.ReadAndParsePDF;
import Helper.DateConverter;
import Models.Days;
import Models.Menus;
import Models.ResponseType;

public class DBResponsePDFTest {

	private static final Logger logger = LoggerFactory.getLogger(DBResponsePDFTest.class);

	@Test
	public void dBResponsePDFTest() {
		String menu = null;
		try {
			menu = ReadAndParsePDF.getInstance().getMenuResponse(Menus.BISTRO, DateConverter.getToday(), false);
		} catch (Exception e) {
			logger.error(e.toString());
			menu = "Sorry, No food for you.";
		}

		System.out.println("***********");
		System.out.println(menu);
		System.out.println("***********");
	}

}
