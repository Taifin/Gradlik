package project

import exceptions.ProcessReturnCodeException
import exceptions.UndeclaredInputException
import project.lists.*

class Task(val name: String) {
    val dependencyList: DependencyList = DependencyList()
    val inputList: InputList = InputList()
    val outputList: OutputList = OutputList()

    private val actions: MutableList<() -> Unit> = mutableListOf()

    inner class Builder {
        fun dependsOn(config: DependencyList.() -> Unit) = dependencyList.config()

        fun inputs(config: InputList.Configuration.() -> Unit) {
            inputList.Configuration().config()
            dependencyList.dependsOnNames.addAll(inputList.getTasks())
        }

        fun outputs(config: OutputList.() -> Unit) = outputList.config()

        fun inputFile(name: String): String {
            if (name !in inputList.combinedInputs) {
                throw UndeclaredInputException(this@Task.name, name)
            }

            return name
        }

        fun inputFiles(): Set<String> = inputList.combinedInputs

        fun runProcess(name: String, config: ArgumentList.() -> Unit = {}) = actions.add {
            val argumentList = ArgumentList()
            argumentList.config()

            val processBuilder =
                ProcessBuilder(name, *argumentList.arguments.toTypedArray())
            val process = processBuilder.start()

            process.waitFor()

            if (process.exitValue() != 0) {
                throw ProcessReturnCodeException(name, process.exitValue(), process.errorReader().use { it.readText() })
            }

            println("[GRADLIK]:$name:stdout:\n\t${process.inputReader().use { it.readText() }}")
        }

        fun runCode(code: () -> Unit) = actions.add(code)
    }

    fun run() {
        actions.forEach {
            it.invoke()
        }
    }
}
