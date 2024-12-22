package strategy

import filters.StudentFilter
import enums.SearchParam
import template.DataList
import template.student.DataListStudentShort
import strategy.strategies.StudentFileProcessor
import strategy.strategies.StudentTxtFileProcessor
import student.Student
import student.StudentShort

class StudentListFile(
    private var students: MutableList<Student>,
    var fileProcessor: StudentFileProcessor = StudentTxtFileProcessor()
) {
    constructor(
        fileProcessor: StudentFileProcessor = StudentTxtFileProcessor()
    ) : this(mutableListOf(), fileProcessor)

    constructor(
        filePath: String,
        fileProcessor: StudentFileProcessor = StudentTxtFileProcessor()
    ) : this(mutableListOf(), fileProcessor) {
        readFromFile(filePath)
    }

    var studentFilter: StudentFilter? = null;

    /**
     * Считывает объекты из файла, используя объект StudentFileProcessor
     */
    fun readFromFile(filePath: String) {
        students = fileProcessor.readFromFile(filePath)
    }

    /**
     * Записать объекты в файл, использует объект StudentFileProcessor
     */
    fun writeToFile(directory: String, fileName: String) {
        fileProcessor.writeToFile(students, directory, fileName)
    }

    /**
     * Найти объект по id
     */
    fun findById(id: Int): Student {
        return students.first { it.id == id }
    }

    /**
     * Получить список k по счету n объектов класса Student_short
     *
     * @param n - страница (должен быть 0 или больше)
     * @param k - количество элементов для выборки (должно быть больше 0)
     *
     * @return Список объектов Student_short
     */
    fun getKNStudentShortList(n: Int, k: Int): DataList<StudentShort> {
        require(n >= 0) { "Индекс n должен быть больше или равен 0." }
        require(k > 0) { "Количество k должно быть больше 0." }

        if (studentFilter != null) {
            return DataListStudentShort(
                students
                    .drop((n - 1) * k)
                    .take(k)
                    .stream()
                    .filter { filterByStudentFilter(it) }
                    .map { StudentShort(it) }
                    .toList()
            )
        }
        return DataListStudentShort(
            students
                .drop((n - 1) * k)
                .take(k)
                .map { StudentShort(it) }
        )
    }

    fun filterByStudentFilter(student: Student): Boolean {
        if (studentFilter == null) {
            return true;
        }

        if (studentFilter!!.surnameAndInitialsFilter.isNotEmpty() && !student.getFullName().contains(studentFilter!!.surnameAndInitialsFilter)) {
            return false;
        } else if (
            !filterValueAndSearchParam(student.email, studentFilter!!.emailFilter, studentFilter!!.emailSearch) ||
            !filterValueAndSearchParam(student.git, studentFilter!!.gitFilter, studentFilter!!.gitSearch) ||
            !filterValueAndSearchParam(student.phone, studentFilter!!.phoneFilter, studentFilter!!.phoneSearch) ||
            !filterValueAndSearchParam(student.tg, studentFilter!!.tgFilter, studentFilter!!.tgSearch)
        ) {
            return false
        }

        return true
    }

    fun filterValueAndSearchParam(value: String?, filterValue: String, searchParam: SearchParam): Boolean {
        if (
            searchParam == SearchParam.YES
            && (
                    (filterValue.isNotEmpty() && value?.contains(filterValue) != true)
                            || (filterValue.isEmpty() && value.isNullOrEmpty())
                    )
        ) {
            return false
        } else if (
            searchParam == SearchParam.NO &&
            !value.isNullOrEmpty()
        ) {
            return false
        }
        return true
    }

    /**
     * Сортировка списка по фамилии и инициалам
     */
    fun orderStudentsByLastNameInitials() {
        orderStudents(compareBy { it.getSurnameWithInitials() })
    }

    /**
     * Сортировка списка
     */
    fun orderStudents(comparator: Comparator<Student>) {
        students.sortedWith(comparator)
    }

    /**
     * Добавление объекта
     */
    fun add(student: Student) {
        val nextId = (students.maxByOrNull { it.id }?.id ?: 0) + 1
        student.id = nextId

        students.addLast(student)
    }

    /**
     * Заменить объект по ID
     */
    fun replaceById(student: Student, id: Int) {
        student.id = id
        students.replaceAll { if (it.id == id) student else it }
    }

    /**
     * Удалить по ID
     */
    fun removeById(id: Int) {
        students.removeIf { it.id == id }
    }

    /**
     * Получить количество объектов в списке
     */
    fun getStudentShortСount(): Int = students.count()
}