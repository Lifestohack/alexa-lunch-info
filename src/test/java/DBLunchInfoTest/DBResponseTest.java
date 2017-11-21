package DBLunchInfoTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;

import DBLunchInfo.DBResponse;
import Models.Menus;

public class DBResponseTest {

	private static final Logger logger = LoggerFactory.getLogger(DBResponseTest.class);
	private static final String MENU = "suppe";

	
	@Test
	public void dBResponseTest() {
		logger.info("Starting...");

		String speechText = DBResponse.getMenuItems(testHelper(), MENU);

		if (speechText == null) {
			System.out.println("Did not match");
		} else {
			System.out.println(speechText.toString());
		}

		logger.info("Ending...");
	}
	
	private Intent testHelper() {
		Slot slot = Slot.builder().withName(MENU).withValue(MENU).build();
		return Intent.builder().withName(MENU).withSlot(slot).build();
		
	}

}
