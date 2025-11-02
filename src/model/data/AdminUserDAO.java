package model.data;

import model.AdminUser;
import model.ModelException;

public interface AdminUserDAO {
    
    void save(AdminUser admin) throws ModelException;

    AdminUser findByEmail(String email) throws ModelException;

    void updatePassword(String email, String newHashedPassword) throws ModelException;
}