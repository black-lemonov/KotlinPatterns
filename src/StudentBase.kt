abstract class StudentBase {
    fun getInfo() : String {
        return "${getId()}${getSurnameAndInitials()};${getGitInfo()};${getContactsInfo()}"
    }

    abstract fun getId() : UInt?

    abstract fun getSurnameAndInitials() : String

    abstract fun getGitInfo() : String

    abstract fun getContactsInfo() : String
}