package view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class App {
    private static final JTextField nameField = new JTextField();
    private static final JComboBox<String> gitComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField gitField = new JTextField();

    private static final JTextField emailField = new JTextField();
    private static final JComboBox<String> emailComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField phoneField = new JTextField();
    private static final JComboBox<String> phoneComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});
    private static final JTextField telegramField = new JTextField();
    private static final JComboBox<String> telegramComboBox = new JComboBox<>(new String[] {"Не важно", "Да", "Нет"});

    public static void create() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(854, 480);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Список студентов", getStudentPanel());
            tabbedPane.add("Вкладка 2", new JLabel("Технические работы"));
            tabbedPane.add("Вкладка 3", new JLabel("Технические работы"));

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }

    private static JPanel getStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        addFilters(panel);

        return panel;
    }

    private static void addFilters(JPanel panel) {
        JPanel filterPanel = new JPanel(new GridLayout(5, 3));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));

        filterPanel.add(new JLabel("Фамилия И.О."));
        filterPanel.add(nameField);
        filterPanel.add(new JLabel());

        filterPanel.add(new JLabel("Телефон"));
        filterPanel.add(phoneComboBox);
        filterPanel.add(phoneField);
        setFilter(phoneComboBox, phoneField);

        filterPanel.add(new JLabel("Tg"));
        filterPanel.add(telegramComboBox);
        filterPanel.add(telegramField);
        setFilter(telegramComboBox, telegramField);

        filterPanel.add(new JLabel("Email"));
        filterPanel.add(emailComboBox);
        filterPanel.add(emailField);
        setFilter(emailComboBox, emailField);

        filterPanel.add(new JLabel("Git"));
        filterPanel.add(gitComboBox);
        filterPanel.add(gitField);
        setFilter(gitComboBox, gitField);

        panel.add(filterPanel, BorderLayout.NORTH);
    }

    private static void setFilter(JComboBox<String> comboBox, JTextField textField) {
        textField.setEnabled(false);
        comboBox.addActionListener(e -> {
            textField.setEnabled(Objects.equals(comboBox.getSelectedItem(), "Да"));
        });
    }

    public static void main(String[] args) {
        App.create();
    }
}