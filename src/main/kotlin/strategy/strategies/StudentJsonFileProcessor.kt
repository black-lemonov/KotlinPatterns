package strategy.strategies

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import student.Student
import java.io.File

class StudentJsonFileProcessor: StudentFileProcessor {
    override fun readFromFile(filePath: String): MutableList<Student> {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Некорректный адрес файла: $filePath")
        }

        try {
            return Json.decodeFromString<MutableList<Student>>(file.readText()) ?: mutableListOf()
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при чтении файла JSON: ${e.message}")
        }
    }

    override fun writeToFile(students: MutableList<Student>, directory: String, fileName: String) {
        val file = File(directory, fileName)

        try {
            file.parentFile?.mkdirs()
            file.writeText(Json.encodeToString(students))
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при записи в файл JSON: ${e.message}")
        }
    }
}