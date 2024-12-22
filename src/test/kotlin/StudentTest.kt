import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import student.Student

class StudentTest {

    private lateinit var testStudent: Student

    @BeforeEach
    fun setUp() {
        testStudent = Student(
            id = 1,
            surname = "Хатаке",
            name = "Какаши",
            lastname = "Сакума"
        )
    }

    @Test
    fun testPrimaryConstructor() {
        assertEquals(1, testStudent.id)
        assertEquals("Хатаке", testStudent.surname)
        assertEquals("Какаши", testStudent.name)
        assertEquals("Сакума", testStudent.lastname)
        assertNull(testStudent.tg)
        assertNull(testStudent.git)
        assertNull(testStudent.phone)
        assertNull(testStudent.email)
    }

    @Test
    fun testSecondaryConstructor() {
        val testStudent2 = Student(
            id = 2,
            surname = "Узумаке",
            name = "Наруто",
            lastname = "Минато",
            phone = "+1234567890",
            tg = "@naruto",
            email = "hokage@konoha.com",
            git = "naruto-prog"
        )

        assertEquals(2, testStudent2.id)
        assertEquals("Узумаке", testStudent2.surname)
        assertEquals("Наруто", testStudent2.name)
        assertEquals("Минато", testStudent2.lastname)
        assertEquals("+1234567890", testStudent2.phone)
        assertEquals("@naruto", testStudent2.tg)
        assertEquals("hokage@konoha.com", testStudent2.email)
        assertEquals("naruto-prog", testStudent2.git)
    }

    @Test
    fun testMapConstructor() {
        val info = mapOf(
            "id" to 3,
            "surname" to "Учиха",
            "name" to "Саске",
            "lastname" to "Фугаку",
            "phone" to "+9876543210",
            "email" to "hebi@konoha.com"
        )
        val testStudent = Student(info)

        assertEquals(3, testStudent.id)
        assertEquals("Учиха", testStudent.surname)
        assertEquals("Саске", testStudent.name)
        assertEquals("Фугаку", testStudent.lastname)
        assertEquals("+9876543210", testStudent.phone)
        assertEquals("hebi@konoha.com", testStudent.email)
        assertNull(testStudent.tg)
        assertNull(testStudent.git)
    }

    @Test
    fun testValidPhoneNumber() {
        testStudent.phone = "+1234567890"
        assertEquals("+1234567890", testStudent.phone)
    }

    @Test
    fun testInvalidPhoneNumber() {
        assertThrows(IllegalStateException::class.java) {
            testStudent.phone = "не телефон"
        }
    }

    @Test
    fun testValidEmail() {
        testStudent.email = "kakashi@konoha.com"
        assertEquals("kakashi@konoha.com", testStudent.email)
    }

    @Test
    fun testInvalidEmail() {
        assertThrows(IllegalStateException::class.java) {
            testStudent.email = "точно не почта"
        }
    }

    @Test
    fun testToString() {
        testStudent.phone = "+1234567890"
        testStudent.email = "kakashi@konoha.com"
        testStudent.tg = "@kakashi"
        testStudent.git = "kakashi-prog"

        val expected = "Student(id=1, name=Какаши, surname=Хатаке, lastname=Сакума, phone=+1234567890, tg=@kakashi, email=kakashi@konoha.com, git=kakashi-prog)"
        assertEquals(expected, testStudent.toString())
    }

    @Test
    fun testValidateValid() {
        testStudent.git = "kakashi-prog"
        testStudent.email = "kakashi@konoha.com"
        assertTrue(testStudent.validate())
    }

    @Test
    fun testValidateInvalid() {
        assertFalse(testStudent.validate())
    }

    @Test
    fun testValidateWithOnlyGit() {
        testStudent.git = "kakashi-prog"
        assertFalse(testStudent.validate())
    }

    @Test
    fun testValidateWithOnlyContact() {
        testStudent.phone = "+1234567890"
        assertFalse(testStudent.validate())
    }

    @Test
    fun testSetContacts() {
        testStudent.setContacts("kakashi@konoha.com", "@kakashi", "+1234567890")
        assertEquals("kakashi@konoha.com", testStudent.email)
        assertEquals("@kakashi", testStudent.tg)
        assertEquals("+1234567890", testStudent.phone)
    }

    @Test
    fun testSetContactsEmail() {
        testStudent.setContacts("kakashi@konoha.com", null, null)
        assertEquals("kakashi@konoha.com", testStudent.email)
        assertNull(testStudent.tg)
        assertNull(testStudent.phone)
    }

    @Test
    fun testSetContactsInvalid() {
        assertThrows(IllegalStateException::class.java) {
            testStudent.setContacts("не почта", null, null)
        }
    }
}