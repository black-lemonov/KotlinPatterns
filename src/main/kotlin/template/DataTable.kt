package template

class DataTable(
    private var data: List<List<Any?>>
) {

    fun get(row: Int, column: Int) : Any? {
        require(row > 0 && column > 0) {
            "Номер строки/столбца должен быть > 0"
        }
        require(row <= rows && column <= columns) {
            "Таблица имеет меньший размер"
        }

        return data[row-1][column-1]
    }

    val rows : Int
        get() = data.size

    val columns : Int
        get() = data.first().size
}