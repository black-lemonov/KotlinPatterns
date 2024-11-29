package strategy.writers

import students.Student

interface StudentFileWriter {
    fun writeToFile(filepath: String, students: List<Student>)
}