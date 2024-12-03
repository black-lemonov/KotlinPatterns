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

    fun select(columns: String, from: String, where: String) : ResultSet {
        checkConnection()
        val stmt = con?.createStatement()
        val result = stmt?.executeQuery("select $columns from $from where $where")
        return result ?: throw SQLException()
    }

    fun select(columns: String, from: String, limit: Int, offset: Int): ResultSet {
        checkConnection()
        val stmt = con?.createStatement()
        val result = stmt?.executeQuery("select $columns from $from limit $limit offset $offset")
        return result ?: throw SQLException()
    }

    fun insert(into: String, columns: String, values: String) {
        checkConnection()
        val stmt = con?.createStatement()
        stmt?.executeUpdate("INSERT INTO $into ( $columns ) VALUES ( $values )")
    }

    fun update(table: String, set: String, where: String) {
        checkConnection()
        val stmt = con?.createStatement()
        stmt?.executeUpdate("UPDATE $table SET $set WHERE $where")
    }

    fun delete(from: String, where: String) {
        checkConnection()
        val stmt = con?.createStatement()
        stmt?.executeUpdate("DELETE FROM $from WHERE $where")
    }


    fun selectCountAll(from: String) : Int {
        checkConnection()
        val stmt = con?.createStatement()
        val resultSet = stmt?.executeQuery("SELECT count(*) FROM $from")
        resultSet?.next()
        return resultSet?.getInt(1) ?: throw SQLException()
    }
}