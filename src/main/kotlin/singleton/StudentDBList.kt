package singleton

import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort


class StudentDBList {
    private val context = DBContext

    init {
        context.connect("jdbc:sqlite:./src/main/resources/patterns.db")
    }

    fun get(id: Int) : Student? {
        val res = context.select("*", "student", "id = $id")
        return if (res != null) Student(res) else null
    }

    fun getByPage(
        page : Int, number : Int
    ) : DataList<StudentShort> {
        require(page > 0) { "page must be > 0" }
        val result = context.select("*",  "student", number, number * (page-1))
        if (result != null) {
            return DataListStudentShort(
                generateSequence {
                    if (result.next()) StudentShort(result) else null
                }.toMutableList()
            )
        }
        return DataListStudentShort(mutableListOf())
    }

    fun add(student: Student) {
        context.insert(
            "student",
            "\"surname\", \"name\", \"lastname\", \"phone\", \"tg\", \"email\", \"git\"",
            listOf<Any?>(student.surname, student.name, student.lastname, student.phone, student.tg, student.email, student.git)
        )
    }

    fun replaceById(student: Student, id: Int) {
        context.update(
            "student",
            listOf("surname", "name", "lastname", "phone", "tg", "email", "git"),
            listOf(student.surname, student.name, student.lastname, student.phone, student.tg, student.email, student.git),
            "id = $id"
        )
    }

    fun removeById(id: Int) {
        context.delete(
            "student",
            "\"id\" = $id"
        )
    }

    fun countAll() : Int? {
        val res = context.selectCountAll("student")
        return res
    }
}


