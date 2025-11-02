package controller;

import model.AdminUser;
import model.ModelException;
import model.data.AdminUserDAO;
import model.data.DAOFactory;

public class LoginController {
    

    private final AdminUserDAO adminDAO = DAOFactory.createAdminUserDAO();

    public AdminUser login(String email, String plainPassword) throws AuthException, ModelException {
        if (email == null || email.isBlank() || plainPassword == null || plainPassword.isBlank()) {
            throw new AuthException("Email e senha são obrigatórios.");
        }

        AdminUser admin = adminDAO.findByEmail(email);
        
        if (admin == null) {
            throw new AuthException("Email ou senha inválidos.");
        }

        if (!AuthService.checkPassword(plainPassword, admin.getPassword())) {
            throw new AuthException("Email ou senha inválidos.");
        }

        return admin;
    }

    public void registerUser(String name, String email, String plainPassword, String confirmPassword) 
            throws AuthException, ModelException {
        
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new AuthException("A senha não pode ser vazia.");
        }
        if (!plainPassword.equals(confirmPassword)) {
            throw new AuthException("As senhas não conferem.");
        }
        if (emailExists(email)) {
            throw new AuthException("Este email já está cadastrado.");
        }

        AdminUser newAdmin = new AdminUser();
        newAdmin.setName(name);
        newAdmin.setEmail(email);

        try {
            newAdmin.validate(); 
        } catch (IllegalArgumentException e) {
            throw new AuthException(e.getMessage());
        }

        String hashedPassword = AuthService.hashPassword(plainPassword);
        newAdmin.setPassword(hashedPassword);
        
        adminDAO.save(newAdmin);
    }


    public boolean emailExists(String email) throws ModelException {
        return adminDAO.findByEmail(email) != null;
    }

    public void resetPassword(String email, String newPlainPassword, String confirmPassword) 
            throws AuthException, ModelException {
        
        if (newPlainPassword == null || newPlainPassword.isBlank()) {
            throw new AuthException("A nova senha não pode ser vazia.");
        }
        if (!newPlainPassword.equals(confirmPassword)) {
            throw new AuthException("As senhas não conferem.");
        }
        
        if (!emailExists(email)) {
             throw new AuthException("Nenhum usuário encontrado com este email.");
        }

        String hashedPassword = AuthService.hashPassword(newPlainPassword);
        adminDAO.updatePassword(email, hashedPassword);
    }
}