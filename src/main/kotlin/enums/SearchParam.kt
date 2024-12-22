package enums

enum class SearchParam {
    YES,
    NO,
    DONT_MATTER;

    companion object {
        @JvmStatic
        fun create(searchParam: String): SearchParam {
            return when (searchParam) {
                "Да" -> YES
                "Нет" -> NO
                else -> DONT_MATTER
            }
        }
    }
}