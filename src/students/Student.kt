package students


class Student(
    surname: String,
    name: String,
    lastname: String
) : StudentBase(), Comparable<Student> {
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

    private fun validate() {
        check(
            this.hasGit() && this.hasContact()
        ) {
            "Необходимо указать гит и один из контактов!"
        }
    }

   private fun hasGit() : Boolean {
        return _git != null
   }

    private fun hasContact() : Boolean {
        return listOf(_phone, _tg, _email).any {it != null}
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
        return listOf(_id, _surname, _name, _lastname, _phone, _tg, _email, _git)
            .joinToString(",") {
                it?.toString() ?: ""
            }
    }

    override fun getId(): UInt? {
        return _id
    }

    override fun setId(id: UInt?) {
        _id = id
    }

    override fun getSurnameAndInitials() : String {
        return "$_surname ${_name.uppercase().first()}.${_surname.uppercase().first()}."
    }

    override fun getGitInfo() : String {
        if (_git != null) {
            return "git: $_git"
        }
        return ""
    }

    override fun getContactsInfo() : String {
        if (_phone != null) {
            return "тел: $_phone"
        }
        if (_tg != null) {
            return "тг: $_tg"
        }
        if (_email != null) {
            return "почта: $_email"
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
