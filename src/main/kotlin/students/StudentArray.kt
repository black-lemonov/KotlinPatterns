package students

import kotlinx.serialization.Serializable

@Serializable
data class StudentArray (
    var students: MutableList<Student>
)