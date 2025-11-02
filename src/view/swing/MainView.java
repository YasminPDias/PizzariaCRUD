package view.swing;

import view.swing.orders.OrdersListView;
import view.swing.pizza.PizzaListView;
import view.swing.user.UserListView;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

	public MainView() {
        setTitle("Pizzaria CRUD - Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu userMenu = new JMenu("Clientes");
        JMenuItem userListItem = new JMenuItem("Listar Clientes");
        userListItem.addActionListener(e -> new UserListView(this).setVisible(true));
        userMenu.add(userListItem);
        menuBar.add(userMenu);

        JMenu pizzaMenu = new JMenu("Pizzas");
        JMenuItem pizzaListItem = new JMenuItem("Listar Pizzas");
        pizzaListItem.addActionListener(e -> new PizzaListView(this).setVisible(true));
        pizzaMenu.add(pizzaListItem);
        menuBar.add(pizzaMenu);

        JMenu orderMenu = new JMenu("Pedidos");
        JMenuItem orderListItem = new JMenuItem("Listar Pedidos");
        orderListItem.addActionListener(e -> new OrdersListView(this).setVisible(true));
        orderMenu.add(orderListItem);
        menuBar.add(orderMenu);
        
        menuBar.add(Box.createHorizontalGlue());

        JMenu menuSair = new JMenu("...");
        JMenuItem sairItem = new JMenuItem("Fechar o sistema");
        sairItem.addActionListener(e -> System.exit(0));
        menuSair.add(sairItem);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);

        JLabel label = new JLabel("Seja bem-vindo!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
        contentPanel.add(label, BorderLayout.CENTER);

        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
            if (login.isAuthenticated()) {
                MainView mainView = new MainView();
                mainView.setVisible(true);
                mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                System.exit(0);
            }
        });
    }
}