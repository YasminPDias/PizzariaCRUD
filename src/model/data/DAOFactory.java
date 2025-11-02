package model.data;

import model.data.mysql.MySQLAdminUserDAO; 
import model.data.mysql.MySQLOrdersDAO;
import model.data.mysql.MySQLPizzaDAO;
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {

	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}

	public static PizzasDAO createPizzaDAO() {
		return new MySQLPizzaDAO();
	}

	public static OrdersDAO createOrdersDAO() {
	    return new MySQLOrdersDAO();
	}

	public static AdminUserDAO createAdminUserDAO() {
	    return new MySQLAdminUserDAO();
	}
}