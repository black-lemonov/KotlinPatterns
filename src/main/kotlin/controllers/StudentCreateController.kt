package controllers

import strategy.StudentList
import student.Student

class StudentCreateController(
    studentListController: StudentListController,
    studentList: StudentList
) : StudentFormController(studentListController,studentList) {

    constructor(studentListController: StudentListController): this(studentListController, studentListController.getStudentsList())

    override fun saveProcessedStudent(student: Student, id: Int?): String {
        val id = studentList.addStudent(student)
        if (id > 0) {
            studentListController.refreshData()
            return "Студент добавлен!"
        } else {
            return "Ошибка при добавлении студента."
        }
    }

    override fun getAccessFields(): ArrayList<String> {
        return arrayListOf("Фамилия", "Имя", "Отчество", "Telegram", "GitHub", "Email")
    }
}