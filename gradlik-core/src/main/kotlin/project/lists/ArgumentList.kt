package project.lists

class ArgumentList {
    val arguments: MutableList<String> = mutableListOf()

    fun arg(name: String) {
        arguments.add(name)
    }

    fun input(vararg names: String) {
        arguments.addAll(names.toList())
    }
}