package project.lists

class InputList {
    private val tasksInputs: MutableList<String> = mutableListOf()
    private val filesInputs: MutableList<String> = mutableListOf()
    val combinedInputs: MutableSet<String> = mutableSetOf()

    inner class Configuration {
        fun task(name: String) = tasksInputs.add(name)
        fun file(name: String) = filesInputs.add(name)
    }

    fun combineInputs(tasks: TaskList) {
        tasksInputs.forEach {
            combinedInputs.addAll(tasks.getOutputsByName(it))
        }

        combinedInputs.addAll(filesInputs)
    }

    fun getTasks() = tasksInputs
}