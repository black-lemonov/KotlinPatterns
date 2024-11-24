package strategy

import java.io.File
import java.io.FileNotFoundException
import kotlin.math.max
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import kotlinx.serialization.encodeToString
import students.Student
import students.Student_short
import template.Data_list_student_short


class Students_list_YAML {
    private var _data : MutableList<Student> = mutableListOf()

    fun read_from_txt(filepath : String) {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException("Неверный путь к файлу: $filepath")
        }
        _data = Yaml.default.decodeFromStream(
            file.inputStream()
        )
    }

    fun write_to_txt(destpath: String) {
        val file = File(destpath)
        file.writeText(
            Yaml.default.encodeToString(_data)
        )
    }

    fun get(id: UInt) : Student {
        return _data.first {it.getId() == id}
    }

    fun get_k_n_student_short_list(
        page : Int, number : Int
    ) : Data_list {
        val prevPage = max(page-1, 0)
        require(prevPage * number <= _data.size) {
            "Нет страницы с номером $page"
        }

        return Data_list_student_short(_data
            .drop(prevPage * number)
            .take(number)
            .map { Student_short(it) }
        )
    }

    fun orderBySurnameAndInitials() {
        _data.sort()
    }

    fun addStudent(newStudent: Student) {
        val maxId = _data.mapNotNull { it.getId() }.max()
        addStudent(newStudent, maxId + 1u)
    }

    private fun addStudent(newStudent: Student, id: UInt) {
        newStudent.setId(id)
        _data.add(newStudent)
    }

    fun updateStudent(newStudent: Student, id: UInt) {
        deleteStudent(id)
        addStudent(newStudent, id)
    }

    fun deleteStudent(id: UInt) {
        _data.removeIf { it.getId() == id }
    }

    fun get_student_short_count() : Int {
        return _data.size
    }
}