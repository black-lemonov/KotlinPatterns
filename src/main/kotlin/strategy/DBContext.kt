package strategy

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class DBContext {
    private var con: Connection = DriverManager.getConnection(dbURL)

    fun select(columns: String, from: String, where: String) : ResultSet {
        val stmt = con.createStatement()
        val result = stmt.executeQuery("select $columns from $from where $where")
        return result
    }

    fun select(columns: String, from: String, limit: Int, offset: Int): ResultSet {
        val stmt = con.createStatement()
        val result = stmt.executeQuery("select $columns from $from limit $limit offset $offset")
        return result
    }

    fun insert(into: String, columns: String, values: String) {
        val stmt = con.createStatement()
        stmt.executeUpdate("INSERT INTO $into ( $columns ) VALUES ( $values )")
    }

    fun update(table: String, set: String, where: String) {
        val stmt = con.createStatement()
        stmt.executeUpdate("UPDATE $table SET $set WHERE $where")
    }

    fun delete(from: String, where: String) {
        val stmt = con.createStatement()
        stmt.executeUpdate("DELETE FROM $from WHERE $where")
    }


    fun selectCountAll(from: String) : Int {
        val stmt = con.createStatement()
        val resultSet = stmt.executeQuery("SELECT count(*) FROM $from")
        resultSet.next()
        return resultSet.getInt(1)
    }
}