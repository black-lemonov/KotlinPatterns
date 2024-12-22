package adapter

import filters.StudentFilter
import template.student.DataListStudentShort
import student.Student

interface StudentListInterface {

    fun getStudentById(id: Int): Student?

    fun getKNStudentShortList(k: Int, n: Int): DataListStudentShort

    fun addStudent(student: Student): Int

    fun initStudentFilter(studentFilter: StudentFilter)

    fun updateStudent(student: Student): Boolean

    fun deleteStudent(id: Int): Boolean

    fun getStudentCount(): Int
}