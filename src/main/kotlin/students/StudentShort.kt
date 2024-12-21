package students

import kotlinx.serialization.Serializable

@Serializable
class StudentShort(
    val id: Int,
    val surnameAndInitials: String,
    var git: String? = null,
    val contact: String? = null
): StudentBase(), Comparable<StudentShort> {
    constructor(student: Student) : this(
        id = student.id,
        info = student.getInfo()
    )

    constructor(id: Int, info: String) : this(
        id = id,
        surnameAndInitials = info.split(";")[0].trim(),
        git = info.split(";")[1].trim(),
        contact = info.split(";")[2].trim()
    )

    override fun getContactsInfo(): String = this.contact ?: "Нет контактной информации"

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