import adapter.StudentDBListAdapter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import students.Student
import template.DataListStudentShort

class DBAdapterTest {
    private val db = StudentDBListAdapter()

    @Test
    fun testGet() {
        val s = db.get(1)
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