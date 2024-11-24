package template

import students.Student_short
import strategy.Data_list

class Data_list_student_short(
    data : List<Student_short>
) : Data_list() {
    private val _data : MutableList<Student_short> = data as MutableList
    private val _selected : MutableList<Int> = mutableListOf()

    override fun select(index: Int) {
        require(index > 0 && index <= _data.size) {
            "Нет такого номера!"
        }
        _selected.add(index)
    }

    override fun get_selected(): List<Int> {
        return _selected as List<Int>
    }

    override fun getId(index: Int): Any {
        return _data[index].getId() as Any
    }

    override fun getRest(index: Int): List<Any> {
        val student = _data[index]
        return listOf(
            student.getSurnameAndInitials(),
            student.getGitInfo(),
            student.getContactsInfo()
        )
    }

}