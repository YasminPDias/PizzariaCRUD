package controller;

import java.util.List;
import model.ModelException;
import model.Pizza;
import model.data.DAOFactory;
import model.data.PizzasDAO;
import view.swing.pizza.IPizzaFormView;
import view.swing.pizza.IPizzaListView;

public class PizzaController {
    private final PizzasDAO pizzaDAO = DAOFactory.createPizzaDAO();
    private IPizzaListView pizzaListView;
    private IPizzaFormView pizzaFormView;

    public void loadPizzas() {
        try {
            List<Pizza> pizzas = pizzaDAO.findAll();
            if (pizzaListView != null) {
                pizzaListView.setPizzaList(pizzas);
            }
        } catch (ModelException e) {
            if (pizzaListView != null) {
                pizzaListView.showMessage("Erro ao carregar pizzas: " + e.getMessage());
            }
        }
    }

    public void saveOrUpdate(boolean isNew) {
        if (pizzaFormView == null) return;
        Pizza pizza = pizzaFormView.getPizzaFromForm();

        try {
            pizza.validate();
        } catch (IllegalArgumentException e) {
            pizzaFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                pizzaDAO.save(pizza);
            } else {
                pizzaDAO.update(pizza);
            }
            pizzaFormView.showInfoMessage("Pizza salva com sucesso!");
            pizzaFormView.close();
        } catch (ModelException e) {
            pizzaFormView.showErrorMessage("Erro ao salvar pizza: " + e.getMessage());
        }
    }

    public void deletePizza(Pizza pizza) {
        if (pizzaListView == null) return;
        try {
            pizzaDAO.delete(pizza);
            pizzaListView.showMessage("Pizza excluída com sucesso!");
            loadPizzas();
        } catch (ModelException e) {
            pizzaListView.showMessage("Erro ao excluir pizza: " + e.getMessage());
        }
    }

    public void setPizzaListView(IPizzaListView pizzaListView) {
        this.pizzaListView = pizzaListView;
    }

    public void setPizzaFormView(IPizzaFormView pizzaFormView) {
        this.pizzaFormView = pizzaFormView;
    }
}