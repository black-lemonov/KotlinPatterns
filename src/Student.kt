
class Student(
    surname: String,
    name: String,
    lastname: String
) {
    private var _id : UInt? = null
    private var _surname : String
    private var _name : String
    private var _lastname : String
    private var _phone : String? = null
    private var _tg : String? = null
    private var _email : String? = null
    private var _git : String? = null

    init {
        checkSurname(surname)
        checkName(name)
        checkLastname(lastname)
        _surname = surname
        _name = name
        _lastname = lastname
    }

    constructor(
        id : UInt? = null,
        surname : String,
        name : String,
        lastname : String,
        phone : String? = null,
        tg : String? = null,
        email : String? = null,
        git : String? = null
    ) : this(
        surname,
        name,
        lastname
    ) {
        checkPhone(phone)
        checkTg(tg)
        checkEmail(email)
        checkGit(git)
        _id = id
        _surname = surname
        _name = name
        _lastname = lastname
        _phone = phone
        _tg = tg
        _email = email
        _git = git
        validate()
    }

    fun validate() {
        check(
            this.hasGit() && this.hasContact()
        ) {
            "Необходимо указать "
        }
    }

    fun hasGit() : Boolean {
        return git != null
    }

    fun hasContact() : Boolean {
        return listOf(phone, tg, email).any {it != null}
    }

    constructor(
        params: Map<String, Any>
    ) : this(
        params.getOrDefault("id", null) as UInt?,
        params["surname"] as String,
        params["name"] as String,
        params["lastname"] as String,
        params.getOrDefault("phone", null) as String?,
        params.getOrDefault("email", null) as String?,
        params.getOrDefault("git", null) as String?
    )

    fun setContacts(
        phone: String? = null,
        tg: String? = null,
        email: String? = null
    ) {
        if (phone != null) {
            checkPhone(phone)
            _phone = phone
        }
        if (tg != null) {
            checkTg(tg)
            _tg = tg
        }
        if (email != null) {
            checkEmail(email)
            _email = email
        }
    }

    var id : UInt?
        get() = _id
        set(value) {
            _id = value
        }
    var surname : String
        get() = _surname
        set(value) {
            _surname = value
        }
    var name : String
        get() = _name
        set(value) {
            _name = value
        }
    var lastname : String
        get() = _lastname
        set(value) {
            _lastname = value
        }
    var phone : String?
        get() = _phone
        set(value) {
            _phone = value
        }
    var tg : String?
        get() = _tg
        set(value) {
            _tg = value
        }
    var email : String?
        get() = _email
        set(value) {
            _email = value
        }
    var git : String?
        get() = _git
        set(value) {
            _git = value
        }

    companion object {
        val PART_OF_NAME_REGEX = Regex("^[А-Яа-я]$")
        val PHONE_REGEX = Regex("^(\\+7|8)\\s*\\(?\\d{3}\\)?\\s*\\d{3}[- ]?\\d{2}[- ]?\\d{2}\$\n")
        val TG_REGEX = Regex("^(https://t\\.me/[a-zA-Z0-9_]+|tg://resolve\\?domain=[a-zA-Z0-9_]+)$\n")
        val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$\n")
        val GIT_REGEX = Regex("^(https://(www\\.)?(github\\.com|gitlab\\.com)/[a-zA-Z0-9._-]+/[a-zA-Z0-9._-]+)$\n")

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
