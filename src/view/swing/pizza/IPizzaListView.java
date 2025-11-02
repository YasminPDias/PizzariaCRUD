package view.swing.pizza;

import java.util.List;
import model.Pizza;

public interface IPizzaListView {
    void setPizzaList(List<Pizza> pizzas);
    void showMessage(String msg);
}