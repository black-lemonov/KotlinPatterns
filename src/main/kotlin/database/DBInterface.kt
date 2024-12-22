package database

import java.sql.Connection
import java.sql.ResultSet

interface DBInterface {

    /**
     * Выполнить подключение к базе данных
     */
    fun connect()

    /**
     * Выполнить запрос к базе данных и вернуть результат ResultSet
     */
    fun executeQuery(sql: String): ResultSet

    /**
     * Выполнить запрос на обновление и вернуть результат в количестве изменённых объектов
     */
    fun executeUpdate(sql: String): Int

    /**
     * Закрыть подключение к базе данных
     */
    fun closeConnection()

    fun getConn(): Connection?
}