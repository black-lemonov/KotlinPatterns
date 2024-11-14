package lab1

fun main() {
    val objs = listOf(Student(1u, "Masodov"), Student(2u, "Sorokin"), Student(3u, "Elizarov"))
    val d = Student(4u, "Danilov")
    d.phone = "+79183729902"
    d.setContact(Pair("tg", "@eeeee"))
}