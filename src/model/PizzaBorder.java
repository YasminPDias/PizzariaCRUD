package model;

public enum PizzaBorder {
	S("Sim"), 
	N("Nao");
	
	private String value;
	
	PizzaBorder(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
