package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

/**
 * Реализация класс для работы с базой данных
 */
class SQLiteDB private constructor(): DBInterface {

    private var path: String = "./src/main/resources/patterns.db"

    private var connection: Connection? = null

    companion object {
        @Volatile
        private var instance: SQLiteDB? = null

        /**
         * Получить экземпляр класса
         */
        fun getInstance(): SQLiteDB {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SQLiteDB()
                    }
                }
            }
            return instance!!
        }
    }

    override fun connect() {
        if (connection != null) {
            return
        }
        val url = "jdbc:postgresql://$path"

        connection = DriverManager.getConnection(url)
    }

    private fun checkConnection() {
        if (connection == null || connection?.isClosed == true) {
            throw SQLException("Подключение к базе данных закрыто или отсутствует")
        }
    }

    override fun executeQuery(sql: String): ResultSet {
        checkConnection()
        val statement: Statement = connection?.createStatement()!!
        return statement.executeQuery(sql)
    }

    override fun executeUpdate(sql: String): Int {
        checkConnection()
        val statement = connection?.createStatement()!!
        return statement.executeUpdate(sql)
    }

    override fun closeConnection() {
        checkConnection()
        connection?.close()
        connection = null
    }

    override fun getConn(): Connection? {
        return connection
    }
}