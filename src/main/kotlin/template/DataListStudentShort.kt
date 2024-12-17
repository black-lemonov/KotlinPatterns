package template

import students.StudentShort

class DataListStudentShort(
    override val _data: MutableList<StudentShort>,
) : DataList<StudentShort>() {

    override fun getEntityFields(index: Int): List<Any?> {
        val student = _data[index]
        return listOf(
            student.id,
            student.getSurnameAndInitials(),
            student.git,
            student.getContactsInfo()
        )
    }
}