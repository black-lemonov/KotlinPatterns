package view;

import adapter.StudentListDBAdapter;
import students.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Objects;

public class TablePanel extends JPanel {
    private static JTable table;
    private static DefaultTableModel model;
    private static final StudentListDBAdapter students = new StudentListDBAdapter();

    private static int currentPage = 1;
    private static int pageSize = 10;

    TablePanel() {
        super(new BorderLayout());
        addFilters();
        addTable();
        refreshModel();
        addButtons();
    }

    private void addFilters() {
        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));

        JTextField nameField = new JTextField();
        JComboBox<String> gitComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("Фамилия И.О."));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel());

        JTextField phoneField = new JTextField();
        JComboBox<String> phoneComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("Телефон"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);
        setFilter(phoneComboBox, phoneField);

        JTextField tgField = new JTextField();
        JComboBox<String> tgComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("Tg"));
        filterPanel.add(tgComboBox);
        filterPanel.add(tgField);
        setFilter(tgComboBox, tgField);

        JTextField emailField = new JTextField();
        JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("Email"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);
        setFilter(emailComboBox, emailField);

        JTextField gitField = new JTextField();
        filterPanel.add(new JLabel("Git"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);
        setFilter(gitComboBox, gitField);

        this.add(filterPanel, BorderLayout.NORTH);
    }

    private void addTable() {
        String[] columnNames = { "ID", "Фамилия И.О.", "Телефон", "Tg", "Email", "Git" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("+");
        JButton editButton = new JButton("Изменить");
        JButton deleteButton = new JButton("-");
        JButton nextPageButton = new JButton(">");
        JButton prevPageButton = new JButton("<");
        JButton refreshButton = new JButton("Обновить");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        refreshModel();

        addButton.addActionListener(e -> {
            Student student = new Student(
                    11,
                    "Учиха", "Саске", "Фугаку",
                    "+79298389922", "https://t.me/sasuke",
                    "sasuke@hebi.com", "https://www.github.com/sasukeprog"
            );
            students.add(student);
            refreshModel();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int id = (int) table.getValueAt(selectedRow, 0);
            Student student = students.get(id);
            student.setName("Изменено");
            refreshModel();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int id = (int) table.getValueAt(selectedRow, 0);
            students.removeById(id);
            refreshModel();
        });

        var pageLbl = new JLabel(Integer.toString(currentPage));
        nextPageButton.addActionListener(e -> {
            currentPage++;
            pageLbl.setText(Integer.toString(currentPage));
            refreshModel();
        });

        prevPageButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                pageLbl.setText(Integer.toString(currentPage));
                refreshModel();
            }
        });

        refreshButton.addActionListener(e -> {
            refreshModel();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(prevPageButton);
        buttonPanel.add(pageLbl);
        buttonPanel.add(nextPageButton);
        buttonPanel.add(refreshButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private static void refreshModel() {
        model.setRowCount(0);

        var tablePage = students.getByPage(currentPage, pageSize).getData();
        for (int row = 1; row <= tablePage.getRows(); row++ ) {
            var rowData = new Object[tablePage.getColumns()];
            for (int col = 1; col <= tablePage.getColumns(); col++ ) {
                rowData[col-1] = tablePage.get(row, col);
            }
            model.addRow(rowData);
        }
    }

    private static void setFilter(JComboBox<String> comboBox, JTextField textField) {
        textField.setEnabled(false);
        comboBox.addActionListener(e -> {
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), "Да"));
        });
    }
}
