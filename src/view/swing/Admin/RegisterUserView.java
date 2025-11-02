package view.swing.Admin;

import controller.LoginController;
import controller.AuthException;
import model.ModelException;
import javax.swing.*;
import java.awt.*;

public class RegisterUserView extends JDialog {

    private final LoginController controller;
    private final JTextField nameField = new JTextField(25);
    private final JTextField emailField = new JTextField(25);
    private final JPasswordField passwordField = new JPasswordField(25);
    private final JPasswordField confirmPasswordField = new JPasswordField(25);
    private final JButton registerButton = new JButton("Cadastrar");
    private final JButton cancelButton = new JButton("Cancelar");

    public RegisterUserView(Dialog parent, LoginController controller) {
        super(parent, "Novo Cadastro de Operador", true);
        this.controller = controller;

        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email (para login):"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Confirmar Senha:"), gbc);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(registerButton);
        btnPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        cancelButton.addActionListener(e -> dispose());
        registerButton.addActionListener(e -> register());
    }

    private void register() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            controller.registerUser(name, email, password, confirmPass);
            
            JOptionPane.showMessageDialog(this, 
                "Cadastro realizado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (AuthException | ModelException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro no cadastro: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}