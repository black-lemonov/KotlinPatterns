package lab1

fun main() {
    val objs = listOf(Student(1u, "Masodov"), Student(2u, "Sorokin"), Student(3u, "Elizarov"))
    for (o in objs) {
        println(o)
    }
}