package student

import kotlinx.serialization.Serializable

@Serializable
class StudentShort(
    val id: Int,
    val surnameAndInitials: String,
    var git: String? = null,
    val contact: String? = null
): StudentBase(), Comparable<StudentShort> {
    // Конструктор, который принимает объект класса Student
    constructor(student: Student) : this(
        id = student.id,
        info = student.getInfo()
    )

    // Конструктор, который принимает ID и строку с остальной информацией
    constructor(id: Int, info: String) : this(
        id = id,
        surnameAndInitials = info.split(";")[0].trim(),
        git = info.split(";")[1].trim(),
        contact = info.split(";")[2].trim()
    )

    override fun getContactInfo(): String = this.contact ?: "Нет контактной информации"

    override fun getSurnameWithInitials(): String = this.surnameAndInitials

    override fun getGitInfo(): String? = git

    override fun compareTo(other: StudentShort): Int {
        return if (this.surnameAndInitials > other.surnameAndInitials) {
            1
        } else if (this.surnameAndInitials == other.surnameAndInitials) {
            0
        } else {
            -1
        }
    }

    override fun toString(): String = getInfo()
}