package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ModelException;
import model.Orders;
import model.Pizza;
import model.User;
import model.UserGender;
import model.data.DAOUtils;
import model.data.OrdersDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLOrdersDAO implements OrdersDAO {

    @Override
    public void save(Orders order) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = MySQLConnectionFactory.getConnection();
            String sql = "INSERT INTO orders (observation, pizza_id, orders_date, user_id) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, order.getObservation());
            preparedStatement.setInt(2, order.getPizza().getId()); 
            preparedStatement.setDate(3, new java.sql.Date(order.getDate().getTime()));
            preparedStatement.setInt(4, order.getUser().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DAOUtils.sqlExceptionTreatement("Erro ao salvar pedido.", e);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void update(Orders order) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sql = "UPDATE orders SET observation = ?, pizza_id = ?, user_id = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, order.getObservation());
            preparedStatement.setInt(2, order.getPizza().getId());
            preparedStatement.setInt(3, order.getUser().getId());
            preparedStatement.setInt(4, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DAOUtils.sqlExceptionTreatement("Erro ao atualizar pedido.", e);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void delete(Orders order) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sql = "DELETE FROM orders WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DAOUtils.sqlExceptionTreatement("Erro ao excluir pedido.", e);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public List<Orders> findByUserId(int userId) throws ModelException {
        String sql = "SELECT o.*, u.nome, u.sexo, u.email, p.flavor " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "JOIN pizza p ON o.pizza_id = p.id " +
                     "WHERE o.user_id = ? ORDER BY o.orders_date DESC";
        return findOrders(sql, userId);
    }

    @Override
    public List<Orders> findAll() throws ModelException {
        String sql = "SELECT o.*, u.nome, u.sexo, u.email, p.flavor " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "JOIN pizza p ON o.pizza_id = p.id " +
                     "ORDER BY o.orders_date DESC";
        return findOrders(sql, null);
    }
    
    private List<Orders> findOrders(String sql, Integer userId) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Orders> ordersList = new ArrayList<>();

        try {
            connection = MySQLConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (userId != null) {
                preparedStatement.setInt(1, userId);
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Orders order = createOrderFromResultSet(rs);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            DAOUtils.sqlExceptionTreatement("Erro ao buscar pedidos.", e);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
        return ordersList;
    }

    private Orders createOrderFromResultSet(ResultSet rs) throws SQLException {
        int orderId = rs.getInt("id");
        String observation = rs.getString("observation");
        java.util.Date date = rs.getTimestamp("orders_date");
        
        int ownerId = rs.getInt("user_id");
        String userName = rs.getString("nome");
        String userGenderStr = rs.getString("sexo");
        String userEmail = rs.getString("email");
        UserGender userGender = "M".equals(userGenderStr) ? UserGender.M : UserGender.F;
        User user = new User(ownerId);
        user.setName(userName);
        user.setGender(userGender);
        user.setEmail(userEmail);

        int pizzaId = rs.getInt("pizza_id");
        String pizzaFlavor = rs.getString("flavor");
        Pizza pizza = new Pizza(pizzaId);
        pizza.setFlavor(pizzaFlavor);

        Orders order = new Orders(orderId);
        order.setObservation(observation);
        order.setDate(date);
        order.setUser(user);
        order.setPizza(pizza);
        return order;
    }
}