package Models;

public enum Menus {
	SUPPE("suppe"), MENU1("men� eins"), MENU2("men� zwei"), MENU3("men� drei"), AKTION(
			"aktion"), BISTRO("bistro"), DESSERT("dessert");

	private String value;

	private Menus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
