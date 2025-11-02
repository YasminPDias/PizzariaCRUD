package view.swing.orders;

import java.util.List;
import model.Orders;

public interface IOrdersView {
    void setOrderList(List<Orders> orders);
    void showMessage(String msg);
}