package project

import exceptions.CyclicDependencyException
import exceptions.CyclicGraphException
import exceptions.TaskExecutionException
import graph.Graph
import project.lists.TaskList
import java.lang.RuntimeException
import java.util.*

class ProjectImpl : Project {
    private val tasks: TaskList = TaskList()
    private val graph: Graph = Graph()

    @Throws(CyclicDependencyException::class)
    fun assembleGraph(sourceTask: String): Stack<String> {
        if (sourceTask == "Main") {
            tasks.getAllTasks().forEach {
                graph.addTask(it)
            }
        } else {
            graph.addTask(tasks.getByName(sourceTask)!!)
        }

        val executionOrder: Stack<String>
        try {
            executionOrder = graph.topSort()
        } catch (e: CyclicGraphException) {
            throw CyclicDependencyException(
                e.node,
                tasks.getByName(e.node)!!.dependencyList.dependsOnNames.filter { it != "Main" })
        }

        executionOrder.pop() // pop "Main" task

        return executionOrder
    }

    fun executeGraph(executionOrder: Stack<String>) {
        while (executionOrder.isNotEmpty()) {
            val task = executionOrder.pop()
            tasks.getByName(task)!!.inputList.combineInputs(tasks)
            try {
                tasks.getByName(task)!!.run()
            } catch (e: Exception) {
                throw TaskExecutionException(task, e.message, e.cause)
            }
        }
    }

    override fun task(name: String, config: Task.Builder.() -> Unit) {
        tasks.register(name, config)
    }
}