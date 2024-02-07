import java.time.LocalDate
enum class Status{
    ACTIVE,
    COMPLETED
}

data class Task(val name: String, val description: String, val deadLine: LocalDate, var status: Status = Status.ACTIVE )