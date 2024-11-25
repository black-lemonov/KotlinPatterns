package students

import kotlinx.serialization.Serializable

@Serializable
class Student(
    var id: UInt? = null,
    var surname: String,
    var name : String,
    var lastname : String,
    var phone : String? = null,
    var tg : String? = null,
    var email : String? = null,
    var git : String? = null
) : StudentBase(), Comparable<Student> {

    init {
        checkSurname(surname)
        checkName(name)
        checkLastname(lastname)
        checkPhone(phone)
        checkTg(tg)
        checkEmail(email)
        checkGit(git)
        validate()
    }

    private fun validate() {
        check(
            this.hasGit() && this.hasContact()
        ) {
            "Необходимо указать гит и один из контактов!"
        }
    }

   private fun hasGit() : Boolean {
        return git != null
   }

    private fun hasContact() : Boolean {
        return listOf(phone, tg, email).any {it != null}
    }

    constructor(
        params: Map<String, Any?>
    ) : this(
        params.getOrDefault("id", null) as? UInt,
        params["surname"] as String,
        params["name"] as String,
        params["lastname"] as String,
        params.getOrDefault("phone", null) as? String,
        params.getOrDefault("tg", null) as? String,
        params.getOrDefault("email", null) as? String,
        params.getOrDefault("git", null) as? String
    )

    constructor(
        params: String
    ) : this(
        listOf("id", "surname", "name", "lastname", "phone", "tg", "email", "git")
            .zip(
                params.split(',').map {it.ifEmpty {null} }
            )
            .toMap()
    )

    override fun toString(): String {
        return listOf(id, surname, name, lastname, phone, tg, email, git)
            .joinToString(",") {
                it?.toString() ?: ""
            }
    }

    override fun getId(): UInt? {
        return id
    }

    override fun setId(newId: UInt?) {
        id = newId
    }

    override fun getSurnameAndInitials() : String {
        return "$surname ${name.uppercase().first()}.${surname.uppercase().first()}."
    }

    override fun getGitInfo() : String {
        if (git != null) {
            return "git: $git"
        }
        return ""
    }

    override fun getContactsInfo() : String {
        if (phone != null) {
            return "тел: $phone"
        }
        if (tg != null) {
            return "тг: $tg"
        }
        if (email != null) {
            return "почта: $email"
        }
        return ""
    }

    fun setContacts(
        phone_: String? = null,
        tg_: String? = null,
        email_: String? = null
    ) {
        if (phone_ != null) {
            checkPhone(phone_)
            phone = phone_
        }
        if (tg_ != null) {
            checkTg(tg_)
            tg = tg_
        }
        if (email_ != null) {
            checkEmail(email_)
            email = email_
        }
    }

    override fun compareTo(other: Student): Int {
        return if (this.getSurnameAndInitials() > other.getSurnameAndInitials()) {
            1
        } else if (this.getSurnameAndInitials() == other.getSurnameAndInitials()) {
            0
        } else {
            -1
        }
    }

    companion object {
        val PART_OF_NAME_REGEX = Regex("^[А-Яа-яA-Za-z]+$")
        val PHONE_REGEX = Regex("^(\\+7|8)\\s*\\(?\\d{3}\\)?\\s*\\d{3}[- ]?\\d{2}[- ]?\\d{2}\$")
        val TG_REGEX = Regex("^(https://t\\.me/[a-zA-Z0-9_]+|tg://resolve\\?domain=[a-zA-Z0-9_]+)$")
        val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        val GIT_REGEX = Regex("^(https://(www\\.)?(github\\.com|gitlab\\.com)/[a-zA-Z0-9._-]+/[a-zA-Z0-9._-]+)$")

        fun checkSurname(surname: String) {
            check(PART_OF_NAME_REGEX.matches(surname)) {
                "Фамилия может содержать только буквы"
            }
        }
        fun checkName(name: String) {
            check(PART_OF_NAME_REGEX.matches(name)) {
                "Имя может содержать только буквы"
            }
        }
        fun checkLastname(lastname: String) {
            check(PART_OF_NAME_REGEX.matches(lastname)) {
                "Отчество может содержать только буквы"
            }
        }
        fun checkPhone(phone: String?) {
            check(
                phone == null || PHONE_REGEX.matches(phone)
            ) {
                "Неправильный формат телефона"
            }
        }
        fun checkTg(tg: String?) {
            check(
                tg == null || TG_REGEX.matches(tg)
            ) {
                "Неправильная ссылка на телеграм"
            }
        }
        fun checkEmail(email: String?) {
            check(
                email == null || EMAIL_REGEX.matches(email)
            ) {
                "Неправильно задана почта"
            }
        }
        fun checkGit(git: String?) {
            check(
                git == null || GIT_REGEX.matches(git)
            ) {
                "Неправильно задан Git"
            }
        }
    }
}
