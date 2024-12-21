package adapter

import students.Student
import template.DataList


interface StudentList {
    fun get(id: Int) : Student?

    fun getByPage(page : Int, number : Int) : DataList<Student>

    fun add(student: Student)

    fun update(student: Student, id: Int)

    fun remove(id: Int)

    fun countAll(): Int
}