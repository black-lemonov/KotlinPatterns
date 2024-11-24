package students

class Student_short : StudentBase {
    private var _id : UInt?
    private val _surnameAndInitials : String
    private val _git : String?
    private val _contacts : String?

    constructor(
        id : UInt? = null,
        info: String
    ) {
        _id = id
        val fields = fetchInfo(info)
        _surnameAndInitials = fields[0] as String
        _git = fields[1]
        _contacts = fields[2]
    }

    private fun fetchInfo(info: String) : List<String?> {
        return info.split(';').map { it.ifBlank {null} }
    }

    constructor(
        student : Student
    ) : this(
        student.getId(),
        student.getInfo()
    )

    override fun getId(): UInt? {
        return _id
    }

    override fun setId(id: UInt?) {
        _id = id
    }

    override fun getSurnameAndInitials(): String {
        return _surnameAndInitials
    }

    override fun getGitInfo(): String {
        if (_git != null) {
            return "гит: $_git"
        }
        return ""
    }

    override fun getContactsInfo(): String {
        if (_contacts != null) {
            return _contacts
        }
        return ""
    }
}