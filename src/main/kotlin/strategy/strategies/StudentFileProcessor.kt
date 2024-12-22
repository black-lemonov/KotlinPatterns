package strategy.strategies

import student.Student

/**
 * Интерфейс для классов, предназначенных для реализации работы с разным видом записи и чтения из файлов
 */
interface StudentFileProcessor {
    fun readFromFile(filePath: String): MutableList<Student>

    fun writeToFile(students: MutableList<Student>, directory: String, fileName: String)

}