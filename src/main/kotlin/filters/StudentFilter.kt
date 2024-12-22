package filters

import enums.SearchParam

data class StudentFilter(
    val nameFilter: String,
    val gitFilter: String,
    val emailFilter: String,
    val phoneFilter: String,
    val tgFilter: String,

    val gitSearch: SearchParam,
    val phoneSearch: SearchParam,
    val tgSearch: SearchParam,
    val emailSearch: SearchParam,
)
