package Models;

public enum Menus {
	SUPPE("suppe"), MENU1("menü eins"), MENU2("menü zwei"), MENU3("menü drei"), AKTION(
			"aktion"), BISTRO("bistro"), DESSERT("dessert");

	private String value;

	private Menus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
