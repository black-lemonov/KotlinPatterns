package adapter

import students.Student
import students.StudentShort
import template.DataList


interface StudentList {
    fun get(id: Int) : Student

    fun getByPage(page : Int, number : Int) : DataList<StudentShort>

    fun add(student: Student)

    fun replaceById(student: Student, id: Int)

    fun removeById(id: Int)

    fun countAll(): Int
}