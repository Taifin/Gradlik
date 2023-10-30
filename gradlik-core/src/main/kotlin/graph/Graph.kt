package graph

import project.Task
import exceptions.CyclicGraphException
import java.util.*

class Graph {
    private val nodes: MutableMap<String, Node> = mutableMapOf()

    private fun addNode(task: String) {
        if (!nodes.containsKey(task)) {
            nodes[task] = Node(task, mutableListOf())
        }
    }

    fun addTask(task: Task) {
        addNode(task.name)

        task.dependencyList.dependsOnNames.forEach {
            addNode(it)

            nodes[it]!!.children.add(task.name)
        }
    }

    fun topSort(): Stack<String> {
        val stack = Stack<String>()
        val color = mutableMapOf<String, Int>()

        nodes.keys.forEach {
            color[it] = 0
        }

        topSortImpl("Main", stack, color)

        return stack
    }

    @Throws(CyclicGraphException::class)
    private fun topSortImpl(node: String, stack: Stack<String>, color: MutableMap<String, Int>) {
        if (color[node]!! == 1) {
            throw CyclicGraphException(node)
        }

        if (color[node]!! == 2) {
            return
        }

        color[node] = 1

        nodes[node]!!.children.forEach {
            topSortImpl(it, stack, color)
        }

        color[node] = 2
        stack.add(node)
    }
}