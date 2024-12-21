package view;

import adapter.StudentList;
import singleton.StudentListDB;
import students.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

public class TablePanel extends JPanel {
    private static JTable table;
    private static DefaultTableModel model;
    private static final StudentList studentList = new StudentListDB();

    private static int CURRENT_PAGE = 1;
    private static final int PAGE_SIZE = 10;

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
        filterPanel.add(new JLabel("телефон"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);
        setFilter(phoneComboBox, phoneField);

        JTextField tgField = new JTextField();
        JComboBox<String> tgComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("tg"));
        filterPanel.add(tgComboBox);
        filterPanel.add(tgField);
        setFilter(tgComboBox, tgField);

        JTextField emailField = new JTextField();
        JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
        filterPanel.add(new JLabel("email"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);
        setFilter(emailComboBox, emailField);

        JTextField gitField = new JTextField();
        filterPanel.add(new JLabel("git"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);
        setFilter(gitComboBox, gitField);

        this.add(filterPanel, BorderLayout.NORTH);
    }

    private void addTable() {
        String[] columnNames = { "ID", "Фамилия И.О.", "телефон", "tg", "email", "git" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addButtons() {
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
                JOptionPane.showMessageDialog(this, "Успешно!");
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
                        JOptionPane.showMessageDialog(this, "Успешно!");
                        refreshModel();
                    });
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            if (selectedRows.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Удалить выбранных студентов?",
                        "Подтвердите действие",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int id = (int) model.getValueAt(selectedRows[i], 0);
                        studentList.remove(id);
                    }
                    JOptionPane.showMessageDialog(this, "Успешно!");
                    refreshModel();
                }
            }
        });

        var pageLbl = new JLabel(Integer.toString(CURRENT_PAGE));
        nextPageButton.addActionListener(e -> {
            CURRENT_PAGE++;
            pageLbl.setText(Integer.toString(CURRENT_PAGE));
            refreshModel();
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

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private static void refreshModel() {
        model.setRowCount(0);

        var tablePage = studentList.getByPage(CURRENT_PAGE, PAGE_SIZE).getData();
        for (int row = 1; row <= tablePage.getRows(); ++row ) {
            var rowData = new Object[tablePage.getColumns()];
            for (int col = 2; col <= tablePage.getColumns(); ++col ) {
                rowData[col-2] = tablePage.get(row, col);
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
