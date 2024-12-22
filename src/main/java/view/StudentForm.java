package view;

import controllers.StudentFormController;
import student.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentForm {

    StudentFormController controller;

    public void create(Student existingStudent, String title) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(569, 320);
        dialog.setLayout(new GridLayout(7, 2));

        JTextField surnameField = new JTextField(existingStudent != null ? existingStudent.getSurname() : "");
        JTextField nameField = new JTextField(existingStudent != null ? existingStudent.getName() : "");
        JTextField lastnameField = new JTextField(existingStudent != null ? existingStudent.getLastname() : "");
        JTextField tgField = new JTextField(existingStudent != null && existingStudent.getTg() != null ? existingStudent.getTg() : "");
        JTextField gitField = new JTextField(existingStudent != null && existingStudent.getGit() != null ? existingStudent.getGit() : "");
        JTextField emailField = new JTextField(existingStudent != null && existingStudent.getEmail() != null ? existingStudent.getEmail() : "");

        ArrayList<String> accessFields = controller.getAccessFields();
        System.out.println(accessFields.toString());
        dialog.add(new JLabel("Фамилия"));
        dialog.add(surnameField);
        if (!accessFields.contains("Фамилия")) {
            surnameField.setEnabled(false);
        }

        dialog.add(new JLabel("Имя"));
        dialog.add(nameField);
        if (!accessFields.contains("Имя")) {
            nameField.setEnabled(false);
        }

        dialog.add(new JLabel("Отчество"));
        dialog.add(lastnameField);
        if (!accessFields.contains("Отчество")) {
            lastnameField.setEnabled(false);
        }

        dialog.add(new JLabel("tg"));
        dialog.add(tgField);
        if (!accessFields.contains("tg")) {
            tgField.setEnabled(false);
        }

        dialog.add(new JLabel("git"));
        dialog.add(gitField);
        if (!accessFields.contains("git")) {
            gitField.setEnabled(false);
        }

        dialog.add(new JLabel("email"));
        dialog.add(emailField);
        if (!accessFields.contains("email")) {
            emailField.setEnabled(false);
        }

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отменить");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(e -> {

            try {
                Student student = controller.processForm(
                        existingStudent,
                        surnameField.getText().trim(),
                        nameField.getText().trim(),
                        lastnameField.getText().trim(),
                        tgField.getText().trim(),
                        gitField.getText().trim(),
                        emailField.getText().trim()
                );

                String resultMessage = controller.saveProcessedStudent(student, existingStudent != null ? existingStudent.getId() : null);
                JOptionPane.showMessageDialog(dialog, resultMessage);
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
