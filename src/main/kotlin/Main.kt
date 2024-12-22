import adapter.StudentListFileAdapter
import controllers.StudentListController
import strategy.StudentListFile
import strategy.strategies.StudentYamlFileProcessor
import view.MainWindow

fun main() {
    val view = MainWindow()
    val studentList = StudentListFileAdapter(StudentListFile("./src/main/resources/students.yaml", StudentYamlFileProcessor()))
    val controller = StudentListController(studentList, view)
    view.create(controller)
}