import controllers.StudentListController
import strategy.StudentListDB
import view.MainWindow

fun main() {
    val view = MainWindow()
    val studentListDB = StudentListDB()
    val controller = StudentListController(studentListDB, view)
    view.create(controller)
}