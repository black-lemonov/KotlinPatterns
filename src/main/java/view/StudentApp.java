package view;

import filters.StudentFilter;
import enums.SearchParam;
import strategy.StudentListDB;
import student.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.IllegalStateException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class StudentApp {
    private static final int PAGE_SIZE = 20;
    private static int CURRENT_PAGE = 1;

    private static final StudentListDB studentDB = new StudentListDB();

    private static final JTextField nameField = new JTextField();

    private static final JComboBox<String> gitComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField gitField = new JTextField();

    private static final JTextField emailField = new JTextField();
    private static final JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private static final JTextField phoneField = new JTextField();
    private static final JComboBox<String> phoneComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private static final JTextField tgField = new JTextField();
    private static final JComboBox<String> tgComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    private static final JLabel pageInfoLabel = new JLabel("Страница: 1 / ?");
    private static final JButton prevPageButton = new JButton("<");
    private static final JButton nextPageButton = new JButton(">");

    private static final JButton refreshButton = new JButton("Обновить");
    private static final JButton addButton = new JButton("Добавить");
    private static final JButton editButton = new JButton("Изменить");
    private static final JButton deleteButton = new JButton("Удалить");


    public static void create() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", createStudentTab());
            tabbedPane.add("Вкладка 2", new JLabel());
            tabbedPane.add("Вкладка 3", new JLabel());

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }

    private static JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout());

        addFilters(panel);

        String[] columnNames = { "ID", "Фамилия И.О.", "телефон", "tg", "email", "git" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRowCount = table.getSelectedRowCount();
            editButton.setEnabled(selectedRowCount == 1);
            deleteButton.setEnabled(selectedRowCount > 0);
        });

        refreshInfo(tableModel);

        addButton.addActionListener(e -> {
            showStudentForm(null, "Добавить студента", student -> {
                int id = studentDB.addStudent(student);
                if (id > 0) {
                    JOptionPane.showMessageDialog(panel, "Успешно");
                    refreshInfo(tableModel);
                } else {
                    JOptionPane.showMessageDialog(panel, "Ошибка");
                }
            });
        });


        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Student student = studentDB.getStudentById(id);
                if (student != null) {
                    showStudentForm(student, "Изменить студента", updatedStudent -> {
                        if (studentDB.updateStudent(updatedStudent)) {
                            JOptionPane.showMessageDialog(panel, "Успешно");
                            refreshInfo(tableModel);
                        } else {
                            JOptionPane.showMessageDialog(panel, "Ошибка");
                        }
                    });
                }
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
                        if (!studentDB.deleteStudent(id)) {
                            success = false;
                        }
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(panel, "Успешно");
                    } else {
                        JOptionPane.showMessageDialog(panel, "При удалении произошла ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }

                    refreshInfo(tableModel);
                }
            }
        });

        nextPageButton.addActionListener(e -> {
            CURRENT_PAGE++;
            refreshInfo(tableModel);
        });

        prevPageButton.addActionListener(e -> {
            if (CURRENT_PAGE > 1) {
                CURRENT_PAGE--;
                refreshInfo(tableModel);
            }
        });

        refreshButton.addActionListener(e -> refreshInfo(tableModel));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(prevPageButton);
        buttonPanel.add(pageInfoLabel);
        buttonPanel.add(nextPageButton);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private static void addFilters(JPanel panel) {
        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
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


    private static void refreshInfo(DefaultTableModel tableModel) {
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

        int totalItems = studentDB.getFilteredStudentCount(studentFilter);
        int lastPage = calculateLastPage(totalItems);
        if (lastPage < CURRENT_PAGE) {
            CURRENT_PAGE = lastPage;
            refreshInfo(tableModel);
            return;
        }
        loadStudents(tableModel, studentFilter);

        updatePageControls(lastPage);
    }



    private static void loadStudents(
            DefaultTableModel tableModel,
            StudentFilter studentFilter
    ) {
        tableModel.setRowCount(0);

        List<Student> students = studentDB.getFilteredStudentList(
                CURRENT_PAGE, PAGE_SIZE,
                studentFilter
        );

        for (Student student : students) {
            tableModel.addRow(new Object[] {
                    student.getId(),
                    student.getSurnameWithInitials(),
                    student.getGitInfo(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getTg(),
            });
        }
    }

    private static void updatePageControls(int lastPage) {
        pageInfoLabel.setText(String.format("Страница %d / %d ", CURRENT_PAGE, lastPage));

        prevPageButton.setEnabled(CURRENT_PAGE > 1);
        nextPageButton.setEnabled(CURRENT_PAGE < lastPage);
    }


    private static int calculateLastPage(int totalItems) {
        int page = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        return page == 0 ? 1 : page;
    }

    private static void showStudentForm(Student existingStudent, String title, Consumer<Student> onSave) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(569, 320);
        dialog.setLayout(new GridLayout(7, 2));

        JTextField surnameField = new JTextField(existingStudent != null ? existingStudent.getSurname() : "");
        JTextField nameField = new JTextField(existingStudent != null ? existingStudent.getName() : "");
        JTextField lastnameField = new JTextField(existingStudent != null ? existingStudent.getLastname() : "");
        JTextField tgField = new JTextField(existingStudent != null && existingStudent.getTg() != null ? existingStudent.getTg() : "");
        JTextField gitField = new JTextField(existingStudent != null && existingStudent.getGit() != null ? existingStudent.getGit() : "");
        JTextField emailField = new JTextField(existingStudent != null && existingStudent.getEmail() != null ? existingStudent.getEmail() : "");

        dialog.add(new JLabel("Фамилия"));
        dialog.add(surnameField);

        dialog.add(new JLabel("Имя"));
        dialog.add(nameField);

        dialog.add(new JLabel("Отчество"));
        dialog.add(lastnameField);

        dialog.add(new JLabel("tg"));
        dialog.add(tgField);

        dialog.add(new JLabel("git"));
        dialog.add(gitField);

        dialog.add(new JLabel("email"));
        dialog.add(emailField);

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
                Student student = existingStudent != null ? existingStudent : new Student();
                student.setSurname(surname);
                student.setName(name);
                student.setLastname(lastname);
                student.setTg(tgField.getText().trim());
                student.setGit(gitField.getText().trim());
                student.setEmail(emailField.getText().trim());
                student.validate();

                onSave.accept(student);
                dialog.dispose();
            } catch (IllegalStateException exception) {
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
