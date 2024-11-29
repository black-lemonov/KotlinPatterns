package strategy.writers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentJsonWriter : StudentFileWriter {
    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            Json.encodeToString(students)
        )
    }
}