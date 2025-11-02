package model;

public class AdminUser {
	private int id;
	private String name;
	private String email;
	private String password;
	
	public AdminUser() {}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void validate() {
		if(name == null || name.isBlank()) {
			throw new IllegalArgumentException("O nome não pode ser vazio.");
		}
		if(email == null || email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("O email é invalido");
		}
	}
}
