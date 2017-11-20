package Models;

public enum Menus {
	SUPPE("SUPPE"), MENU1("MEN� 1"), MENU2("MEN� 2"), MENU3("MEN� 3"), AKTION(
			"SUPPE"), BISTRO("SUPPE"), DESSERT("SUPPE");

	private String value;

	private Menus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
