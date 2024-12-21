package template

import observer.Publisher
import observer.Subscriber
import students.Student
import students.StudentShort


class DataListStudentShort(students: List<StudentShort>) : DataList<StudentShort>(students), Publisher {
    override val subscribers: MutableList<Subscriber> = mutableListOf()

    constructor(fullStudents: List<Student>) : this(students = fullStudents.map { StudentShort(it) })

    override fun getEntityFields(): List<String> {
        return listOf("ID", "Имя", "Гит", "Контакт")
    }

    override fun getDataRow(entity: StudentShort): List<Any> {
        return listOf(entity.id, entity.surnameAndInitials, entity.git, entity.contact) as List<Any>
    }

}