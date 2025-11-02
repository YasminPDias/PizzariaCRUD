package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ModelException;
import model.Pizza;
import model.PizzaBorder;
import model.PizzaSize;
import model.data.DAOUtils;
import model.data.PizzasDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLPizzaDAO implements PizzasDAO {

    @Override
    public void save(Pizza pizza) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sqlInsert = "INSERT INTO pizza (flavor, descricao, size, valor, border) VALUES (?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, pizza.getFlavor());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setString(3, pizza.getSize().toString());
            preparedStatement.setDouble(4, pizza.getValue());
            preparedStatement.setString(5, pizza.getBorder().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao inserir pizza no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void update(Pizza pizza) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sqlUpdate = "UPDATE pizza SET flavor = ?, descricao = ?, size = ?, valor = ?, border = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, pizza.getFlavor());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setString(3, pizza.getSize().toString());
            preparedStatement.setDouble(4, pizza.getValue());
            preparedStatement.setString(5, pizza.getBorder().toString());
            preparedStatement.setInt(6, pizza.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao atualizar pizza no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void delete(Pizza pizza) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sqlDelete = "DELETE FROM pizza WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, pizza.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao deletar pizza do BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public List<Pizza> findAll() throws ModelException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Pizza> pizzasList = new ArrayList<>();

        try {
            connection = MySQLConnectionFactory.getConnection();
            statement = connection.createStatement();
            String sqlSelect = "SELECT * FROM pizza ORDER BY flavor;";
            rs = statement.executeQuery(sqlSelect);

            while (rs.next()) {
                pizzasList.add(createPizzaFromResultSet(rs));
            }
        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao carregar pizzas do BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(statement);
            DAOUtils.close(connection);
        }
        return pizzasList;
    }

	@Override
	public Pizza findById(int id) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Pizza pizza = null;

		try {
			connection = MySQLConnectionFactory.getConnection();
			String sqlSelect = "SELECT * FROM pizza WHERE id = ?;";
			preparedStatement = connection.prepareStatement(sqlSelect);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
                pizza = createPizzaFromResultSet(rs);
			}
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao buscar pizza por id no BD.", sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		return pizza;
	}

    private Pizza createPizzaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String flavor = rs.getString("flavor");
        String description = rs.getString("descricao");
        String sizeStr = rs.getString("size");
        double value = rs.getDouble("valor");
        String borderStr = rs.getString("border");

        PizzaSize size = PizzaSize.valueOf(sizeStr);
        PizzaBorder border = PizzaBorder.valueOf(borderStr);
        
        Pizza pizza = new Pizza(id);
        pizza.setFlavor(flavor);
        pizza.setDescription(description);
        pizza.setSize(size);
        pizza.setValue(value);
        pizza.setBorder(border);
        return pizza;
    }
}