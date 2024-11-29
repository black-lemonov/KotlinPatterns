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
        Student(1u, "Курсед", "Тамара", "Львовна","+79118323322"),
        Student(2u, "Акума", "Сергей", "Петрович"),
        Student(3u, "Степанова", "Вероника", "Петровна","+79829371233")
    )

    fun readFile(filepath: String, fileReader: StudentFileReader) {
        data = fileReader.readFromFile(filepath) as MutableList<Student>
    }

    fun writeToFile(filepath: String, fileWriter: StudentFileWriter) {
        fileWriter.writeToFile(filepath, data)
    }

    fun get(id: UInt) : Student {
        return data.first {it.id == id}
    }
}