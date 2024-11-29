import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import students.Student
import students.StudentShort


class StudentShortTest {
    @Test
    fun testIdInfoConstructor() {
        StudentShort(
            1u,
            "Абрамов И.А.;;тел: +70000000000"
        )
    }

    @Test
    fun testFromStudentConstructor() {
        StudentShort(
            Student(
                1u,
                "Абрамов",
                "Иван",
                "Адольфович",
                "+70000000000"
            )
        )
    }

    @Test
    fun testGetInfo() {
        val ss = StudentShort(
            1u,
            "Абрамов И.А.;;тел: +70000000000"
        )
        assertEquals(
            "Абрамов И.А.;;тел: +70000000000",
            ss.getInfo()
        )
    }

    @Test
    fun testGetContactsInfo() {
        val ss = StudentShort(
            1u,
            "Абрамов И.А.;;тел: +70000000000"
        )
        assertEquals(
            "тел: +70000000000",
            ss.getContactsInfo()
        )
    }

    @Test
    fun testGetSurnameAndInitials() {
        val ss = StudentShort(
            1u,
            "Абрамов И.А.;;тел: +70000000000"
        )
        assertEquals(
            "Абрамов И.А.",
            ss.getSurnameAndInitials()
        )
    }
}