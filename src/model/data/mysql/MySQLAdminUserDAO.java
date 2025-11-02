package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AdminUser;
import model.ModelException;
import model.data.AdminUserDAO;
import model.data.DAOUtils;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLAdminUserDAO implements AdminUserDAO{

	@Override
	public void save(AdminUser admin) throws ModelException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connection = MySQLConnectionFactory.getConnection();
			String sql = "INSERT INTO admin_logins (name, email, password) VALUES (?, ?, ?);";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, admin.getName());
			preparedStatement.setString(2, admin.getEmail());
			preparedStatement.setString(3, admin.getPassword());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DAOUtils.sqlExceptionTreatement("Erro ao salvar admin no Banco de Dados", e);
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}

	@Override
	public AdminUser findByEmail(String email) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		AdminUser admin = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();
			String sql = "SELECT * FROM admin_logins WHERE email = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			rs =  preparedStatement.executeQuery();
			
			if(rs.next()) {
				admin =  new AdminUser();
				admin.setId(rs.getInt("id"));
				admin.setName(rs.getString("name"));
				admin.setEmail(rs.getString("email"));
				admin.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			DAOUtils.sqlExceptionTreatement("Erro ao encontrar/buscar admin por email", e);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		return admin;
	}

	@Override
	public void updatePassword(String email, String newHashedPassword) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();
			String sql = "UPDATE admin_logins SET password = ? WHERE email = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newHashedPassword);
			preparedStatement.setString(2, email);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar senha do admin", e);
		} finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
		
	}



}
