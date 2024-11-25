package students

abstract class StudentBase {
    fun getInfo() : String {
        return "${getSurnameAndInitials()};${getGitInfo()};${getContactsInfo()}"
    }

    abstract fun getId() : UInt?

    abstract fun setId(newId : UInt?)

    abstract fun getSurnameAndInitials() : String

    abstract fun getGitInfo() : String

    abstract fun getContactsInfo() : String
}