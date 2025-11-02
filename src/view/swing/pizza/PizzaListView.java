package view.swing.pizza;

import controller.PizzaController;
import model.Pizza;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PizzaListView extends JDialog implements IPizzaListView {
    private final PizzaController controller;
    private final PizzaTableModel tableModel = new PizzaTableModel();
    private final JTable table = new JTable(tableModel);

    public PizzaListView(JFrame parent) {
        super(parent, "Gerenciar Pizzas", true);
        this.controller = new PizzaController();
        this.controller.setPizzaListView(this);

        setSize(750, 450);
        setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(28);

        JButton addButton = new JButton("Adicionar Pizza");
        addButton.addActionListener(e -> {
            PizzaFormView form = new PizzaFormView(this, null, controller);
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
                Pizza pizza = tableModel.getPizzaAt(row);
                PizzaFormView form = new PizzaFormView(this, pizza, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Pizza pizza = tableModel.getPizzaAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Tem certeza que deseja excluir a pizza '" + pizza.getFlavor() + "'?", 
                    "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deletePizza(pizza);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadPizzas();
    }

    @Override
    public void setPizzaList(List<Pizza> pizzas) {
        tableModel.setPizzas(pizzas);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadPizzas();
    }

    static class PizzaTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Sabor", "Descrição", "Tamanho", "Valor", "Borda Recheada"};
        private List<Pizza> pizzas = new ArrayList<>();

        public void setPizzas(List<Pizza> pizzas) {
            this.pizzas = pizzas;
            fireTableDataChanged();
        }

        public Pizza getPizzaAt(int row) {
            return pizzas.get(row);
        }

        @Override public int getRowCount() { return pizzas.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Pizza p = pizzas.get(row);
            switch (col) {
                case 0: return p.getId();
                case 1: return p.getFlavor();
                case 2: return p.getDescription();
                case 3: return p.getSize().value();
                case 4: return String.format("R$ %.2f", p.getValue());
                case 5: return p.getBorder().value();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}