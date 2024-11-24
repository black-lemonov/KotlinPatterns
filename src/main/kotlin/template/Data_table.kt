package template

class Data_table(data: List<List<Any>>) {
    private var _data: List<List<Any>> = data

    fun get(row: Int, column: Int) : Any {
        require(row > 0 && column > 0) {
            "Номер строки/столбца должен быть > 0"
        }
        require(row <= rows && column <= columns) {
            "Таблица имеет меньший размер"
        }

        return _data[row][column]
    }

    val rows : Int
        get() = _data.size

    val columns : Int
        get() = _data.first().size
}