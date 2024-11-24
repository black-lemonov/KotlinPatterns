package strategy

import students.Student
import students.Student_short
import strategy.readers.StudentFileReader
import strategy.readers.StudentTxtReader
import template.Data_list
import template.Data_list_student_short
import kotlin.math.max

open class Student_list(
    private var _data : MutableList<Student> = mutableListOf(),
    var fileReader : StudentFileReader = StudentTxtReader()
) {

    fun readFile(filepath: String) {
        _data = fileReader.readFromFile(filepath) as MutableList<Student>
    }

    fun writeToFile(filepath: String) {
        fileReader.writeToFile(filepath, _data)
    }

    fun get(id: UInt) : Student {
        return _data.first {it.getId() == id}
    }

    fun getByPage(
        page : Int, number : Int
    ) : Data_list {
        val prevPage = max(page - 1, 0)
        require(prevPage * number <= _data.size) {
            "Нет страницы с номером $page"
        }

        return Data_list_student_short(
            _data
                .drop(prevPage * number)
                .take(number)
                .map { Student_short(it) }
        )
    }

    fun orderBySurnameAndInitials() {
        _data.sort()
    }

    fun addStudent(newStudent: Student) {
        val maxId = _data.mapNotNull { it.getId() }.max()
        addStudent(newStudent, maxId + 1u)
    }

    private fun addStudent(newStudent: Student, id: UInt) {
        newStudent.setId(id)
        _data.add(newStudent)
    }

    fun updateStudent(newStudent: Student, id: UInt) {
        deleteStudent(id)
        addStudent(newStudent, id)
    }

    fun deleteStudent(id: UInt) {
        _data.removeIf { it.getId() == id }
    }

    fun countAll() : Int {
        return _data.size
    }
}