package singleton

import adapter.StudentList
import students.Student
import template.DataList
import template.DataListStudent


class StudentListDB : StudentList {
    private val context = DBContext

    init {
        context.connect("jdbc:sqlite:./src/main/resources/patterns.db")
    }

    override fun get(id: Int) : Student? {
        val res = context.select("*", "student", "id = $id")
        return if (res != null) Student(res) else null
    }

    override fun getByPage(
        page : Int, number : Int
    ) : DataList<Student> {
        require(page > 0) { "page must be > 0" }
        val result = context.select("*",  "student", number, number * (page-1))
        if (result != null) {
            return DataListStudent(
                generateSequence {
                    if (result.next()) Student(result) else null
                }.toMutableList()
            )
        }
        return DataListStudent(mutableListOf())
    }

    override fun add(student: Student) {
        context.insert(
            "student",
            "\"surname\", \"name\", \"lastname\", \"phone\", \"tg\", \"email\", \"git\"",
            listOf<Any?>(student.surname, student.name, student.lastname, student.phone, student.tg, student.email, student.git)
        )
    }

    override fun update(student: Student, id: Int) {
        context.update(
            "student",
            listOf("surname", "name", "lastname", "phone", "tg", "email", "git"),
            listOf(student.surname, student.name, student.lastname, student.phone, student.tg, student.email, student.git),
            "id = $id"
        )
    }

    override fun remove(id: Int) {
        context.delete(
            "student",
            "\"id\" = $id"
        )
    }

    override fun countAll() : Int {
        val res = context.selectCountAll("student")
        return res ?: 0
    }
}


