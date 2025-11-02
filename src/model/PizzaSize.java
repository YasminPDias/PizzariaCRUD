package model;

public enum PizzaSize {
	P("Pequena"), 
	M("Média"),
	G("Grande");
	
	private String value;
	
	PizzaSize(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
