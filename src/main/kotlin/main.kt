import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timerTask

fun main() {
    val scanner = Scanner(System.`in`)
    val scheduler = Scheduler()


    while (true) {
        println("1. Добавить новую задачу. ")
        println("2. Список всех задач. ")
        println("3. Отметить задачу как выполненную. ")
        println("4. Удалить задачу. ")
        println("5. Выгрузить список в файл. ")
        println("6. Показать текущее время и ближайший к нему дедлайн")
        println("0. Закрыть приложение. ")
        println()
        print("Введите номер команды (1-6): ")

        when (scanner.nextInt()) {
            1 -> {
                try {
                    scanner.nextLine()
                    print("Введите название задачи: ")
                    val name = scanner.nextLine()
                    print("Введите описание задачи: ")
                    val description = scanner.nextLine()
                    print("Введите срок выполнения задачи в формате гггг-мм-дд: ")
                    val deadLine = LocalDate.parse(scanner.next())

                    scheduler.addTask(name, description, deadLine)
                    println()
                } catch (e: DateTimeException) {
                    println("Ошибка ввода даты! Следует ввести в формате гггг-мм-дд: ")
                }

            }

            2 -> {
                println("------  Список всех задач  ------")
                println()
                scheduler.viewTasks()
                println()
                println("Отфильтровать задачи?")
                println("1. Активные \n2. Выполненные \n3. Не фильтровать (Показать все)")
                print("(1-3): ")
                scanner.nextLine()
                when (scanner.nextInt()) {
                    1 -> scheduler.viewTasks(Status.ACTIVE)
                    2 -> scheduler.viewTasks(Status.COMPLETED)
                    3 -> scheduler.viewTasks()
                }
                println("==================================")
            }

            3 -> {
                print("Введите индекс задачи для отметки выполненной: ")
                scheduler.markTaskAsCompleted(scanner.nextInt())
                scanner.nextLine()

            }

            4 -> {
                print("Введите индекс задачи для удаления:")
                scheduler.deleteTask(scanner.nextInt())
                scanner.nextLine()
            }

            5 -> {
                val taskList = Scheduler()
                scheduler.saveTasksToFile(taskList.taskList, "newFileScheduler")
                println("Список успешно выгружен")
            }

            6 -> {
                println("adasdasdasdasfknsdgkos")
                scheduler.showAllDatesFromTaskList()
                // val result = formattedDate.compareTo()
            }

            0 -> {
                println("До скорой встречи!")
                return
            }
            else -> println("Неверная команда!")
        }
    }
}




