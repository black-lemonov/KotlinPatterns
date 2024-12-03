package strategy

import strategy.readers.StudentFileReader
import strategy.writers.StudentFileWriter
import students.Student


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
}