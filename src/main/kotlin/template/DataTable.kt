package template

class DataTable<T> private constructor(private val data: List<List<T>>) {
    companion object {
        fun <T> create(data: List<List<T>>): DataTable<T> {
            return DataTable(data)
        }
    }

    fun getElement(row: Int, column: Int): T {
        require(row in data.indices && column in data[0].indices) {
            "Индексы выходят за пределы таблицы"
        }
        return data[row][column]
    }

    fun getColumnCount(): Int = if (data.isNotEmpty()) data[0].size else 0

    fun getRowCount(): Int = data.size
}