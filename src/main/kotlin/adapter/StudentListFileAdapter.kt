package adapter

import filters.StudentFilter
import template.DataListStudentShort
import strategy.StudentListFile
import student.Student

class StudentListFileAdapter(
    private val studentListFile: StudentListFile,
    var studentFilter: StudentFilter? = null
) : StudentListInterface {

    override fun getStudentById(id: Int): Student? {
        return try {
            studentListFile.findById(id)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    override fun getKNStudentShortList(k: Int, n: Int): DataListStudentShort {
        studentListFile.studentFilter = this.studentFilter
        return studentListFile.getKNStudentShortList(n=n,k=k) as DataListStudentShort
    }

    override fun addStudent(student: Student): Int {
        studentListFile.add(student)
        return student.id
    }

    override fun initStudentFilter(studentFilter: StudentFilter) {
        this.studentFilter = studentFilter
    }

    override fun updateStudent(student: Student): Boolean {
        getStudentById(student.id) ?: return false
        studentListFile.replaceById(student, student.id)
        return true
    }

    override fun deleteStudent(id: Int): Boolean {
        getStudentById(id) ?: return false
        studentListFile.removeById(id)
        return true
    }

    override fun getStudentCount(): Int {
        return studentListFile.getStudentShortСount()
    }
}