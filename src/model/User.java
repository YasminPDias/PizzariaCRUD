package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private String name;
	private UserGender gender;
	private String email;
	private List<Orders> orders;

	public User(int id) {
		this.id = id;
		this.orders = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserGender getGender() {
		return gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public void validate() {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
		}
		if (gender == null) {
			throw new IllegalArgumentException("O sexo do usuário é inválido.");
		}
		if (email == null || email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("O email do usuário é inválido.");
		}	
	}

	@Override
	public String toString() {
		return this.name;
	}
}