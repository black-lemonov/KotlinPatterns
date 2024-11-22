class Student {
    private var _id : UInt?
    private var _surname : String
    private var _name : String
    private var _lastname : String
    private var _phone : String?
    private var _tg : String?
    private var _email : String?
    private var _git : String?

    constructor(
        id : UInt?,
        surname : String,
        name : String,
        lastname : String,
        phone : String?,
        tg : String?,
        email : String?,
        git : String?
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
        get() = id
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