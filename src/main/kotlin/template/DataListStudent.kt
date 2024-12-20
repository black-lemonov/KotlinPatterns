package template

import students.Student

class DataListStudent(
    override val _data: MutableList<Student>,
) : DataList<Student>() {

    override fun getEntityFields(index: Int): List<Any?> {
        val student = _data[index]
        return listOf(
            student.id,
            student.getSurnameAndInitials(),
            student.phone,
            student.tg,
            student.email,
            student.git
        )
    }
}