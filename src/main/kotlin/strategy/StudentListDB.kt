package strategy

import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort
import java.sql.DriverManager


class StudentListDB {
    private val con = DBConnection("jdbc:sqlite:./src/main/resources/patterns.db")
//    private val con = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/patterns.db")

    fun get(id: Int) : Student {
        val res = con.select("*", "student", "id = $id")
        return Student(res)
    }

    fun getByPage(
        page : Int, number : Int
    ) : DataList<StudentShort> {
        require(page > 0) { "page must be > 0" }
        val result = con.select("*",  "student", number, number * (page-1))
        return  DataListStudentShort(
            generateSequence {
                if (result.next()) StudentShort(result) else null
            }.toMutableList()
        )
    }

    fun add(student: Student) {
        con.insert(
            "student",
            "\"surname\", \"name\", \"lastname\", \"phone\", \"tg\", \"email\", \"git\"",
            "\"${student.surname}\", \"${student.name}\", \"${student.lastname}\", \"${student.phone}\", \"${student.tg}\", \"${student.email}\", \"${student.git}\""
        )
    }

    fun replaceById(student: Student, id: Int) {
        con.update(
            "student",
            "\"surname\" = \"${student.surname}\", \"name\" = \"${student.name}\", \"lastname\" = \"${student.lastname}\", \"phone\" = \"${student.phone}\", \"tg\" = \"${student.phone}\", \"email\" = \"${student.email}\", \"git\" = \"${student.git}\"",
            "id = $id"
        )
    }

    fun removeById(id: Int) {
        con.delete(
            "student",
            "\"id\" = $id"
        )
    }

    fun countAll() : Int {
        return con.selectCountAll("student")
    }
}


