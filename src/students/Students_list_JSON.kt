package students

import java.io.File
import java.io.FileNotFoundException
import kotlin.math.max

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

import template.Data_list
import template.Data_list_student_short


class Students_list_JSON {
    private var _data : MutableList<Student> = mutableListOf()

    fun read_from_json(filepath : String) {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException("Неверный путь к файлу: $filepath")
        }
        _data = Json.decodeFromString<MutableList<Student>>(
            file.readText()
        )
    }

    fun write_to_json(destpath: String) {
        val file = File(destpath)
        file.writeText(
            Json.encodeToString(_data)
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

    fun add(newStudent: Student) {
        val maxId = _data.mapNotNull { it.getId() }.max()
        add(newStudent, maxId + 1u)
    }

    private fun add(newStudent: Student, id: UInt) {
        newStudent.setId(id)
        _data.add(newStudent)
    }

    fun replaceWhere(newStudent: Student, id: UInt) {
        deleteWhere(id)
        add(newStudent, id)
    }

    fun deleteWhere(id: UInt) {
        _data.removeIf { it.getId() == id }
    }

    fun get_student_short_count() : Int {
        return _data.size
    }
}