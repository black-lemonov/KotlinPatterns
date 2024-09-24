package lab1

class Student(
    id: UInt,
    surname: String
) {
    private val _id: UInt = id
    private var _surname: String = surname
    private var _name: String? = name
    private var _patronymic: String? = patronymic
    private var _phone: String? = phone
    private var _tg: String? = tg
    private var _email: String? = email
    private var _giturl: String? = giturl

    constructor(
        id: UInt,
        surname: String,
        name: String? = null,
        patronymic: String? = null,
        phone: String? = null,
        tg: String? = null,
        email: String? = null,
        giturl: String? = null
    ) : this(id, surname) {
        this.name = name
        this.patronymic = patronymic
        this.phone = phone
        this.tg = tg
        this.email = email
        this.giturl = giturl
    }

    constructor(
        params: Map<String, Any?>
    ) : this(
        params["id"] as UInt,
        params["surname"] as String,
        params["name"] as? String,
        params["patronymic"] as? String,
        params["phone"] as? String,
        params["tg"] as? String,
        params["email"] as? String,
        params["giturl"] as? String
    )

    var surname: String
        get() = _surname
        set(value) {
            checkName(value)
            _surname = value
        }

    var name: String?
        get() = _name
        set(value) {
            checkName(value)
            _name = value
        }

    var patronymic: String?
        get() = _patronymic
        set(value) {
            checkName(value)
            _patronymic = value
        }

    var phone: String?
        get() = _phone
        set(value) {
            checkPhone(value)
            _phone = value
        }

    var tg: String?
        get() = _tg
        set(value) {
            checkTg(value)
            _tg = value
        }

    var email: String?
        get() = _email
        set(value) {
            checkEmail(value)
            _email = value
        }

    var giturl: String?
        get() = _giturl
        set(value) {
            checkGit(value)
            _giturl = value
        }

    override fun toString(): String {
        val fields = listOf(_id, _surname, _name, _patronymic, _phone, _tg, _email, _giturl)
        return fields.joinToString(" ") { f -> f?.toString() ?: "" }
    }

    companion object {
        private val nameRegex = Regex("^[А-Яа-яA-Za-z]+$")
        private val tgRegex = Regex("^\\w+$")
        private val emailRegex = Regex("^\\w+@\\w+\\.\\w+$")
        private val gitRegex = Regex("^(https://) | (www\\.) git (hub) | (lab) \\.com/\\w+/?$")
        private val phoneRegex = Regex("^\\+?\\d{11}$")

        private val checkName = {
            name: String? -> if (name != null) check(
                nameRegex.matches(name)
            ) {
                "Ошибка! Имя содержит недопустимые символы."
            }
        }

        private val checkTg = {
            tg: String? -> if (tg != null) check(
                tgRegex.matches(tg)
            ) {
                "Ошибка! Неверно указан телеграм студента."
            }
        }


        private val checkEmail = {
            email: String? -> if (email != null) check(
                emailRegex.matches(email)
            ) {
                "Ошибка! Неверно указана почта студента."
            }
        }

        private val checkGit = {
            git: String? -> if (git != null) check(
                gitRegex.matches(git)
            ) {
                "Ошибка! Неверно указан гит студента."
            }
        }

        private val checkPhone = {
            phone: String? -> if (phone != null) check(
                phoneRegex.matches(phone)
            ) {
                "Ошибка! Некорректный номер телефона: $phone."
            }
        }
    }
}