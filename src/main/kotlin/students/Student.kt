package students

import kotlinx.serialization.Serializable
import java.sql.ResultSet


@Serializable
class Student(
    override var id: Int,
    var surname: String,
    var name : String,
    var lastname : String,
    var phone : String? = null,
    var tg : String? = null,
    var email : String? = null,
    override var git : String? = null
) : StudentBase(), Comparable<Student> {

    init {
        checkSurname(surname)
        checkName(name)
        checkLastname(lastname)
        checkPhone(phone)
        checkTg(tg)
        checkEmail(email)
        checkGit(git)
    }

    constructor(
        params: Map<String, Any?>
    ) : this(
        params["id"].toString().toInt(),
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

    constructor(
        rs: ResultSet
    ) : this(
        id = rs.getInt("id"),
        surname = rs.getString("surname"),
        name = rs.getString("name"),
        lastname = rs.getString("lastname"),
        phone = rs.getString("phone"),
        tg = rs.getString("tg"),
        email = rs.getString("email"),
        git = rs.getString("git")
    )

    override fun toString(): String {
        return "$id,$surname,$name,$lastname,${phone ?: ""},${tg ?: ""},${email ?: ""},${git ?: ""}"
    }

    override fun getSurnameAndInitials() : String {
        return "$surname ${name.uppercase().first()}.${surname.uppercase().first()}."
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
        phone: String? = null,
        tg: String? = null,
        email: String? = null
    ) {
        if (phone != null) {
            checkPhone(phone)
            this.phone = phone
        }
        if (tg != null) {
            checkTg(tg)
            this.tg = tg
        }
        if (email != null) {
            checkEmail(email)
            this.email = email
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
