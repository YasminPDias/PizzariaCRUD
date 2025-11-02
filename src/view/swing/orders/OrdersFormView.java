package view.swing.orders;

import controller.OrdersController;
import model.ModelException;
import model.Orders;
import model.Pizza;
import model.User;
import model.data.DAOFactory;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OrdersFormView extends JDialog implements IOrderFormView {

    private final JComboBox<User> userComboBox = new JComboBox<>();
    private final JComboBox<Pizza> pizzaComboBox = new JComboBox<>();
    private final JTextArea observationArea = new JTextArea(3, 20);
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private final OrdersController controller;
    private final boolean isNew;
    private Orders order;

    public OrdersFormView(JDialog parent, Orders order, OrdersController controller) {
        super(parent, (order == null) ? "Novo Pedido" : "Editar Pedido", true);
        this.controller = controller;
        this.order = order;
        this.isNew = (order == null);

        loadInitialData();

        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        add(userComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Pizza:"), gbc);
        gbc.gridx = 1;
        add(pizzaComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Observação:"), gbc);
        gbc.gridx = 1;
        observationArea.setLineWrap(true);
        observationArea.setWrapStyleWord(true);
        add(new JScrollPane(observationArea), gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) {
            setOrderInForm(order);
        }

        saveButton.addActionListener(e -> {
            controller.saveOrUpdate(isNew, getOrderFromForm());
            close();
        });
        closeButton.addActionListener(e -> close());
    }

    private void loadInitialData() {
        try {
            List<User> users = DAOFactory.createUserDAO().findAll();
            if (users.isEmpty()) saveButton.setEnabled(false);
            users.forEach(userComboBox::addItem);

            List<Pizza> pizzas = DAOFactory.createPizzaDAO().findAll();
            if (pizzas.isEmpty()) saveButton.setEnabled(false);
            pizzas.forEach(pizzaComboBox::addItem);
        } catch (ModelException e) {
            showErrorMessage("Erro ao carregar dados: " + e.getMessage());
            saveButton.setEnabled(false);
        }
    }

    @Override
    public Orders getOrderFromForm() {
        if (isNew) {
            order = new Orders(0);
            order.setDate(new Date());
        }
        order.setUser((User) userComboBox.getSelectedItem());
        order.setPizza((Pizza) pizzaComboBox.getSelectedItem());
        order.setObservation(observationArea.getText());
        return order;
    }

    @Override
    public void setOrderInForm(Orders order) {
        observationArea.setText(order.getObservation());
        
        for (int i = 0; i < userComboBox.getItemCount(); i++) {
            User userInBox = userComboBox.getItemAt(i);
            if (userInBox != null && Objects.equals(userInBox.getId(), order.getUser().getId())) {
                userComboBox.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < pizzaComboBox.getItemCount(); i++) {
            Pizza pizzaInBox = pizzaComboBox.getItemAt(i);
            if (pizzaInBox != null && Objects.equals(pizzaInBox.getId(), order.getPizza().getId())) {
                pizzaComboBox.setSelectedIndex(i);
                break;
            }
        }
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
        dispose();
    }
}