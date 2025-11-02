package view.swing.pizza;

import controller.PizzaController;
import model.Pizza;
import model.PizzaBorder;
import model.PizzaSize;
import javax.swing.*;
import java.awt.*;

public class PizzaFormView extends JDialog implements IPizzaFormView {
    private final JTextField flavorField = new JTextField(20);
    private final JTextArea descriptionArea = new JTextArea(3, 20);
    private final JComboBox<PizzaSize> sizeBox = new JComboBox<>(PizzaSize.values());
    private final JComboBox<PizzaBorder> borderBox = new JComboBox<>(PizzaBorder.values());
    private final JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(30.0, 0.0, 1000.0, 0.50));
    
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    
    private final PizzaController controller;
    private final boolean isNew;
    private final PizzaListView parent;
    private Pizza pizza;

    public PizzaFormView(PizzaListView parent, Pizza pizza, PizzaController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setPizzaFormView(this);
        this.parent = parent;
        this.pizza = pizza;
        this.isNew = (pizza == null);

        setTitle(isNew ? "Nova Pizza" : "Editar Pizza");
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Sabor:"), gbc);
        gbc.gridx = 1;
        add(flavorField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1;
        add(sizeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        add(valueSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Borda Recheada:"), gbc);
        gbc.gridx = 1;
        add(borderBox, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) {
            setPizzaInForm(pizza);
        }

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Pizza getPizzaFromForm() {
        if (pizza == null) {
            pizza = new Pizza();
        }
        pizza.setFlavor(flavorField.getText());
        pizza.setDescription(descriptionArea.getText());
        pizza.setSize((PizzaSize) sizeBox.getSelectedItem());
        pizza.setValue((Double) valueSpinner.getValue());
        pizza.setBorder((PizzaBorder) borderBox.getSelectedItem());
        return pizza;
    }

    @Override
    public void setPizzaInForm(Pizza pizza) {
        flavorField.setText(pizza.getFlavor());
        descriptionArea.setText(pizza.getDescription());
        sizeBox.setSelectedItem(pizza.getSize());
        valueSpinner.setValue(pizza.getValue());
        borderBox.setSelectedItem(pizza.getBorder());
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
}