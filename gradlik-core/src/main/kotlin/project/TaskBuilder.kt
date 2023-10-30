package project

object TaskBuilder {
    fun build(name: String, config: Task.Builder.() -> Unit): Task {
        val task = Task(name)
        task.Builder().config()
        return task
    }
}