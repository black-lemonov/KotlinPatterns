package strategy.readers

import kotlinx.serialization.encodeToString
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentYamlReader : StudentFileReader {
    override fun readFromFile(filepath: String): List<Student> {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException("Неверный путь к файлу: $filepath")
        }
        return Yaml.default.decodeFromStream(
            file.inputStream()
        )
    }

    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            Yaml.default.encodeToString(students)
        )
    }
}