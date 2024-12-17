package adapter

import singleton.StudentDBList
import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort
import java.sql.SQLException


class StudentDBListAdapter : StudentList {
    private val adaptee = StudentDBList()

    override fun get(id: Int) : Student {
        return adaptee.get(id) ?: throw SQLException("No student found with id $id")
    }

    override fun getByPage(page : Int, number : Int) : DataList<StudentShort> {
        return adaptee.getByPage(page, number)
    }

    fun getListByPage(page : Int, number : Int) : List<Student> {
        return adaptee.getListByPage(page, number)
    }

    override fun add(student: Student) {
        adaptee.add(student)
    }

    override fun replaceById(student: Student, id: Int) {
        adaptee.replaceById(student, id)
    }

    override fun removeById(id: Int) {
        adaptee.removeById(id)
    }

    override fun countAll() : Int {
        return adaptee.countAll() ?: 0
    }
}