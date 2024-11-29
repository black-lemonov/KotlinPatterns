import org.junit.jupiter.api.Test

import strategy.readers.StudentJsonReader
import strategy.readers.StudentTxtReader
import strategy.readers.StudentYamlReader
import strategy.writers.StudentJsonWriter
import strategy.writers.StudentTxtWriter
import strategy.writers.StudentYamlWriter
import strategy.StudentList

class StudentListTest {
    private val baseDir = "/home/egorp/MyProgs/uni/KotlinPatterns/src/test/resources"
    private val sl = StudentList()

    @Test fun testTxt() {
        sl.readFile("$baseDir/in.txt", StudentTxtReader())
        sl.writeToFile("$baseDir/out.txt", StudentTxtWriter())
    }

    @Test fun testJson() {
        sl.readFile("$baseDir/in.json", StudentJsonReader())
        sl.writeToFile("$baseDir/out.json", StudentJsonWriter())
    }

    @Test fun testYaml() {
        sl.readFile("$baseDir/in.yaml", StudentYamlReader())
        sl.writeToFile("$baseDir/out.yaml", StudentYamlWriter())
    }
}
