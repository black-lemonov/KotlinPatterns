package student

import kotlinx.serialization.Serializable

/**
 * Базовый класс для студентов
 */
@Serializable
abstract class StudentBase() {

    /**
     * Получить контактную информацию о пользователе
     */
    abstract fun getContactInfo(): String

    /**
     * Получить фамилию с инициалами
     */
    abstract fun getSurnameWithInitials(): String

    abstract fun getGitInfo(): String?

    /**
     * Метод для получения краткой информации о студенте
     */
    fun getInfo(): String {
        val surnameAndInitials = getSurnameWithInitials()
        val contactInfo = getContactInfo()
        val git = getGitInfo()
        return "$surnameAndInitials; $git; $contactInfo"
    }
}