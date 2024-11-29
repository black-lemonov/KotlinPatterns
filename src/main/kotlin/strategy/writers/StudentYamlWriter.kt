package strategy.writers

import kotlinx.serialization.encodeToString
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import students.Student
import java.io.File
import java.io.FileNotFoundException

class StudentYamlWriter : StudentFileWriter {
    override fun writeToFile(filepath: String, students: List<Student>) {
        val file = File(filepath)
        file.writeText(
            Yaml.default.encodeToString(students)
        )
    }
}