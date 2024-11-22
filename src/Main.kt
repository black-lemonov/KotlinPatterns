import java.io.File
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.exists

fun main() {

}

fun readFromTxt(filepath : String) : List<Student> {
    val file = File(filepath)
    if (!file.exists()) {
        throw FileNotFoundException("Неверный путь к файлу: $filepath")
    }
    return file.readLines().map {
        Student(it)
    }
}

fun writeToTxt(destpath: String, students: List<Student>) {
    val file = File(destpath)
    file.writeText(
        students.joinToString("\n") { it.toString() }
    )
}