package strategy.readers

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

}