package DBLunchInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;

import Helper.DateConverter;
import Models.Days;
import Models.Menus;

public class DBResponse {

	private static final Logger logger = LoggerFactory.getLogger(DBResponse.class);

	public static String getMenuResponseFromIntent(Intent intent, String MENUS_ITEMS, String PRICE_ITEMS,
			boolean priceIntent) {
		logger.info("Menu Slot that was used: " + MENUS_ITEMS);
		logger.info("Price Slot that was used: " + PRICE_ITEMS);
		Menus menu = getMenuType(intent, MENUS_ITEMS);

		String speechText = null;
		if (menu == null) {
			speechText = "Entschuldigung! Ich habe menü nicht verstanden. Bitte versuch mal wieder.";
		} else {
			logger.info("Running GetMenuResponse Funtion.");
			try {

				if (isSlotUsed(intent, MENUS_ITEMS) == true && isSlotUsed(intent, PRICE_ITEMS) == true) {
					if (priceIntent == true) {
						speechText = responsePrice(menu, DateConverter.getToday());
					} else if (priceIntent == false) {
						speechText = responseMenuAndPrice(menu, DateConverter.getToday());
					} else {
						speechText = "Entschuldigung! etwas ist schief gelaufen. Bitte versuch mal wieder.";
					}

				} else if (isSlotUsed(intent, MENUS_ITEMS) == true && isSlotUsed(intent, PRICE_ITEMS) == false) {
					speechText = responseMenu(menu, DateConverter.getToday());
				}

			} catch (Exception e) {
				logger.error(e.toString());
				speechText = "Entschuldigung! etwas ist schief gelaufen. Bitte versuch mal wieder.";
				e.printStackTrace();
			}
			logger.info("Returing GetMenuResponse Funtion.");
		}

		return speechText;
	}

	private static String responseMenuAndPrice(Menus menu, Days day) {

		String menuItem = getResponseFromPDF(menu, day, false);
		String menuPrice = getResponseFromPDF(menu, day, true);

		return menuItem + " und " + menuPrice;
	}

	private static String responseMenu(Menus menu, Days day) {
		return getResponseFromPDF(menu, day, false);
	}

	private static String responsePrice(Menus menu, Days day) {
		return getResponseFromPDF(menu, day, true);
	}

	private static String getResponseFromPDF(Menus menu, Days day, boolean price) {
		return ReadAndParsePDF.getInstance().getMenuResponse(menu, DateConverter.getToday(), price);
	}

	private static Menus getMenuType(Intent intent, String MENUS_ITEMS) {
		Slot itemSlot = intent.getSlot(MENUS_ITEMS);
		Menus menu = null;
		if (itemSlot != null && itemSlot.getValue() != null) {
			String itemName = itemSlot.getValue();
			menu = getMenuNameFromSlot(itemName);
			logger.info("Menu that was requested was " + menu.getValue());
		}
		return menu;
	}

	public static boolean isSlotUsed(Intent intent, String slot) {
		Slot itemSlot = intent.getSlot(slot);
		boolean isSlotUsed = false;
		if (itemSlot != null && itemSlot.getValue() != null) {
			String itemName = itemSlot.getValue();
			logger.info("Price Solt that was requested was " + itemName);
			isSlotUsed = true;
		}

		return isSlotUsed;
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
