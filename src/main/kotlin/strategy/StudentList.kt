package adapter

import filters.StudentFilter
import students.Student
import students.StudentShort
import template.DataList

interface StudentList {
    var filter: StudentFilter?

    fun setFilter(filter: StudentFilter?) {
        this.filter = filter
    }

    fun get(id: Int) : Student?

    fun getByPage(page : Int, pageSize : Int) : DataList<StudentShort>

    fun add(student: Student)

    fun update(student: Student, id: Int)

    fun remove(id: Int)

    fun countAll(): Int
}