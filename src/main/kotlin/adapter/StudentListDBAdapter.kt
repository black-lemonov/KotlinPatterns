package adapter

import singleton.DBContext
import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort


class StudentListDBAdapter : StudentList {
    private val context = DBContext

    init {
        context.connect("jdbc:sqlite:./src/main/resources/patterns.db")
    }

    override fun get(id: Int) : Student {
        val res = context.select("*", "student", "id = $id")
        return Student(res)
    }

    override fun getByPage(
        page : Int, number : Int
    ) : DataList<StudentShort> {
        require(page > 0) { "page must be > 0" }
        val result = context.select("*",  "student", number, number * (page-1))
        return DataListStudentShort(
            generateSequence {
                if (result.next()) StudentShort(result) else null
            }.toMutableList()
        )
    }

    override fun add(student: Student) {
        context.insert(
            "student",
            "\"surname\", \"name\", \"lastname\", \"phone\", \"tg\", \"email\", \"git\"",
            "\"${student.surname}\", \"${student.name}\", \"${student.lastname}\", \"${student.phone}\", \"${student.tg}\", \"${student.email}\", \"${student.git}\""
        )
    }

    override fun replaceById(student: Student, id: Int) {
        context.update(
            "student",
            "\"surname\" = \"${student.surname}\", \"name\" = \"${student.name}\", \"lastname\" = \"${student.lastname}\", \"phone\" = \"${student.phone}\", \"tg\" = \"${student.phone}\", \"email\" = \"${student.email}\", \"git\" = \"${student.git}\"",
            "id = $id"
        )
    }

    override fun removeById(id: Int) {
        context.delete(
            "student",
            "\"id\" = $id"
        )
    }

    override fun countAll() : Int {
        val res = context.selectCountAll("student")
        return res
    }
}