package singleton

import java.sql.*

object DBContext {
    private var con: Connection? = null

    fun connect(dbURL: String) {
        if (con != null) {
            println("Connection is busy. Try to disconnect first.")
            return
        }
        con = DriverManager.getConnection(dbURL)
        println("The connection to $dbURL was opened.")
    }

    fun disconnect() {
        if (con != null) {
            con?.close()
            println("Closed connection.")
        }
    }

    private fun checkConnection() {
        check(con != null) {
            "Connection is not opened!"
        }
    }

    fun select(columns: String, from: String, where: String) : ResultSet? {
        checkConnection()
        val stmt = con?.createStatement()
        val result = stmt?.executeQuery("select $columns from $from where $where")
        return result
    }

    fun select(columns: String, from: String, limit: Int, offset: Int): ResultSet? {
        checkConnection()
        val stmt = con?.createStatement()
        val result = stmt?.executeQuery("select $columns from $from limit $limit offset $offset")
        return result
    }

    fun insert(into: String, columns: String, values: List<Any?>) {
        checkConnection()
        val stmt = con?.createStatement()
        stmt?.executeUpdate("INSERT INTO $into ( $columns ) VALUES ( ${values.map { if (it == null) null else "\"$it\"" }.joinToString(",") } )")
    }

    fun update(table: String, setColumns: List<String>, setValues: List<Any?>, where: String) {
        checkConnection()
        val stmt = con?.createStatement()
        val setStmt = setColumns.zip(setValues) {
            a, b -> "\"$a\"=${if (b == null) "NULL" else "\"$b\""}"
        }.joinToString(",")

        stmt?.executeUpdate("UPDATE $table SET $setStmt WHERE $where")
    }

    fun delete(from: String, where: String) {
        checkConnection()
        val stmt = con?.createStatement()
        stmt?.executeUpdate("DELETE FROM $from WHERE $where")
    }


    fun selectCountAll(from: String) : Int? {
        checkConnection()
        val stmt = con?.createStatement()
        val resultSet = stmt?.executeQuery("SELECT count(*) FROM $from")
        resultSet?.next()
        return resultSet?.getInt(1)
    }
}