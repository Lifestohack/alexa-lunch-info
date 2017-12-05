package DBLunchInfoTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;

import DBLunchInfo.DBResponse;

public class DBResponseTest {

	private static final Logger logger = LoggerFactory.getLogger(DBResponseTest.class);
	private static final String MENU = "menü eins";
	private static final String PREIS = "kostet";

	@Test
	public void dBResponseTest() {
		logger.info("Starting...");

		String speechText = DBResponse.getMenuResponseFromIntent(createIntent(), MENU, PREIS, false);

		if (speechText == null) {
			System.out.println("Did not match");
		} else {
			System.out.println(speechText.toString());
		}

		logger.info("Ending...");
	}

	private Intent createIntent() {
		Map<String, Slot> slots = new HashMap<>();
		Slot slot1 = Slot.builder().withName("MENU_ITEMS").withValue("menü eins").build();
		slots.put("menü eins", slot1);
		Slot slot2 = Slot.builder().withName("MENU_ITEMS").withValue("suppe").build();
		slots.put("suppe", slot2);
		Slot slot3 = Slot.builder().withName("MENU_ITEMS").withValue("aktion").build();
		slots.put("aktion", slot3);
		
		Map<String, Slot> slots1 = new HashMap<>();
		Slot slot4 = Slot.builder().withName("PRICE_ITEMS").withValue("kostet").build();
		slots1.put("kostet", slot4);
		Slot slot5 = Slot.builder().withName("PRICE_ITEMS").withValue("pries").build();
		slots1.put("pries", slot5);
		Slot slot6 = Slot.builder().withName("PRICE_ITEMS").withValue("tuere").build();
		slots1.put("tuere", slot6);
		Intent intent = Intent.builder().withName("LUNCHINTENT").withSlots(slots).withSlots(slots1).build();
	
//		return null;
		return intent;

	}

}
