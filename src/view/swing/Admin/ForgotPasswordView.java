package view.swing.Admin;

import controller.LoginController;
import model.ModelException;
import javax.swing.*;
import java.awt.*;

public class ForgotPasswordView extends JDialog {

    private final LoginController controller;
    
    private final JTextField emailField = new JTextField(30);

    private final JButton validateButton = new JButton("Validar Email");
    private final JButton cancelButton = new JButton("Cancelar");

    public ForgotPasswordView(Dialog parent, LoginController controller) {
        super(parent, "Esqueci a Senha", true);
        this.controller = controller;

        setSize(450, 180);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0; 
        add(new JLabel("Email Cadastrado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(emailField, gbc);

        gbc.weightx = 0.0; 
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(validateButton);
        btnPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; 
        add(btnPanel, gbc);

        cancelButton.addActionListener(e -> dispose());
        validateButton.addActionListener(e -> validateEmail());
    }

    private void validateEmail() {
        try {
            String email = emailField.getText();
            if (controller.emailExists(email)) {
                NewPasswordView newPassView = new NewPasswordView(this, controller, email);
                this.setVisible(false);
                newPassView.setVisible(true);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum usuário encontrado com este email.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (ModelException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao verificar email: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}