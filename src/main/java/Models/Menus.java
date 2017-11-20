package Models;

public enum Menus {
	SUPPE("SUPPE"), MENU1("MENÜ 1"), MENU2("MENÜ 2"), MENU3("MENÜ 3"), AKTION(
			"SUPPE"), BISTRO("SUPPE"), DESSERT("SUPPE");

	private String value;

	private Menus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
