import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import strategy.readers.StudentJsonReader
import strategy.readers.StudentTxtReader
import strategy.readers.StudentYamlReader
import strategy.writers.StudentJsonWriter
import strategy.writers.StudentTxtWriter
import strategy.writers.StudentYamlWriter
import strategy.StudentListFile
import students.Student

class StudentListFileTest {
    private val sl = StudentListFile()

    @Test fun testTxt() {
        sl.readFile("./src/test/resources/in.txt", StudentTxtReader())
        sl.writeToFile("./src/test/resources/out.txt", StudentTxtWriter())
    }

    @Test fun testJson() {
        sl.readFile("./src/test/resources/in.json", StudentJsonReader())
        sl.writeToFile("./src/test/resources/out.json", StudentJsonWriter())
    }

    @Test fun testYaml() {
        sl.readFile("./src/test/resources/in.yaml", StudentYamlReader())
        sl.writeToFile("./src/test/resources/out.yaml", StudentYamlWriter())
    }

    @Test fun testGetByPage() {
        sl.getByPage(1, 1)
        sl.getByPage(2, 1)
    }

    @Test fun testAdd() {
        sl.add(
            Student(
                4,
                "Канеки",
                "Кен",
                "Адольфович"
            )
        )
    }

    @Test fun testOrderBy() {
        sl.orderBySurnameAndInitials()
    }

    @Test fun testRemove() {
        sl.remove(4)
    }

    @Test fun testReplace() {
        sl.update(
            Student(4, "Ризе", "Зинаида", "Георгиевна"),
            4
        )
    }
}
