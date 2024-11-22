abstract class StudentBase {
    fun getInfo() : String {
        return "${getSurnameAndInitials()};${getGitInfo()};${getContactsInfo()}"
    }

    protected abstract fun getSurnameAndInitials() : String

    protected abstract fun getGitInfo() : String

    protected abstract fun getContactsInfo() : String
}