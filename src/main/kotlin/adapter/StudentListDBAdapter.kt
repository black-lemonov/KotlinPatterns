package adapter

import singleton.StudentListDB
import students.Student
import template.DataList


class StudentListDBAdapter : StudentList {
    private val adaptee = StudentListDB()

    override fun get(id: Int) : Student? {
        return adaptee.get(id)
    }

    override fun getByPage(page : Int, number : Int) : DataList<Student> {
        return adaptee.getByPage(page, number)
    }

    override fun add(student: Student) {
        adaptee.add(student)
    }

    override fun update(student: Student, id: Int) {
        adaptee.update(student, id)
    }

    override fun remove(id: Int) {
        adaptee.remove(id)
    }

    override fun countAll() : Int {
        return adaptee.countAll()
    }
}