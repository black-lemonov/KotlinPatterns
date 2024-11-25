## Диаграммы классов 
### Лaб. 1
#### Класс Student
```mermaid
classDiagram
class Student {
    -UInt? _id
    -String _surname
    -String _name
    -String _lastname
    -String? _phone
    -String? _tg
    -String? _email
    -String? _git

    +UInt? id
    +String surname
    +String name 
    +String lastname
    +String? phone
    +String? tg
    +String? email
    +String? git

    +Student(String surname, String name, String lastname)
    +Student(UInt? id, String surname, String name, String lastname, String? phone, String? tg, String? email, String? git)
    +Student(Map~String, Any~ params)
  
    +setContacts(String? phone, String? tg, String? email)

    +validate()
    +hasGit() Boolean
    +hasContact()
    
    +Regex PART_OF_NAME_REGEX$
    +Regex PHONE_REGEX$
    +Regex TG_REGEX$
    +Regex EMAIL_REGEX$
    +Regex GIT_REGEX$

    +checkSurname(String surname)$
    +checkName(String name)$
    +checkLastname(String lastname)$
    +checkPhone(String? phone)$
    +checkTg(String? tg)$
    +checkEmail(String? email)$
    +checkGit(String? git)$
}
```
### Лаб.2
#### Рефакторинг, Student_short и базовый класс
```mermaid
classDiagram
Student ..|> StudentBase
Student_short ..|> StudentBase
Student_short ..> Student
class Student {
    -UInt? _id
    -String _surname
    -String _name
    -String _lastname
    -String? _phone
    -String? _tg
    -String? _email
    -String? _git

    +Student(String surname, String name, String lastname)
    +Student(UInt? id, String surname, String name, String lastname, Stirng? phone, String? tg, String? email, String? git)
    +Student(Map~String, Any?~ params)
    +Student(String params)

    -validate()
    -hasGit() Boolean
    -hasContact() Boolean
    
    +toString() String

    +getId() UInt?
    +getSurnameAndInitials() String
    +getGitInfo() String
    +getContactsInfo() String

    +setContacts(String? phone, String? tg, String? email)

    +Regex PART_OF_NAME_REGEX$
    +Regex PHONE_REGEX$
    +Regex TG_REGEX$
    +Regex EMAIL_REGEX$
    +Regex GIT_REGEX$

    +checkSurname(String surname)$
    +checkName(String name)$
    +checkLastname(String lastname)$
    +checkPhone(String? phone)$
    +checkTg(String? tg)$
    +checkEmail(String? email)$
    +checkGit(String? git)$
}
class Student_short {
    -UInt? _id
    -String _surnameAndInitials
    -String? _git
    -String? _contacts

    +Student_short(Uint? id, String info)
    +Student_short(Student student)

    -fetchInfo(String info) List~String?~

    +getId() UInt?
    +getSurnameAndInitials() String
    +getGitInfo() String
    +getContactsInfo() String
}
class StudentBase {
    <<Abstract>>
    +getInfo() String

    +getId()* UInt?
    +getSurnameAndInitials()* String
    +getGitInfo()* String
    +getContactsInfo()* String
}
```
#### Классы Data_table, Data_list и Data_list_student_short
```mermaid
classDiagram
Data_list ..> Data_table
Data_list_student_short ..|> Data_list
namespace template {
  class Data_table {
      +Int rows
      +Int columns
  
      -List~List~Any~~ _data
  
      +Data_table(List~List~Any~~ data)
  
      +get(Int row, Int column) Any
  }
  class Data_list {
      <<Abstract>>
      +get_selected() List~Int~
      +get_data() Data_table
      #get_names() List~String~
  
      +select(Int index)*
      #getNamesWithoutID()* List~String~
      #getId(Int index)* Any
      #getRest(Int index)* List~Any~
  }
  class Data_list_student_short {
      -MutableList~Student_short~ _data
      -MutableList~Int~ _selected
  
      +Data_list_student_short(List~Student_short~ data)
      
      +select(Int index)
      +get_selected() List~Int~
      #getNamesWithoutID() List~String~
      #getId(Int index) Any
      #getRest(Int index) List~Any~
  }
}
```
### Лаб. 3
#### Классы Students_list и наследники (txt, json, yaml)
```mermaid
classDiagram
Students_list_txt --|> Students_list
Students_list_JSON --|> Students_list
Students_list_YAML --|> Students_list
class Students_list {
    <<Abstract>>
    -MutableList~Student~ _data

    +read_from_file(String filepath)*
    +write_to_file(String destpath)*
    +get(UInt id) Student
    +get_k_n_student_short_list(Int page, Int number) Data_list
    +sortBySurnameAndInitials()
    +add(Student newStudent)
    +add(Student newStudent, Uint id)
    +replaceWhere(Student newStudent, Uint id)
    +deleteWhere(UInt id)
    +get_student_short_count() Int
}
class Students_list_txt {
    +read_from_file(String filepath)
    +write_to_file(String destpath)
}
class Students_list_JSON {
    +read_from_file(String filepath)
    +write_to_file(String destpath)
}
class Students_list_YAML {
    +read_from_file(String filepath)
    +write_to_file(String destpath)
}
```
#### Применение паттерна "Стратегия"
```mermaid
classDiagram
Student_list --> StudentFileReader
class Student_list {
    Student_list(MutableList~Student~ data, StudentFileReader fileReader)

    +readFile(String filepath)
    +writeToFile(String filepath)
    +get(UInt id) Student
    +getByPage(Int page, Int number) Data_list
    +orderBySurnameAndInitials()
    +addStudent(Student newStudent)
    -addStudent(Student newStudent,UInt id)
    +updateStudent(Student newStudent, UInt id)
    +deleteStudent(UInt id)
    +countAll() Int
}
StudentJsonReader ..|> StudentFileReader
StudentYamlReader ..|> StudentFileReader
StudentTxtReader ..|> StudentFileReader
namespace readers {
class StudentFileReader {
    <<Interface>>
    +readFromFile(String filepath)* List~Student~
    +writeToFile(String filepath, List~Student~ students)*
}
class StudentJsonReader {
    +readFromFile(String filepath) List~Student~
    +writeToFile(String filepath, List~Student~ students)
}
class StudentTxtReader {
    +readFromFile(String filepath) List~Student~
    +writeToFile(String filepath, List~Student~ students)
}
class StudentYamlReader {
    +readFromFile(String filepath) List~Student~
    +writeToFile(String filepath, List~Student~ students)
}
}
```
