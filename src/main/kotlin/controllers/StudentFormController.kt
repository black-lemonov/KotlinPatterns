package controllers

import strategy.StudentList
import student.Student

abstract class StudentFormController(
    val studentListController: StudentListController,
    val studentList: StudentList
) {
    constructor(studentListController: StudentListController) : this(
        studentListController,
        studentListController.getStudentsList()
    )

    fun processForm(
        existingStudent: Student?,
        surname: String,
        name: String,
        lastname: String,
        tg: String,
        git: String,
        email: String
    ): Student {
        require(surname.isNotEmpty() || name.isNotEmpty() || lastname.isNotEmpty()) {
            "Фамилия, имя и отчество обязательны для заполнения!"
        }
        val student = if (existingStudent != null) existingStudent else Student()
        student.surname = surname
        student.name = name
        student.lastname = lastname
        student.tg = tg
        student.git = git
        student.email = email
        student.validate()

        return student
    }

    fun getStudentById(id: Int): Student? {
        return studentList.getStudentById(id)
    }

    abstract fun saveProcessedStudent(student: Student, id: Int?): String

    abstract fun getAccessFields(): ArrayList<String>
}