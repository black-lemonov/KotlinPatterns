package template

abstract class DataList<T> {
    protected abstract val _data: MutableList<T>
    protected val _selected: MutableList<Int> = mutableListOf()

    fun select(index : Int) {
        require(index > 0 && index <= _data.size) {
            "Нет такого номера!"
        }
        if (_selected.contains(index))
            return
        _selected.add(index)
    }

    // выбранные номера
    fun getSelected() : List<Int> {
        return _selected.toList()
    }

    fun getData() : DataTable {
        return DataTable(
            List(_data.size) {
                index -> listOf(index+1) + getEntityFields(index)
            }
        )
    }

    protected abstract fun getEntityFields(index: Int) : List<Any?>
}