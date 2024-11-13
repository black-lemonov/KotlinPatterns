
class StudentShort(id: UInt, studentInfo: String) : Student(
    toStudentParams(id, studentInfo)
) {
    companion object {
        private fun toStudentParams(id: UInt, studentInfo: String): Map<String, Any?> {
            val studentParams: MutableMap<String, Any?> = parseString(studentInfo).toMutableMap()
            studentParams["id"] = id
            return studentParams
        }

        private fun parseString(studentInfo: String): Map<String, Any?> {
            val studentParams = mutableMapOf<String, Any?>()
            val (initials, git, contacts) = studentInfo.split(',')
            val fullName = initials.split(' ')
            studentParams["surname"] = fullName[0]
            studentParams["name"] = fullName[1]
            if (fullName.size == 3) {
                studentParams["patronymic"] = fullName[2]
            }
            studentParams["git"] = git
            val (contactType, contact) = contacts.split(' ')
            when (contactType) {
                "тел:" -> studentParams["phone"] = contact
                "почта:" -> studentParams["email"] = contact
                "тг:" -> studentParams["tg"] = contact
            }
            return studentParams
        }
    }
}