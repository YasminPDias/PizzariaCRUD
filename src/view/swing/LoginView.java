package view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AuthException;
import controller.LoginController;
import model.ModelException;
import view.swing.Admin.ForgotPasswordView;
import view.swing.Admin.RegisterUserView;

public class LoginView extends JDialog {
    private boolean authenticated = false;
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    
    private final LoginController controller;

    public LoginView() {
    	this.controller = new LoginController();
    	
        setTitle("Pizzaria CRUD - Login");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Entrar");
        JButton registerBtn = new JButton("Cadastrar");
        JButton forgotPassBtn = new JButton("Esqueci a senha");
        JButton cancelBtn = new JButton("Cancelar");
        buttons.add(loginBtn);
        buttons.add(registerBtn);
        buttons.add(forgotPassBtn);
        buttons.add(cancelBtn);
        
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttons, BorderLayout.NORTH);

        loginBtn.addActionListener(e -> {
        	try {
            String email = emailField.getText();
            String senha = new String(passwordField.getPassword());
            controller.login(email, senha);
            
            authenticated = true;
            dispose();
        	} catch(AuthException | ModelException ex) {
        		JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro",JOptionPane.ERROR_MESSAGE);
        		authenticated = false;
        	}
        });

        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });
        
        registerBtn.addActionListener(e -> {
            new RegisterUserView(this, controller).setVisible(true);
        });

        forgotPassBtn.addActionListener(e -> {
            new ForgotPasswordView(this, controller).setVisible(true);
        });

        add(form, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}