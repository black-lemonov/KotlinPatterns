import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import singleton.StudentListDB
import students.Student
import template.DataListStudent

class StudentListDBTest {
    private val db = StudentListDB()

    @Test
    fun testGetByPage() {
        val dl = db.getByPage(1, 3)
        assertTrue(dl is DataListStudent)
    }

    @Test
    fun testAdd() {
        db.add(
            Student(0, "Карелин", "Вячеслав", "Семенович")
        )
    }

    @Test
    fun testReplace() {
        db.update(
            Student(0, "Учиха", "Саске", "Фугаки"),
            8
        )
    }

    @Test
    fun testRemove() {
        db.remove(9)
    }

    @Test
    fun testCount() {
        println(db.countAll())
    }
}