package singleton

import strategy.StudentList
import filters.StudentFilter
import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort


class StudentListDB(override var filter: StudentFilter?) : StudentList {
    private val context = DBContext

    init {
        context.connect("jdbc:sqlite:./src/main/resources/patterns.db")
    }

    override fun get(id: Int) : Student? {
        val res = context.select("*", "student", "id = $id")
        return if (res != null) Student(res) else null
    }

    override fun getByPage(
        page : Int, pageSize : Int
    ) : DataList<StudentShort> {
        require(page > 0) { "page must be > 0" }
        val result = context.select("*",  "student", pageSize, pageSize * (page-1))
        if (result != null) {
            return DataListStudentShort(
                generateSequence {
                    if (result.next()) StudentShort(Student(result)) else null
                }.toMutableList()
            )
        }
        return DataListStudentShort(mutableListOf())
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

