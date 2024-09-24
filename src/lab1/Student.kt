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
        _name = name
        _patronymic = patronymic
        _phone = phone
        _tg = tg
        _email = email
        _giturl = giturl
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
            _surname = value
        }

    var name: String?
        get() = _name
        set(value) {
            _name = value
        }

    var patronymic: String?
        get() = _patronymic
        set(value) {
            _patronymic = value
        }

    var phone: String?
        get() = _phone
        set(value) {
            _phone = value
        }

    var tg: String?
        get() = _tg
        set(value) {
            _tg = value
        }

    var email: String?
        get() = _email
        set(value) {
            _email = value
        }

    var giturl: String?
        get() = _giturl
        set(value) {
            _giturl = value
        }

    override fun toString(): String {
        val fields = listOf(_id, _surname, _name, _patronymic, _phone, _tg, _email, _giturl)
        return fields.joinToString(" ") { f -> f?.toString() ?: "" }
    }
}