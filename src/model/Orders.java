package model;

import java.util.Date;

public class Orders {
	private int id;
	private String observation;
	private Date date;
	private User user;
	private Pizza pizza;

	public Orders(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public void validate() {	
		if (user == null || user.getId() <= 0) {
			throw new IllegalArgumentException("Usuário inválido para o pedido.");
		}
		if (pizza == null || pizza.getId() <= 0) {
			throw new IllegalArgumentException("Pizza inválida para o pedido.");
		}
	}
}