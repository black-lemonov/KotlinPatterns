package strategy.writers

import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentTxtWriter : StudentFileWriter {
    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            students.joinToString("\n") { it.toString() }
        )
    }
}