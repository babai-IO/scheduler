import jdk.jfr.Description
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
val reset = "\u001B[0m"
val green = "\u001B[32m"
class Scheduler {

    val taskList: MutableList<Task> = mutableListOf()

    fun addTask(name: String, description: String, deadline: LocalDate) {
        taskList.add(Task(name, description, deadline))
        println()
        println("${green}Задача успешно добавлена.$reset")
    }


    fun viewTasks(filter: Status? = null) {
        val filteredTasks = if (filter != null)
            taskList.filter { it.status == filter } else taskList
        if (filteredTasks.isEmpty()) {
            println("Список задач пуст.")
        } else {
            filteredTasks.forEachIndexed {index, task ->
                println("$index. ${task.description} - Дедлайн: ${task.deadLine} - Статус: ${task.status}")

            }
        }
    }


    fun deleteTask(index: Int) {
        if (index in 0 until taskList.size) {
            taskList.removeAt(index)
            println("Задача успешно удалена!")
        }
    }

    fun showAllDatesFromTaskList() {
        val currentDate = LocalDate.now()
        taskList.forEachIndexed { index, task ->

            val deadlineDateString = task.deadLine
            val deadlineDate = LocalDate.parse(deadlineDateString.toString(), DateTimeFormatter.ISO_DATE)

            if (currentDate.isBefore(deadlineDate)) {
                val daysRemaining = ChronoUnit.DAYS.between(currentDate, deadlineDate)
                if (daysRemaining < 2){
                    println("Индекс: $index: Наименование задачи: ${task.name} :ВСЕ, ЧТО ДЕЛАЕТСЯ В ПЯТНИЦУ ВЕЧЕРОМ - ПЕРЕДЕЛЫВАЕТСЯ В ПОНДЕЛЬНИК УТРОМ ")
                }else
                println("До дедлайна задачи с индексом $index и названием ${task.name} осталось $daysRemaining дней.")
            } else {
                println("Дедлайн задачи $index ${task.name} уже прошел.")
            }

        }
        }

        fun markTaskAsCompleted(index: Int) {
            if (index in 0 until taskList.size) {
                taskList[index].status = Status.COMPLETED
                println("Задача отмечена как выполненная.")
            } else {
                println("Недопустимый индекс задачи.")
            }
        }


        fun saveTasksToFile(taskList: MutableList<Task>, fileName: String) {
            try {
                val fileOutputStream = FileOutputStream(fileName)
                val objectOutputStream = ObjectOutputStream(fileOutputStream)

                objectOutputStream.writeObject(taskList)

                objectOutputStream.close()
                fileOutputStream.close()

                println("Список задач успешно сохранен в файл $fileName.")
            } catch (e: Exception) {
                println("Ошибка при сохранении списка задач в файл: ${e.message}")
            }
        }

    }
