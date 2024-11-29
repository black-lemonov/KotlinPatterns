package students

abstract class StudentBase {
    abstract val id: UInt
    abstract val git: String?

    fun getInfo() : String {
        return "$id;${getSurnameAndInitials()};${git ?: ""};${getContactsInfo()}"
    }

    abstract fun getSurnameAndInitials() : String

    abstract fun getContactsInfo() : String
}