import org.junit.jupiter.api.Test
import java.sql.DriverManager

class SQLiteTest {

    @Test
    fun testSelectFromStudent() {
        val con = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/patterns.db")
        val stmt = con.createStatement()
        val result = stmt.executeQuery("select * from student")
        while (result.next()) {
            println(listOf(
                result.getInt("id"),
                result.getString("surname"),
                result.getString("name"),
                result.getString("lastname"),
                result.getString("phone"),
                result.getString("tg"),
                result.getString("email"),
                result.getString("git")
            ).joinToString())
        }
    }
}