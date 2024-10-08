package lab1

import javax.lang.model.type.UnionType

class Student(
    id: UInt,
    surname: String
) {
    private val _id: UInt = id
    private var _surname: String = surname
    private var _name: String? = null
    private var _patronymic: String? = null
    private var _phone: String? = null
    private var _tg: String? = null
    private var _email: String? = null
    private var _giturl: String? = null

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

    val id: UInt
        get() = _id

    val surname: String
        get() = _surname

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
            println("Отчество студента $_surname успешно установлено!")
        }

    var phone: String?
        get() = _phone
        set(value) {
            checkPhone(value)
            _phone = value
            println("Номер телефона студента $_surname успешно установлен!")
        }

    var tg: String?
        get() = _tg
        set(value) {
            checkTg(value)
            _tg = value
            println("Телеграм студента $_surname успешно установлен!")
        }

    var email: String?
        get() = _email
        set(value) {
            checkEmail(value)
            _email = value
            println("Email студента $_surname успешно установлен!")
        }

    var giturl: String?
        get() = _giturl
        set(value) {
            checkGit(value)
            _giturl = value
            println("Git студента $_surname успешно установлен!")
        }

    override fun toString(): String {
        val fields = listOf(_id, _surname, _name, _patronymic, _phone, _tg, _email, _giturl)
        return fields.joinToString(" ") { f -> f?.toString() ?: "" }
    }

    fun validate(): Boolean = (_giturl != null) and ((_tg != null) or (_email != null) or (_phone != null))

    fun setContact(pair: Pair<String, String?>) {
        when (pair.first) {
            "phone" -> this.phone = pair.second
            "email" -> this.email = pair.second
            "tg" -> this.tg = pair.second
            "giturl" -> this.giturl = pair.second
            else -> {
                throw IllegalArgumentException(
                    "Неизвестный тип контакта. Доступные контакты: phone, email, tg, giturl."
                )
            }
        }
    }

    companion object {
        private val nameRegex = Regex("^[А-Яа-яA-Za-z]+$")
        private val tgRegex = Regex("^@?\\w+$")
        private val emailRegex = Regex("^\\w+@\\w+\\.\\w+$")
        private val gitRegex = Regex("^(https://) | (www\\.) git (hub) | (lab) \\.com/\\w+/?$")
        private val phoneRegex = Regex("^\\+?\\d{11}$")

        private val checkName = {
            name: String? -> if (name != null) check(
                nameRegex.matches(name)
            ) {
                "Ошибка! Имя должно содержать только буквы."
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