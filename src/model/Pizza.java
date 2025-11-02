package model;

public class Pizza {
	private int id;
	private String flavor;
	private String description;
	private PizzaSize size;
	private double value;
	private PizzaBorder border;
	
	public Pizza(int id) {
		this.id = id;
	}
	
	public Pizza() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PizzaSize getSize() {
		return size;
	}

	public void setSize(PizzaSize size) {
		this.size = size;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public PizzaBorder getBorder() {
		return border;
	}

	public void setBorder(PizzaBorder border) {
		this.border = border;
	}
	
	public void validate() {
		if (flavor == null || flavor.isBlank()) {
			throw new IllegalArgumentException("O sabor não pode ser vazio.");
		}
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("É preciso conter a descrição do sabor.");
		}
		if (size == null) {
			throw new IllegalArgumentException("Selecione um tamanho.");
		}
		if (value <= 0) {
			throw new IllegalArgumentException("Informe um valor válido para a pizza.");
		}
		if (border == null) {
			throw new IllegalArgumentException("Informe a escolha da borda.");
		}
	}

	@Override
	public String toString() {
		return this.flavor;
	}
}