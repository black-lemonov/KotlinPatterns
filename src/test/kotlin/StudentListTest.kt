import org.junit.jupiter.api.Test

import strategy.readers.StudentJsonReader
import strategy.readers.StudentTxtReader
import strategy.readers.StudentYamlReader
import strategy.writers.StudentJsonWriter
import strategy.writers.StudentTxtWriter
import strategy.writers.StudentYamlWriter
import strategy.StudentList

class StudentListTest {
    private val sl = StudentList()

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
}
