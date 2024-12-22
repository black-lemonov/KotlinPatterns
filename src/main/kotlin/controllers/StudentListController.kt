package controllers

import java.sql.SQLException

import adapter.StudentListInterface
import filters.StudentFilter
import template.DataListStudentShort
import strategy.StudentList
import view.MainWindow

class StudentListController(studentSourceData: StudentListInterface, private var view: MainWindow) {

    private var studentsList: StudentList = StudentList(studentSourceData);
    private var dataListStudentShort: DataListStudentShort? = null;

    fun getStudentsList(): StudentList {
        return studentsList
    }

    fun setView(view: MainWindow) {
        this.view = view
    }

    fun firstInitDataList() {
        val page = 1
        val pageSize = 20
        try {
            dataListStudentShort = studentsList.getKNStudentShortList(k = pageSize, n = page, studentFilter = null)
            dataListStudentShort?.pagination?.updatePagination(
                studentsList.getStudentCount(),
                page,
                pageSize
            )
            view.setDataList(dataListStudentShort)
        } catch (e: SQLException) {
            throwErrorMessage(e.message)
        }
    }

    fun refreshData(pageSize: Int, page: Int, studentFilter: StudentFilter?) {
        try {
            dataListStudentShort = studentsList.getKNStudentShortList(k = pageSize, n = page, studentFilter = studentFilter);
            dataListStudentShort?.pagination?.updatePagination(
                studentsList.getStudentCount(),
                page,
                pageSize
            )
            view.setDataList(dataListStudentShort)
            dataListStudentShort?.addSubscriber(view)
            dataListStudentShort?.notifySubscribers()
        } catch (e: SQLException) {
            throwErrorMessage(e.message)
        }
    }

    fun refreshData() {
        val page = dataListStudentShort?.pagination?.currentPage
        val pageSize = dataListStudentShort?.pagination?.perPage
        val studentFilter = studentsList.studentFilter
        if (page == null || pageSize == null) {
            throwErrorMessage("Список не был инициализирован, ошибка")
            return
        }
        try {
            dataListStudentShort = studentsList.getKNStudentShortList(k = pageSize, n = page, studentFilter = studentFilter);
            dataListStudentShort?.pagination?.updatePagination(
                studentsList.getStudentCount(),
                page,
                pageSize
            )
            view.setDataList(dataListStudentShort)
            dataListStudentShort?.addSubscriber(view)
            dataListStudentShort?.notifySubscribers()
        } catch (e: SQLException) {
            throwErrorMessage(e.message)
        }
    }

    fun deleteStudent(id: Int): Boolean {
        return studentsList.deleteStudent(id)
    }

    private fun throwErrorMessage(errorMessage: String?) {
        var error = ""
        if (errorMessage == null) {
            error = "Неизвестная ошибка"
        } else {
            error = errorMessage
        }
        val page = 1
        val pageSize = 20
        dataListStudentShort = DataListStudentShort(mutableListOf())
        dataListStudentShort?.pagination?.updatePagination(
            0,
            page,
            pageSize
        )
        view.setDataList(dataListStudentShort)
        view.showError("Error: $error")
    }

}