package model.data;

import java.util.List;
import model.ModelException;
import model.Pizza;

public interface PizzasDAO {
	void save(Pizza pizza) throws ModelException;
	void update(Pizza pizza) throws ModelException;
	void delete(Pizza pizza) throws ModelException;
	List<Pizza> findAll() throws ModelException;
	Pizza findById(int id) throws ModelException;
}