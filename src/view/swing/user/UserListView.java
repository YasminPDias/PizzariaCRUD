package view.swing.user;

import controller.UserController;
import model.User;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UserListView extends JDialog implements IUserListView {
    private final UserController controller;
    private final UserTableModel tableModel = new UserTableModel();
    private final JTable table = new JTable(tableModel);

    public UserListView(JFrame parent) {
        super(parent, "Gerenciar Usuários", true);
        this.controller = new UserController();
        this.controller.setUserListView(this);

        setSize(650, 400);
        setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Usuário");
        addButton.addActionListener(e -> {
            UserFormView form = new UserFormView(this, null, controller);
            form.setVisible(true);
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

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

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                User user = tableModel.getUserAt(row);
                UserFormView form = new UserFormView(this, user, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                User user = tableModel.getUserAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Tem certeza que deseja excluir o usuário '" + user.getName() + "'?", 
                    "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirUsuario(user);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        
        controller.loadUsers();
    }

    @Override
    public void setUserList(List<User> users) {
        tableModel.setUsers(users);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadUsers();
    }

    static class UserTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Nome", "Sexo", "Email"};
        private List<User> users = new ArrayList<>();

        public void setUsers(List<User> users) {
            this.users = users;
            fireTableDataChanged();
        }

        public User getUserAt(int row) {
            return users.get(row);
        }

        @Override public int getRowCount() { return users.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            User u = users.get(row);
            switch (col) {
                case 0: return u.getId();
                case 1: return u.getName();
                case 2: return u.getGender().value();
                case 3: return u.getEmail();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}