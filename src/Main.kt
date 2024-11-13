
fun main() {
    val ivanov = Student(
        4u,
        "Ivanov",
        "Ivan",
        "http://github.com/user/repo.git",
        "Ivanovich",
        "72931830284",
        "@megabrawl",
        "ivan2003rus@mail.ru"
    )
    println(ivanov)
    println(ivanov.getInfo())
    val iv = StudentShort(3u, "Ivanov Ivan,http://github.com/user/repo.git,тел: 72931830284")
    println(iv)
}