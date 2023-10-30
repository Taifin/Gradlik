package project.lists

import project.Task
import project.TaskBuilder

class TaskList {
    private val tasks: MutableMap<String, Task> = mutableMapOf()

    fun register(name: String, config: Task.Builder.() -> Unit) {
        tasks[name] = TaskBuilder.build(name, config)
    }

    fun getByName(name: String): Task? = tasks[name]

    fun getOutputsByName(name: String): List<String> = tasks[name]!!.outputList.getOutputs()

    fun getAllTasks() = tasks.values
}