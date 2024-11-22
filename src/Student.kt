class Student(
    surname: String,
    name: String,
    lastname: String
) {
    private var _id : UInt? = null
    private var _surname : String = surname
    private var _name : String = name
    private var _lastname : String = lastname
    private var _phone : String? = null
    private var _tg : String? = null
    private var _email : String? = null
    private var _git : String? = null

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
        _id = id
        _surname = surname
        _name = name
        _lastname = lastname
        _phone = phone
        _tg = tg
        _email = email
        _git = git
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
}