package model.data;

import java.util.List;
import model.ModelException;
import model.Orders;

public interface OrdersDAO {
    void save(Orders order) throws ModelException;
    void update(Orders order) throws ModelException;
    void delete(Orders order) throws ModelException;
    List<Orders> findByUserId(int userId) throws ModelException;
    List<Orders> findAll() throws ModelException;
}