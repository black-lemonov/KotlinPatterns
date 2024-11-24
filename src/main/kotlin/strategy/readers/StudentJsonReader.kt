package strategy.readers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentJsonReader : StudentFileReader {
    override fun readFromFile(filepath: String): List<Student> {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException("Неверный путь к файлу: $filepath")
        }
        return Json.decodeFromString<MutableList<Student>>(
            file.readText()
        )
    }

    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            Json.encodeToString(students)
        )
    }

}