package lab1

class StudentShort(id: UInt, studentStr: String) {
    private val _id: UInt = id
    private val _initials: String
    private val _git: String
    private val _contacts: String

    init {
        val studentData = studentStr.split(";")
        _initials = studentData[0]
        _git = studentData[1]
        _contacts = studentData[2]
    }

    constructor(student: Student) : this(
        student.id,
        "${student.initials};${student.git};${student.contacts}"
    )
}