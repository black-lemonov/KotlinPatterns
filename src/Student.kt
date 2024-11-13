
open class Student {
    private val _id: UInt
    private var _surname: String
    private var _name: String
    private var _git: String

    constructor(id: UInt, surname: String, name: String, git: String) {
        _id = id
        if (!checkName(surname)) {
            throw IllegalArgumentException(
                "Ошибка при создании: фамилия должно содержать только буквы"
            )
        }
        _surname = surname
        if (!checkName(name)) {
            throw IllegalArgumentException(
                "Ошибка при создании: имя должно содержать только буквы"
            )
        }
        _name = name
        if (!checkGit(git)) {
            throw IllegalArgumentException(
                "Ошибка при создании: git задан неправильно"
            )
        }
        _git = git
    }

    private var _patronymic: String? = null
    private var _phone: String? = null
    private var _tg: String? = null
    private var _email: String? = null

    constructor(
        id: UInt,
        surname: String,
        name: String,
        git: String,
        patronymic: String? = null,
        phone: String? = null,
        tg: String? = null,
        email: String? = null,
    ) : this(id, surname, name, git) {
        this.patronymic = patronymic
        this.phone = phone
        this.tg = tg
        this.email = email
    }

    constructor(
        params: Map<String, Any?>
    ) : this(
        params["id"] as UInt,
        params["surname"] as String,
        params["name"] as String,
        params["git"] as String,
        params["patronymic"] as? String,
        params["phone"] as? String,
        params["tg"] as? String,
        params["email"] as? String,
    )

    constructor(
        studentStr: String
    ) : this (
        parseString(studentStr)
    )

    val id: UInt
        get() = _id

    val surname: String
        get() = _surname

    val name: String
        get() = _name

    val git: String
        get() = _git

    var patronymic: String?
        get() = _patronymic
        set(value) {
            if (checkName(value)) {
                _patronymic = value
            } else {
                println("Ошибка: отчество задано неправильно")
            }
        }

    var phone: String?
        get() = _phone
        set(value) {
            if (checkPhone(value)) {
                _phone = value
            } else {
                println(
                    "Ошибка: телефон задан неправильно (поддерживаются только российские номера)"
                )
            }
        }

    var tg: String?
        get() = _tg
        set(value) {
            if (checkTg(value)) {
                _tg = value
            } else {
                println("Ошибка: тг задан неправильно")
            }
        }

    var email: String?
        get() = _email
        set(value) {
           if (checkEmail(value)) {
               _email = value
           } else {
               println("Ошибка: почта задана неправильно")
           }
        }

    var contacts: String
        get() {
            return when {
                !phone.isNullOrEmpty() -> "тел: ${phone.toString()}"
                !tg.isNullOrEmpty() -> "тг: ${tg.toString()}"
                !email.isNullOrEmpty() -> "почта: ${email.toString()}"
                else -> "отсутствуют"
            }
        }
        set(value: String) {
            if (value == "отсутствуют") {
                _phone = null
                _tg = null
                _email = null
                return
            }
            val (prefix, postfix) = value.split(" ")
            when {
                prefix == "тел:" && checkPhone(postfix) -> _phone = postfix
                prefix == "тг:" && checkTg(postfix) -> _tg = tg
                prefix == "почта:" && checkEmail(postfix) -> _email = email
                else -> throw IllegalArgumentException("Ошибка: выбран неизвестный контакт или неверное значение")
            }
        }

    val initials: String
        get() {
            val patron = if (patronymic.isNullOrEmpty()) "" else patronymic.toString()
            return arrayOf(surname, name, patron).joinToString(" ")
        }

    override fun toString(): String {
        return arrayOf(
            _id,
            _surname,
            _name,
            _patronymic,
            _phone,
            _tg,
            _email,
            _git).joinToString(",") { f -> f?.toString() ?: "" }
    }

    fun getInfo(): String {
        return """Информация о студенте #$id
            |ФИО: $initials
            |Гит: $git
            |$contacts""".trimMargin()
    }

    companion object {
        protected val fields = listOf(
            "id",
            "surname",
            "name",
            "git",
            "patronymic",
            "phone",
            "tg",
            "email"
        )

        protected fun parseString(studentStr: String): Map<String, Any?>  {
            val valuesList = studentStr.split(",")
            val paramsMap = mutableMapOf<String, Any?>()
            fields.zip(valuesList)
                  .forEach { (k, v) -> paramsMap[k] = convToType(k, v, fields) }
            return paramsMap
        }
    }
}