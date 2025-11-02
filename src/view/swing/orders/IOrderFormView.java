package view.swing.orders;

import model.Orders;

public interface IOrderFormView {
    Orders getOrderFromForm();
    void setOrderInForm(Orders order);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}