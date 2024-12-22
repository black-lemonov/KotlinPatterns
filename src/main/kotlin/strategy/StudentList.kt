package strategy

import adapter.StudentListInterface
import filters.StudentFilter
import template.DataListStudentShort
import student.Student

class StudentList(private val studentSource: StudentListInterface) {

    var studentFilter: StudentFilter? = null

    fun getStudentById(id: Int): Student? {
        return studentSource.getStudentById(id)
    }

    fun getKNStudentShortList(k: Int, n: Int, studentFilter: StudentFilter? = null): DataListStudentShort {
        this.studentFilter = studentFilter
        if (studentFilter != null) {
            this.studentSource.initStudentFilter(studentFilter)
        }
        return studentSource.getKNStudentShortList(k, n)
    }

    fun addStudent(student: Student): Int {
        return studentSource.addStudent(student)
    }

    fun updateStudent(student: Student): Boolean {
        return studentSource.updateStudent(student)
    }

    fun deleteStudent(id: Int): Boolean {
        return studentSource.deleteStudent(id)
    }

    fun getStudentCount(): Int {
        return studentSource.getStudentCount()
    }
}