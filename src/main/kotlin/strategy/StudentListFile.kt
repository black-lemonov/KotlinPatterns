package strategy

import adapter.StudentList
import strategy.readers.StudentFileReader
import strategy.writers.StudentFileWriter
import students.Student
import template.DataList
import template.DataListStudent
import kotlin.math.max


open class StudentListFile : StudentList {
    private var data : MutableList<Student> = mutableListOf()

    fun readFile(filepath: String, fileReader: StudentFileReader) {
        data = fileReader.readFromFile(filepath) as MutableList<Student>
    }

    fun writeToFile(filepath: String, fileWriter: StudentFileWriter) {
        fileWriter.writeToFile(filepath, data)
    }

    override fun get(id: Int) : Student {
        return data.first {it.id == id}
    }

    override fun getByPage(
        page : Int, number : Int
    ) : DataList<Student> {
        val prevPage = max(page - 1, 0)

        if (prevPage * number <= data.size) {
            return DataListStudent(mutableListOf())
        }

        return DataListStudent(
            data
                .drop(prevPage * number)
                .take(number)
                .toMutableList()
        )
    }

    fun orderBySurnameAndInitials() {
        data.sort()
    }

    override fun add(student: Student) {
        val nextId = (data.maxByOrNull { it.id }?.id ?: 0) + 1
        student.id = nextId

        data.addLast(student)
    }

    override fun replaceById(student: Student, id: Int) {
        student.id = id
        data.replaceAll { if (it.id == id) student else it }
    }

    override fun removeById(id: Int) {
        data.removeIf { it.id == id }
    }

    override fun countAll(): Int = data.count()
}