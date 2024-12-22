package template

import observer.Publisher
import observer.Subscriber
import student.Student
import student.StudentShort

class DataListStudentShort(students: List<StudentShort>) : DataList<StudentShort>(students), Publisher {

    constructor(studentsList: List<Student>, count: Int) : this(studentsList.map { StudentShort(it) })

    override val subscribers: MutableList<Subscriber> = mutableListOf()

    override fun getEntityFields(): List<String> {
        return listOf("ID", "Фамилия И.О.", "git", "контакт")
    }

    override fun getDataRow(entity: StudentShort): List<Any> {
        return listOf(entity.id, entity.surnameAndInitials, entity.git, entity.contact) as List<Any>
    }

}