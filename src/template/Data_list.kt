package template

abstract class Data_list {
    abstract fun select(index : Int)

    // выбранные номера
    abstract fun get_selected() : List<Int>

    fun get_data() : Data_table {
        val rows = get_selected().mapIndexed { index, i ->
            listOf(getId(index)) + getRest(index)
        }.toList()
        return Data_table(rows)
    }

    protected abstract fun getId(index : Int) : Any

    protected abstract fun getRest(index: Int) : List<Any>
}