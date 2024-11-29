package students

class StudentShort : StudentBase {
    override var id : UInt
    private val surnameAndInitials : String
    override val git : String?
    private val contacts : String?

    constructor(
        id : UInt,
        info: String
    ) {
        this.id = id
        val fields = fetchInfo(info)
        this.surnameAndInitials = fields[0] as String
        this.git = fields[1]
        this.contacts = fields[2]
    }

    constructor(
        student : Student
    ) : this(
        student.id,
        student.getInfo()
    )

    private fun fetchInfo(info: String) : List<String?> {
        return info.split(';').map { it.ifBlank {null} }
    }

    override fun getSurnameAndInitials(): String {
        return surnameAndInitials
    }

    override fun getContactsInfo(): String {
        return contacts ?: ""
    }
}