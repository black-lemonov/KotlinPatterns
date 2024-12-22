package student

import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
class Student(
    var id: Int = 0,
    var surname: String,
    var name: String,
    var lastname: String,
    var tg: String? = null,
    var git: String? = null
): StudentBase() {

    var phone: String? = null
        get() {
            return field
        }
        set(value) {
            validatePhone(value)
            field = value
        }

    var email: String? = null
        get() {
            return field
        }
        set(value) {
            validateEmail(value)
            field = value
        }

    companion object {
        private val PHONE_REGEX = Regex("(?:\\+|\\d)[\\d\\-\\(\\) ]{9,}\\d")
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

        /**
         * Проверить верность номера
         */
        fun validatePhone(phoneNumber: String?) {
            check(phoneNumber == null || phoneNumber == "" || PHONE_REGEX.matches(phoneNumber)) {
                "Invalid phone number format: $phoneNumber"
            }
        }

        /**
         * Проверить верность почты
         */
        fun validateEmail(email: String?) {
            check(email == null || email == "" || EMAIL_REGEX.matches(email)) {
                "Invalid email format: $email"
            }
        }
    }

    /**
     * Конструктор с номером телефона
     */
    constructor(
        id: Int = 0,
        surname: String,
        name: String,
        lastname: String,
        phone: String? = null,
        tg: String? = null,
        email: String? = null,
        git: String? = null
    ) : this(id, surname, name, lastname, tg, git) {
        this.email = email
        this.phone = phone
    }

    constructor() : this(0, "", "", "")

    constructor(
        info: Map<String, Any?>
    ) : this(
        info.getOrDefault("id", 0) as Int,
        info["surname"].toString(),
        info["name"].toString(),
        info["lastname"].toString(),
        info.getOrDefault("phone", null) as String?,
        info.getOrDefault("tg", null) as String?,
        info.getOrDefault("email", null) as String?,
        info.getOrDefault("git", null) as String?
    )

    constructor(
        rs: ResultSet
    ) : this(
        id = rs.getInt("id").toInt(),
        surname = rs.getString("surname").toString(),
        name = rs.getString("name").toString(),
        lastname = rs.getString("lastname").toString(),
        phone = rs.getString("phone")?.toString() ?: "",
        tg = rs.getString("tg")?.toString() ?: "",
        email = rs.getString("email")?.toString() ?: "",
        git = rs.getString("git")?.toString() ?: ""
    )

    constructor(
        serializedString: String
    ) : this(0, "", "", "") {
        val regex =
            Regex("Student\\(id=([^,]+), name=([^,]+), surname=([^,]+), lastname=([^,]+), phone=([^,]+), tg=([^,]+), email=([^,]+), git=([^)]*)\\)")
        val matchResult = regex.find(serializedString)

        if (matchResult != null) {
            this.id = matchResult.groups[1]?.value?.toInt() ?: 0
            this.name = matchResult.groups[2]?.value ?: ""
            this.surname = matchResult.groups[3]?.value ?: ""
            this.lastname = matchResult.groups[4]?.value ?: ""
            this.phone = matchResult.groups[5]?.value.let { if (it == null || it == "null") null else it }
            this.tg = matchResult.groups[6]?.value.let { if (it == null || it == "null") null else it }
            this.email = matchResult.groups[7]?.value.let { if (it == null || it == "null") null else it }
            this.git = matchResult.groups[8]?.value.let { if (it == null || it == "null") null else it }

            check(name.isNotEmpty()) {
                "Invalid student string format: name is empty!"
            }
            check(surname.isNotEmpty()) {
                "Invalid student string format: surname is empty!"
            }
            check(lastname.isNotEmpty()) {
                "Invalid student string format: lastname is empty!"
            }

            check(validate()) {
                "Invalid student string format: git or some contact is empty"
            }
        } else {
            throw IllegalStateException("Invalid student string format: $serializedString")
        }
    }

    override fun toString(): String {
        return "Student(id=$id, name=$name, surname=$surname, lastname=$lastname, phone=$phone, tg=$tg, email=$email, git=$git)"
    }

    /**
     * Провести валидацию наличия гита и одного из контактов
     */
    fun validate(): Boolean {
        return this.git?.isNotEmpty() ?: false &&
                (
                        this.email?.isNotEmpty() ?: false ||
                                this.tg?.isNotEmpty() ?: false ||
                                this.phone?.isNotEmpty() ?: false
                        )
    }

    fun transformEmptyStringsToNull() {
        if (this.email == "") {
            this.email = null;
        }
        if (this.tg == "") {
            this.tg = null;
        }
        if (this.phone == "") {
            this.phone = null;
        }
        if (this.git == "") {
            this.git = null;
        }
    }

    /**
     * Установить контакты
     */
    fun setСontacts(email: String?, tg: String?, phone: String?) {

        if (email != null) {
            this.email = email;
        }

        if (tg != null) {
            this.tg = tg;
        }

        if (phone != null) {
            this.phone = phone;
        }
    }

    /**
     * Метод для получения информации о способе связи
     */
    override fun getContactInfo(): String {
        val telegramContact = if (tg != null) "tg: $tg;" else ""
        val phoneContact = if (phone != null) "тел: $phone;" else ""
        val emailContact = if (email != null) "email: $email;" else ""

        return listOf(telegramContact, phoneContact, emailContact).first { it.isNotEmpty() }
    }

    override fun getSurnameWithInitials(): String = "$surname ${name.first()}.${lastname.first()}."

    fun getFullName(): String = "$surname $name $lastname"

    override fun getGitInfo(): String? = git
}
