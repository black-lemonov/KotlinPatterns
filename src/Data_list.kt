abstract class Data_list {
    abstract fun select(index : Int)

    // выбранные номера
    abstract fun get_selected() : List<Int>

    fun get_data() : Data_table {
        val firstRow = get_names()
        val restRows = get_selected().mapIndexed { index, i ->
            listOf(index + 1, getId(index)) + getRest(index)
        }.toList()
        return Data_table(
            listOf(firstRow as List<Any>) + restRows
        )
    }

    // названия полей
    protected fun get_names() : List<String> {
        return listOf("№") + getNamesWithoutID()
    }

    protected abstract fun getNamesWithoutID() : List<String>

    protected abstract fun getId(index : Int) : Any

    protected abstract fun getRest(index: Int) : List<Any>
}