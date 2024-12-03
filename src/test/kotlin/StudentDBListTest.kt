import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import singleton.StudentDBList
import students.Student
import template.DataListStudentShort

class StudentDBListTest {
    private val db = StudentDBList()

    @Test
    fun testGet() {
        val s = db.get(1)
        assertTrue(s is Student)
        println(s)
    }

    @Test
    fun testGetByPage() {
        val dl = db.getByPage(1, 3)
        assertTrue(dl is DataListStudentShort)
    }

    @Test
    fun testAdd() {
        db.add(
            Student(0, "Карелин", "Вячеслав", "Семенович")
        )
    }

    @Test
    fun testReplace() {
        db.replaceById(
            Student(0, "Учиха", "Саске", "Фугаки"),
            8
        )
    }

    @Test
    fun testRemove() {
        db.removeById(9)
    }

    @Test
    fun testCount() {
        println(db.countAll())
    }
}