package view.swing.orders;

import controller.OrdersController;
import model.ModelException;
import model.Orders;
import model.User;
import model.data.DAOFactory;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrdersListView extends JDialog implements IOrdersView {
    private final OrdersController controller;
    private final OrderTableModel tableModel = new OrderTableModel();
    private final JTable table = new JTable(tableModel);
    private final JComboBox<User> userFilterComboBox = new JComboBox<>();

    public OrdersListView(JFrame parent) {
        super(parent, "Gerenciar Pedidos", true);
        this.controller = new OrdersController();
        this.controller.setOrdersView(this);

        setSize(800, 500);
        setLocationRelativeTo(parent);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filtrar por Cliente:"));
        loadUsersIntoFilter();
        
        userFilterComboBox.insertItemAt(null, 0);
        userFilterComboBox.setSelectedIndex(0);
        userFilterComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText((value instanceof User) ? ((User) value).getName() : "Todos");
                return this;
            }
        });
        filterPanel.add(userFilterComboBox);
        userFilterComboBox.addActionListener(e -> refreshOrderList());

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(28);

        JButton addButton = new JButton("Novo Pedido");
        addButton.addActionListener(e -> {
            OrdersFormView form = new OrdersFormView(this, null, controller);
            form.setVisible(true);
            refreshOrderList();
        });
        
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Orders order = tableModel.getOrderAt(row);
                OrdersFormView form = new OrdersFormView(this, order, controller);
                form.setVisible(true);
                refreshOrderList();
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Orders order = tableModel.getOrderAt(row);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o pedido ?",
                    "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteOrder(order);
                    refreshOrderList();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
            @Override public void mousePressed(MouseEvent e) { showPopup(e); }
            @Override public void mouseReleased(MouseEvent e) { showPopup(e); }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(addButton, BorderLayout.EAST);
        
        setLayout(new BorderLayout());
        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        controller.loadAllOrders();
    }
    
    private void refreshOrderList() {
        User selectedUser = (User) userFilterComboBox.getSelectedItem();
        if (selectedUser != null) {
            controller.loadOrdersForUser(selectedUser);
        } else {
            controller.loadAllOrders();
        }
    }

    private void loadUsersIntoFilter() {
        try {
            List<User> users = DAOFactory.createUserDAO().findAll();
            users.forEach(userFilterComboBox::addItem);
        } catch (ModelException e) {
            showMessage("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    @Override
    public void setOrderList(List<Orders> orders) {
        tableModel.setOrders(orders);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    static class OrderTableModel extends AbstractTableModel {
        private final String[] columns = {"ID Pedido", "Data", "Cliente", "Pizza", "Observação"};
        private List<Orders> orders = new ArrayList<>();
        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        public void setOrders(List<Orders> orders) {
            this.orders = orders;
            fireTableDataChanged();
        }
        
        public Orders getOrderAt(int row) {
            return orders.get(row);
        }

        @Override public int getRowCount() { return orders.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Orders o = orders.get(row);
            switch (col) {
                case 0: return o.getId();
                case 1: return o.getDate() != null ? sdf.format(o.getDate()) : "N/A";
                case 2: return o.getUser() != null ? o.getUser().getName() : "N/A";
                case 3: return o.getPizza() != null ? o.getPizza().getFlavor() : "N/A";
                case 4: return o.getObservation();
                default: return null;
            }
        }
    }
}