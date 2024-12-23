package view;

import com.formdev.flatlaf.FlatLightLaf;
import controllers.StudentCreateController;
import controllers.StudentListController;
import controllers.StudentUpdateController;
import enums.SearchParam;
import filters.StudentFilter;
import observer.Subscriber;
import student.Student;
import template.DataListStudentShort;
import student.StudentShort;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainWindow implements Subscriber {

    private static final int PAGE_SIZE = 10;
    private static int CURRENT_PAGE = 1;

    private StudentListController controller;

    public void setController(StudentListController controller) {
        this.controller = controller;
    }

    private DataListStudentShort dataList;

    public void setDataList(DataListStudentShort dataList) {
        this.dataList = dataList;
    }

    private DefaultTableModel tableModel;

    private final JTextField nameField = new JTextField();

    private final JComboBox<String> gitComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private final JTextField gitField = new JTextField();

    private final JTextField emailField = new JTextField();
    private final JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private final JTextField phoneField = new JTextField();
    private final JComboBox<String> phoneComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private final JTextField tgField = new JTextField();
    private final JComboBox<String> tgComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private final JLabel pageInfoLabel = new JLabel("Страница: 1 / ?");
    private final JButton prevPageButton = new JButton("<");
    private final JButton nextPageButton = new JButton(">");

    private final JButton refreshButton = new JButton("Обновить");
    private final JButton addButton = new JButton("Добавить");
    private final JButton editButton = new JButton("Изменить");
    private final JButton deleteButton = new JButton("Удалить");

    public void create(StudentListController controller) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Не удалось загрузить FlatLightLaf:(");
        }
        setController(controller);
        controller.firstInitDataList();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Управление студентами");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(854, 480);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", createStudentTab());

            frame.add(tabbedPane);
            frame.setVisible(true);
            update();
        });
    }

    private JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        String[] columnNames = dataList.getEntityFields().toArray(new String[0]);
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        sorter.setComparator(1, Comparator.comparing(String::toString));
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                if (column == 1) {
                    sorter.toggleSortOrder(column);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRowCount = table.getSelectedRowCount();
            editButton.setEnabled(selectedRowCount == 1);
            deleteButton.setEnabled(selectedRowCount > 0);
        });

        addButton.addActionListener(e -> {
            StudentCreateController studentCreateController = new StudentCreateController(this.controller);
            StudentForm modal = new StudentForm();
            modal.controller = studentCreateController;
            modal.create(null, "Добавить студента");
        });

        editButton.addActionListener(e -> {
            StudentUpdateController studentUpdateController = new StudentUpdateController(this.controller);
            StudentForm modal = new StudentForm();
            modal.controller = studentUpdateController;
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Student student = studentUpdateController.getStudentById(id);
                modal.create(student, "Изменить студента");
            }
        });

        deleteButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        panel,
                        "Удалить выбранных студентов?",
                        "Подтвердите действие",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = true;

                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int id = (int) tableModel.getValueAt(selectedRows[i], 0);
                        if (!controller.deleteStudent(id)) {
                            success = false;
                        }
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(panel, "Успешно");
                    } else {
                        JOptionPane.showMessageDialog(panel, "При удалении произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }

                    controller.refreshData();
                }
            }
        });

        nextPageButton.addActionListener(e -> {
            CURRENT_PAGE++;
            controller.refreshData(PAGE_SIZE, CURRENT_PAGE, getCurrentFilter());
        });

        prevPageButton.addActionListener(e -> {
            if (CURRENT_PAGE > 1) {
                CURRENT_PAGE--;
                controller.refreshData(PAGE_SIZE, CURRENT_PAGE, getCurrentFilter());
            }
        });

        refreshButton.addActionListener(e -> updateFilterInfo());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(prevPageButton);
        buttonPanel.add(pageInfoLabel);
        buttonPanel.add(nextPageButton);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        addFilters(panel);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void update() {
        setTableParams();
        setTableData();
    }

    private void setTableParams() {
        List<String> newColumnNames = dataList.getEntityFields();
        tableModel.setColumnIdentifiers(newColumnNames.toArray());

        int lastPage = dataList.getPagination().getTotalPages();

        if (lastPage < CURRENT_PAGE) {
            CURRENT_PAGE = lastPage;
            controller.refreshData(PAGE_SIZE, CURRENT_PAGE, getCurrentFilter());
            return;
        }

        updatePageControls(lastPage);
    }

    private void updatePageControls(int lastPage) {
        pageInfoLabel.setText(String.format("Страница %d / %d ", CURRENT_PAGE, lastPage));

        prevPageButton.setEnabled(CURRENT_PAGE > 1);
        nextPageButton.setEnabled(CURRENT_PAGE < lastPage);
    }

    private StudentFilter getCurrentFilter() {
        return null;
    }

    private void setTableData() {
        tableModel.setRowCount(0);

        List<StudentShort> students = dataList.toList();

        for (StudentShort student : students) {
            tableModel.addRow(new Object[] {
                    student.getId(),
                    student.getSurnameWithInitials(),
                    student.getGitInfo(),
                    student.getContactInfo(),
            });
        }
    }

    public void showError(String message) {
        JDialog dialog = new JDialog((Frame) null, "Ошибка", true);
        dialog.setSize(569, 320);
        dialog.setLayout(new GridLayout(7, 2));
        JOptionPane.showMessageDialog(dialog, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void updateFilterInfo() {
        String nameFilter = nameField.getText().trim();
        SearchParam gitSearch = SearchParam.create(
                (String) Objects.requireNonNull(gitComboBox.getSelectedItem())
        );
        String gitFilter = gitField.getText().trim();
        SearchParam emailSearch = SearchParam.create(
                (String) Objects.requireNonNull(emailComboBox.getSelectedItem())
        );
        String emailFilter = emailField.getText().trim();

        SearchParam phoneSearch = SearchParam.create(
                (String) Objects.requireNonNull(phoneComboBox.getSelectedItem())
        );
        String phoneFilter = phoneField.getText().trim();

        SearchParam tgSearch = SearchParam.create(
                (String) Objects.requireNonNull(tgComboBox.getSelectedItem())
        );
        String tgFilter = tgField.getText().trim();

        StudentFilter studentFilter = new StudentFilter(
                nameFilter,
                gitFilter,
                emailFilter,
                phoneFilter,
                tgFilter,
                gitSearch,
                phoneSearch,
                tgSearch,
                emailSearch
        );

        controller.refreshData(PAGE_SIZE, CURRENT_PAGE, studentFilter);
    }

    private void addFilters(JPanel panel) {
        JPanel filterPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));

        setupFilter(gitComboBox, gitField);
        setupFilter(emailComboBox, emailField);
        setupFilter(phoneComboBox, phoneField);
        setupFilter(tgComboBox, tgField);

        filterPanel.add(new JLabel("Фамилия И.О."));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel());

        filterPanel.add(new JLabel("git"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);

        filterPanel.add(new JLabel("email"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);

        filterPanel.add(new JLabel("телефон"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);

        filterPanel.add(new JLabel("tg"));
        filterPanel.add(tgComboBox);
        filterPanel.add(tgField);

        panel.add(filterPanel, BorderLayout.NORTH);
    }

    private static void setupFilter(JComboBox<String> comboBox, JTextField textField) {
        textField.setEnabled(false);
        comboBox.addActionListener(e -> {
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), "Да"));
        });
    }
}
