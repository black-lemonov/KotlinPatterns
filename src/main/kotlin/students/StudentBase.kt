package students

abstract class StudentBase {
    abstract val id: Int
    abstract val git: String?

    fun getInfo() : String {
        return "${getSurnameAndInitials()};${git ?: ""};${getContactsInfo()}"
    }

    abstract fun getSurnameAndInitials() : String

    abstract fun getContactsInfo() : String
}