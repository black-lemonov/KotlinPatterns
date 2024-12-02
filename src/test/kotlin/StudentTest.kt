import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import students.Student


class StudentTest {
    @Test
    fun testPrimaryConstructor() {
        Student(
            1u, "Абрамов",
            "Иван",
            "Адольфович",
            "+71233212233"
        )
    }

    @Test
    fun testMapConstructor() {
        Student(
            mapOf(
                "id" to 1,
                "surname" to "Абрамов",
                "name" to "Иван",
                "lastname" to "Адольфович",
                "phone" to "+71233212233"
            )
        )
    }

    @Test
    fun testStringConstructor() {
        Student(
            "1,Абрамов,Иван,Адольфович,+71233212233,,,"
        )
    }

    @Test
    fun testGetInfo() {
        val student = Student(
            1u, "Абрамов", "Иван", "Адольфович",
            "+71233212233"
        )
        assertEquals(
            "Абрамов И.А.;;тел: +71233212233",
            student.getInfo()
        )
    }

    @Test
    fun testGetSurnameAndInitials() {
        val student = Student(
            1u, "Абрамов", "Иван", "Адольфович",
            "+71233212233"
        )
        assertEquals(
            "Абрамов И.А.",
            student.getSurnameAndInitials()
        )
    }

    @Test
    fun testGetContactsInfo() {
        val student = Student(
            1u, "Абрамов", "Иван", "Адольфович",
            "+71233212233"
        )
        assertEquals(
            "тел: +71233212233",
            student.getContactsInfo()
        )
    }

    @Test
    fun testSetContacts() {
        val student = Student(
            1u, "Абрамов", "Иван", "Адольфович",
            "+71233212233"
        )
        student.setContacts(email = "darkvan2006@mail.ru")
        assertEquals("darkvan2006@mail.ru", student.email)

        student.setContacts(phone = "+71231231231")
        assertEquals(student.phone, "+71231231231")

        student.setContacts(tg = "https://t.me/vano2006")
        assertEquals(student.tg, "https://t.me/vano2006")
    }
}
