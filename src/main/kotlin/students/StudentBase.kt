package students

import kotlinx.serialization.Serializable

@Serializable
abstract class StudentBase() {

    abstract fun getContactsInfo(): String

    abstract fun getSurnameWithInitials(): String

    abstract fun getGitInfo(): String?

    fun getInfo(): String {
        val lastNameWithInitials = getSurnameWithInitials()
        val contactInfo = getContactsInfo()
        val git = getGitInfo()
        return "$lastNameWithInitials; $git; $contactInfo"
    }
}