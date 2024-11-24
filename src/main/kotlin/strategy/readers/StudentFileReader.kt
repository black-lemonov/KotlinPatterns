package strategy.readers

import students.Student

interface StudentFileReader {
    fun readFromFile(filepath: String) : List<Student>

    fun writeToFile(filepath: String, students: List<Student>)
}