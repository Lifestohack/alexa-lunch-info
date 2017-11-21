package DBLunchInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;

import Helper.DateConverter;
import Models.Menus;

public class DBResponse {

	private static final Logger logger = LoggerFactory.getLogger(DBResponse.class);

	public static String getMenuItems(Intent intent, String MENUS_ITEMS) {
		logger.info("Slot that was used: " + MENUS_ITEMS);
		Menus menu = getMenuType(intent, MENUS_ITEMS);
		
		String speechText = null;
		if (menu == null) {
			speechText = "Sorry, Ich habe nicht verstanden.";
		} else {
			logger.info("Running GetMenuResponse Funtion.");
			try {
				speechText = ReadAndParsePDF.getInstance().getMenu(menu, DateConverter.getToday());
				speechText = "Für " + menu.getValue() + " gibt es heute: " + speechText;
			} catch (Exception e) {
				logger.error(e.toString());
				speechText = "Sorry, No food for you.";
				e.printStackTrace();
			}
			logger.info("Returing GetMenuResponse Funtion.");
		}

		return speechText;
	}

	private static Menus getMenuType(Intent intent, String MENUS_ITEMS) {
		Slot itemSlot = intent.getSlot(MENUS_ITEMS);
		Menus menu = null;
		if (itemSlot != null && itemSlot.getValue() != null) {
			String itemName = itemSlot.getValue();
			menu = getMenuNameFromSlot(itemName);

		}
		logger.info("Menu that was requested was "+ menu.getValue());
		return menu;
	}

	public static Menus getMenuNameFromSlot(String itemName) {
		Menus menu = null;
		for (Menus menuItem : Menus.values()) {
			if (itemName.equals(menuItem.getValue())) {
				menu = menuItem;
				break;
			}
		}
		return menu;

	}
}
