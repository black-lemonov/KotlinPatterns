package strategy

import strategy.readers.StudentFileReader
import strategy.writers.StudentFileWriter
import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort
import kotlin.math.max

open class StudentList() {
    private var data : MutableList<Student> = mutableListOf(
        Student(1, "Курсед", "Тамара", "Львовна","+79118323322"),
        Student(2, "Акума", "Сергей", "Петрович"),
        Student(3, "Степанова", "Вероника", "Петровна","+79829371233")
    )

    fun readFile(filepath: String, fileReader: StudentFileReader) {
        data = fileReader.readFromFile(filepath) as MutableList<Student>
    }

    fun writeToFile(filepath: String, fileWriter: StudentFileWriter) {
        fileWriter.writeToFile(filepath, data)
    }

    fun get(id: Int) : Student {
        return data.first {it.id == id}
    }

    fun getByPage(
        page : Int, number : Int
    ) : DataList<StudentShort> {
        val prevPage = max(page - 1, 0)
        require(prevPage * number <= data.size) {
            "Нет страницы с номером $page"
        }

        return DataListStudentShort(
            data
                .drop(prevPage * number)
                .take(number)
                .map { StudentShort(it) }
                .toMutableList()
        )
    }

    fun orderBySurnameAndInitials() {
        data.sort()
    }

    fun add(student: Student) {
        val nextId = (data.maxByOrNull { it.id }?.id ?: 0) + 1
        student.id = nextId

        data.addLast(student)
    }

    fun replaceById(student: Student, id: Int) {
        student.id = id
        data.replaceAll { if (it.id == id) student else it }
    }

    fun removeById(id: Int) {
        data.removeIf { it.id == id }
    }

    fun countAll(): Int = data.count()
}