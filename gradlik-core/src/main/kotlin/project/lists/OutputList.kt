package project.lists

class OutputList {
    private val filesOutputs: MutableList<String> = mutableListOf()

    fun file(name: String) = filesOutputs.add(name)

    fun getOutputs() = filesOutputs
}