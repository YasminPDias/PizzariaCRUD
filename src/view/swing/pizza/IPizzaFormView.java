package view.swing.pizza;

import model.Pizza;

public interface IPizzaFormView {
    Pizza getPizzaFromForm();
    void setPizzaInForm(Pizza pizza);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}