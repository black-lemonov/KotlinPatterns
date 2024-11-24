package strategy.readers

import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentTxtReader : StudentFileReader {
    override fun readFromFile(filepath: String): List<Student> {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException("Неверный путь к файлу: $filepath")
        }
        return file.readLines().map { Student(it) }
    }

    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            students.joinToString("\n") { it.toString() }
        )
    }
}