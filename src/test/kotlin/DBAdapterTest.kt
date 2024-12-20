import adapter.StudentListDBAdapter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import students.Student
import template.DataListStudent

class DBAdapterTest {
    private val db = StudentListDBAdapter()

    @Test
    fun testGetByPage() {
        val dl = db.getByPage(1, 3)
        assertTrue(dl is DataListStudent)
    }

    @Test
    fun testAdd() {
        db.add(
            Student(0, "Учиха", "Мадара", "Таджима")
        )
    }

    @Test
    fun testReplace() {
        db.replaceById(
            Student(0, "Учиха", "Итачи", "Фугаки"),
            10
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