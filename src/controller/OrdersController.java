package controller;

import java.util.List;
import model.ModelException;
import model.Orders;
import model.User;
import model.data.DAOFactory;
import model.data.OrdersDAO;
import view.swing.orders.IOrdersView;

public class OrdersController {
    private final OrdersDAO ordersDAO = DAOFactory.createOrdersDAO();
    private IOrdersView ordersView;

    public void loadOrdersForUser(User user) {
        if (ordersView == null || user == null) return;
        try {
            List<Orders> orders = ordersDAO.findByUserId(user.getId());
            ordersView.setOrderList(orders);
        } catch (ModelException e) {
            ordersView.showMessage("Erro ao carregar pedidos: " + e.getMessage());
        }
    }
    
    public void loadAllOrders() {
        if (ordersView == null) return;
        try {
            List<Orders> orders = ordersDAO.findAll();
            ordersView.setOrderList(orders);
        } catch (ModelException e) {
            ordersView.showMessage("Erro ao carregar todos os pedidos: " + e.getMessage());
        }
    }

    public void saveOrUpdate(boolean isNew, Orders order) {
        if (ordersView == null) return;
        try {
            order.validate();
        } catch (IllegalArgumentException e) {
            ordersView.showMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                ordersDAO.save(order);
                ordersView.showMessage("Pedido salvo com sucesso!");
            } else {
                ordersDAO.update(order);
                ordersView.showMessage("Pedido atualizado com sucesso!");
            }
        } catch (ModelException e) {
            ordersView.showMessage("Erro ao salvar o pedido: " + e.getMessage());
        }
    }

    public void deleteOrder(Orders order) {
        if (ordersView == null || order == null) return;
        try {
            ordersDAO.delete(order);
            ordersView.showMessage("Pedido #" + order.getId() + " excluído com sucesso!");
        } catch (ModelException e) {
            ordersView.showMessage("Erro ao excluir pedido: " + e.getMessage());
        }
    }
    
    public void setOrdersView(IOrdersView ordersView) {
        this.ordersView = ordersView;
    }
}