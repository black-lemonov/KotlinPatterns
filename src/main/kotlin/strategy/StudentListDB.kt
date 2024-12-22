package strategy

import adapter.StudentListInterface
import database.DBInterface
import database.SQLiteDB
import filters.StudentFilter
import enums.SearchParam
import template.DataListStudentShort
import student.Student
import student.StudentShort
import java.sql.SQLException
import java.sql.Statement


class StudentListDB(
    private val db: DBInterface = SQLiteDB.getInstance(),
    var studentFilter: StudentFilter? = null
): StudentListInterface {

    override fun getStudentById(id: Int): Student? {
        val query = "SELECT * FROM student WHERE id = $id"
        try {
            db.connect()
            val resultSet = db.executeQuery(query)
            return if (resultSet.next()) {
                Student(resultSet)
            } else {
                null
            }
        } catch (e: SQLException) {
            println("Error getting student: ${e.message}")
            return null
        } finally {
            db.closeConnection()
        }
    }

    override fun initStudentFilter(studentFilter: StudentFilter) {
        this.studentFilter = studentFilter
    }

    override fun getKNStudentShortList(k: Int, n: Int):  DataListStudentShort {
        val page = n
        val pageSize = k
        if (this.studentFilter != null) {
            return DataListStudentShort(getFilteredStudentList(page = page, pageSize = pageSize, studentFilter!!), 1)
        }
        val offset = (page - 1) * pageSize
        val query = "SELECT * FROM student ORDER BY id LIMIT $pageSize OFFSET $offset"
        val studentShortList = mutableListOf<StudentShort>()

        try {
            db.connect()
            val resultSet = db.executeQuery(query)
            while (resultSet.next()) {
                val student = Student(resultSet)
                studentShortList.add(StudentShort(student))
            }
        } catch (e: SQLException) {
            println("Error getting student list: ${e.message}")
        } finally {
            db.closeConnection()
        }

        return DataListStudentShort(studentShortList)
    }

    fun getFilteredStudentList(
        page: Int,
        pageSize: Int,
        studentFilter: StudentFilter
    ): List<Student> {
        val offset = (page - 1) * pageSize

        var query = "SELECT * FROM student "
        query += buildFilterQuery(studentFilter)
        query += " ORDER BY id LIMIT $pageSize OFFSET $offset"

        val studentList: MutableList<Student> = ArrayList()
        try {
            db.connect()
            val rs = db.executeQuery(query)
            while (rs.next()) {
                studentList.add(Student(rs))
            }
        } catch (e: SQLException) {
            println("Error fetching filtered students: " + e.message)
        } finally {
            db.closeConnection()
        }
        return studentList
    }

    fun getFilteredStudentCount(studentFilter: StudentFilter?): Int {
        var query = "SELECT COUNT(*) FROM student "
        query += buildFilterQuery(studentFilter!!) // Метод для генерации условий фильтрации

        try {
            db.connect()
            val rs = db.executeQuery(query)
            if (rs.next()) {
                return rs.getInt(1)
            }
        } catch (e: SQLException) {
            println("Error counting students: " + e.message)
        } finally {
            db.closeConnection()
        }
        return 0
    }

    private fun buildFilterQuery(studentFilter: StudentFilter): String {
        var query = "WHERE (TRUE"
        val nameFilter = studentFilter.surnameAndInitialsFilter
        if (nameFilter.isNotEmpty()) query += " AND surname || ' ' || name ILIKE '%$nameFilter%'"
        query = mutateQueryWithFilter(
            query,
            studentFilter.gitSearch,
            studentFilter.gitFilter,
            "git"
        )
        query = mutateQueryWithFilter(
            query,
            studentFilter.emailSearch,
            studentFilter.emailFilter,
            "email"
        )
        query = mutateQueryWithFilter(
            query,
            studentFilter.phoneSearch,
            studentFilter.phoneFilter,
            "phone"
        )
        query = mutateQueryWithFilter(
            query,
            studentFilter.tgSearch,
            studentFilter.tgFilter,
            "tg"
        )

        return "$query)"
    }

    private fun mutateQueryWithFilter(
        query: String,
        search: SearchParam,
        value: String,
        column: String
    ): String {
        var newQuery = query
        if (search == SearchParam.YES) {
            newQuery += " AND $column IS NOT NULL AND $column!=''"
            if (value.isNotEmpty()) {
                newQuery += " AND $column LIKE '%$value%'"
            }
        } else {
            if (search == SearchParam.NO) newQuery += " AND ($column IS NULL OR $column='')"
        }

        return newQuery
    }


    override fun addStudent(student: Student): Int {
        student.transformEmptyStringsToNull()
        val insertSQL = """
            INSERT INTO student (surname, name, lastname, tg, git, phone, email)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        try {
            db.connect()
            val preparedStatement = db.getConn()?.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)
            preparedStatement?.apply {
                setString(1, student.surname)
                setString(2, student.name)
                setString(3, student.lastname)
                setString(4, student.tg)
                setString(5, student.git)
                setString(6, student.phone)
                setString(7, student.email)
                executeUpdate()

                val generatedKeys = generatedKeys
                if (generatedKeys.next()) {
                    val id = generatedKeys.getInt(1)
                    return id
                }
            }
        } catch (e: SQLException) {
            println("Error adding student: ${e.message}")
        } finally {
            db.closeConnection()
        }
        return -1
    }

    override fun updateStudent(student: Student): Boolean {
        student.transformEmptyStringsToNull()
        val updateSQL = """
            UPDATE student 
            SET surname = ?, name = ?, lastname = ?, tg = ?, git = ?, phone = ?, email = ?
            WHERE id = ?
        """.trimIndent()

        try {
            db.connect()
            val preparedStatement = db.getConn()?.prepareStatement(updateSQL)
            preparedStatement?.apply {
                setString(1, student.surname)
                setString(2, student.name)
                setString(3, student.lastname)
                setString(4, student.tg)
                setString(5, student.git)
                setString(6, student.phone)
                setString(7, student.email)
                setInt(8, student.id)
                val rowsAffected = executeUpdate()
                if (rowsAffected > 0) {
                    return true
                }
            }
        } catch (e: SQLException) {
            println("Error updating student: ${e.message}")
        } finally {
            db.closeConnection()
        }
        return false
    }

    override fun deleteStudent(id: Int): Boolean {
        val deleteSQL = "DELETE FROM student WHERE id = ?"

        try {
            db.connect()
            val preparedStatement = db.getConn()?.prepareStatement(deleteSQL)
            preparedStatement?.apply {
                setInt(1, id)
                val rowsAffected = executeUpdate()
                if (rowsAffected > 0) {
                    return true
                }
            }
        } catch (e: SQLException) {
            println("Error deleting student: ${e.message}")
        } finally {
            db.closeConnection()
        }
        return false
    }

    override fun getStudentCount(): Int {
        if (this.studentFilter != null) {
            return getFilteredStudentCount(studentFilter)
        }
        val query = "SELECT COUNT(*) FROM student"
        try {
            db.connect()
            val resultSet = db.executeQuery(query)
            if (resultSet.next()) {
                return resultSet.getInt(1)
            }
        } catch (e: SQLException) {
            println("Error getting student count: ${e.message}")
        } finally {
            db.closeConnection()
        }
        return 0
    }
}