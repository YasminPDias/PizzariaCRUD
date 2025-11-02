package view.swing.Admin;

import controller.LoginController;
import controller.AuthException;
import model.ModelException;
import javax.swing.*;
import java.awt.*;

public class NewPasswordView extends JDialog {

    private final LoginController controller;
    private final String email;
    private final JPasswordField passwordField = new JPasswordField(25);
    private final JPasswordField confirmPasswordField = new JPasswordField(25);
    private final JButton resetButton = new JButton("Redefinir Senha");
    private final JButton cancelButton = new JButton("Cancelar");

    public NewPasswordView(Dialog parent, LoginController controller, String email) {
        super(parent, "Esqueci a Senha", true);
        this.controller = controller;
        this.email = email;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(new JLabel("Email validado: " + email), gbc);
        gbc.gridwidth = 1; 

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nova Senha:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Confirmar Senha:"), gbc);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(resetButton);
        btnPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        cancelButton.addActionListener(e -> dispose());
        resetButton.addActionListener(e -> resetPassword());
    }

    private void resetPassword() {
        try {
            String newPass = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            controller.resetPassword(email, newPass, confirmPass);
            
            JOptionPane.showMessageDialog(this, 
                "Senha redefinida com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (AuthException | ModelException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao redefinir senha: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}