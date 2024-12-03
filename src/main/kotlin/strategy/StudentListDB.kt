package strategy

import students.Student
import students.StudentShort
import template.DataList
import template.DataListStudentShort
import java.sql.DriverManager


class StudentListDB {
    private val con = DriverManager.getConnection("jdbc:sqlite:./src/main/resources/patterns.db")

    fun get(id: Int) : Student? {
        val stmt = con.createStatement()
        val result = stmt.executeQuery("select * from student where id = $id")
        return if (result.next()) Student(result) else null
    }

    fun getByPage(
        page : Int, number : Int
    ) : DataList<StudentShort> {
        require(page > 0) { "page must be > 0" }
        val stmt = con.createStatement()
        val result = stmt.executeQuery("select * from student limit $number offset ${number * (page-1)}")
        return  DataListStudentShort(
            generateSequence {
                if (result.next()) StudentShort(result) else null
            }.toMutableList()
        )
    }

    fun add(student: Student) {
        try {
            val sql = "INSERT INTO student" +
                    "(\"surname\", \"name\", \"lastname\", \"phone\", \"tg\", \"email\", \"git\")" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
            val preparedStatement = con.prepareStatement(sql)
            preparedStatement.setString(1, student.surname)
            preparedStatement.setString(2, student.name)
            preparedStatement.setString(3, student.lastname)
            preparedStatement.setString(4, student.phone)
            preparedStatement.setString(5, student.tg)
            preparedStatement.setString(6, student.email)
            preparedStatement.setString(7, student.git)
            preparedStatement.executeUpdate()
        } catch (e: Exception) {
            con.rollback()
            throw e
        }
    }

    fun replaceById(student: Student, id: Int) {
        try {
            val sql = "UPDATE student SET \"surname\" = ?, \"name\" = ?, \"lastname\" = ?," +
                    "\"phone\" = ?, \"tg\" = ?, \"email\" = ?, \"git\" = ?" +
                    "WHERE id = ?"
            val preparedStatement = con.prepareStatement(sql)
            preparedStatement.setString(1, student.surname)
            preparedStatement.setString(2, student.name)
            preparedStatement.setString(3, student.lastname)
            preparedStatement.setString(4, student.phone)
            preparedStatement.setString(5, student.tg)
            preparedStatement.setString(6, student.email)
            preparedStatement.setString(7, student.git)
            preparedStatement.setInt(8, id)
            preparedStatement.executeUpdate()
        } catch (e: Exception) {
            con.rollback()
            throw e
        }
    }

    fun remove(id: Int) {
        try {
            val sql = "DELETE FROM student WHERE \"id\" = ?"
            val preparedStatement = con.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            preparedStatement.executeUpdate()
        } catch (e: Exception) {
            con.rollback()
            throw e
        }
    }

    fun countAll() : Int {
        val stmt = con.createStatement()
        val resultSet = stmt.executeQuery("SELECT count(*) FROM student")
        resultSet.next()
        return resultSet.getInt(1)
    }
}


