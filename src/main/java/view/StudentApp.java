package view;

import adapter.StudentList;
import singleton.StudentListDB;
import students.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;


public class StudentApp {
    private static final JTextField nameField = new JTextField();
    private static final JTextField phoneField = new JTextField();
    private static final JComboBox<String> phoneComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField tgField = new JTextField();
    private static final JComboBox<String> tgComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField emailField = new JTextField();
    private static final JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField gitField = new JTextField();
    private static final JComboBox<String> gitComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private static JTable table;
    private static DefaultTableModel model;
    private static final StudentList studentList = new StudentListDB();

    private static boolean LAST_PAGE = false;
    private static int CURRENT_PAGE = 1;
    private static final int PAGE_SIZE = 10;

    public static void create() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(854, 480);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", createTable());
            tabbedPane.add("Вкладка 2", new JLabel());
            tabbedPane.add("Вкладка 3", new JLabel());

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        StudentApp.create();
    }

    private static JPanel createTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));

        filterPanel.add(new JLabel("Фамилия И.О."));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel());

        filterPanel.add(new JLabel("телефон"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);
        setFilter(phoneComboBox, phoneField);

        filterPanel.add(new JLabel("tg"));
        filterPanel.add(tgComboBox);
        filterPanel.add(tgField);
        setFilter(tgComboBox, tgField);

        filterPanel.add(new JLabel("email"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);
        setFilter(emailComboBox, emailField);

        filterPanel.add(new JLabel("git"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);
        setFilter(gitComboBox, gitField);

        tablePanel.add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Фамилия И.О.", "телефон", "tg", "email", "git" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        refreshModel();

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Изменить");
        JButton deleteButton = new JButton("Удалить");
        JButton nextPageButton = new JButton(">");
        JButton prevPageButton = new JButton("<");
        JButton refreshButton = new JButton("Обновить");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRowCount = table.getSelectedRowCount();
            editButton.setEnabled(selectedRowCount == 1);
            deleteButton.setEnabled(selectedRowCount > 0);
        });

        refreshModel();

        addButton.addActionListener(e -> {
            showForm(null, "Новый студент", student -> {
                studentList.add(student);
                JOptionPane.showMessageDialog(tablePanel, "Успешно!");
                refreshModel();
            });
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) model.getValueAt(selectedRow, 0);
                Student student = studentList.get(id);
                if (student != null) {
                    showForm(student, "Изменить студента", updatedStudent -> {
                        studentList.update(updatedStudent, student.getId());
                        JOptionPane.showMessageDialog(tablePanel, "Успешно!");
                        refreshModel();
                    });
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        tablePanel,
                        "Удалить выбранных студентов?",
                        "Подтвердите действие",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int id = (int) model.getValueAt(selectedRows[i], 0);
                        studentList.remove(id);
                    }
                    JOptionPane.showMessageDialog(tablePanel, "Успешно!");
                    refreshModel();
                }
            }
        });

        var pageLbl = new JLabel(Integer.toString(CURRENT_PAGE));
        nextPageButton.addActionListener(e -> {
            if (!LAST_PAGE) {
                CURRENT_PAGE++;
                pageLbl.setText(Integer.toString(CURRENT_PAGE));
                refreshModel();
            }
        });

        prevPageButton.addActionListener(e -> {
            if (CURRENT_PAGE > 1) {
                CURRENT_PAGE--;
                pageLbl.setText(Integer.toString(CURRENT_PAGE));
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

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        return tablePanel;
    }

    private static void setFilter(JComboBox<String> comboBox, JTextField textField) {
        textField.setEnabled(false);
        comboBox.addActionListener(e -> {
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), "Да"));
        });
    }

    private static void refreshModel() {
        model.setRowCount(0);

        var tablePage = studentList.getByPage(CURRENT_PAGE, PAGE_SIZE).getData();
        int rows = tablePage.getRows(), columns = tablePage.getColumns();

        LAST_PAGE = rows < PAGE_SIZE;

        for (int row = 1; row <= rows; ++row ) {
            var rowData = new Object[columns];
            for (int col = 2; col <= columns; ++col ) {
                rowData[col-2] = tablePage.get(row, col);
            }
            if (filter(rowData)) {
                model.addRow(rowData);
            }
        }
    }

    private static boolean filter(Object[] rowData) {
        if (!nameField.getText().isEmpty() && !Objects.equals(rowData[1].toString(), nameField.getText())) {
            return false;
        }
        if (!phoneField.getText().isEmpty() && !Objects.equals(rowData[2].toString(), phoneField.getText())) {
            System.out.println("тел");
            return false;
        }
        if (!tgField.getText().isEmpty() && !Objects.equals(rowData[3].toString(), tgField.getText())) {
            System.out.println("tg");
            return false;
        }
        if (!emailField.getText().isEmpty() && !Objects.equals(rowData[4].toString(), emailField.getText())) {
            return false;
        }
        return gitField.getText().isEmpty() || Objects.equals(rowData[5].toString(), gitField.getText());
    }

    private static void showForm(Student student, String title, Consumer<Student> onSave) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(569, 320);
        dialog.setLayout(new GridLayout(8, 2));

        JTextField surnameField = new JTextField(student != null ? student.getSurname() : "");
        JTextField nameField = new JTextField(student != null ? student.getName() : "");
        JTextField lastnameField = new JTextField(student != null ? student.getLastname() : "");
        JTextField phoneField = new JTextField(student != null ? student.getPhone() : "");
        JTextField tgField = new JTextField(student != null ? student.getTg() : "");
        JTextField emailField = new JTextField(student != null ? student.getEmail() : "");
        JTextField gitField = new JTextField(student != null ? student.getGit() : "");

        dialog.add(new JLabel("Фамилия"));
        dialog.add(surnameField);

        dialog.add(new JLabel("Имя"));
        dialog.add(nameField);

        dialog.add(new JLabel("Отчество"));
        dialog.add(lastnameField);

        dialog.add(new JLabel("телефон"));
        dialog.add(phoneField);

        dialog.add(new JLabel("email"));
        dialog.add(emailField);

        dialog.add(new JLabel("tg"));
        dialog.add(tgField);

        dialog.add(new JLabel("git"));
        dialog.add(gitField);

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            String surname = surnameField.getText().trim();
            String name = nameField.getText().trim();
            String lastname = lastnameField.getText().trim();

            if (surname.isEmpty() || name.isEmpty() || lastname.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Необходимо заполнить поля: Фамилия, Имя, Отчество", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                var phone = phoneField.getText().trim();
                var tg = tgField.getText().trim();
                var email = emailField.getText().trim();
                var git = gitField.getText().trim();

                Student newStudent;
                if (student != null) {
                    student.setSurname(surname);
                    student.setName(name);
                    student.setLastname(lastname);
                    student.setPhone(phone.isEmpty() ? null : phone);
                    student.setTg(tg.isEmpty() ? null : tg);
                    student.setEmail(email.isEmpty() ? null : email);
                    student.setGit(git.isEmpty() ? null : git);
                    newStudent = student;
                } else {
                    newStudent = new Student(
                        1,
                        surname,
                        name,
                        lastname,
                        phone.isEmpty() ? null : phone,
                        tg.isEmpty() ? null : tg,
                        email.isEmpty() ? null : email,
                        git.isEmpty() ? null : git
                    );
                }
                newStudent.validate();

                onSave.accept(newStudent);
                dialog.dispose();
            } catch (IllegalArgumentException | IllegalStateException exception ) {
                JOptionPane.showMessageDialog(
                        dialog,
                        exception.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
