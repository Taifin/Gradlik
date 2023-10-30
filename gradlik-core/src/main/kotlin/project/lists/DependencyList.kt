package project.lists

class DependencyList {
    val dependsOnNames: MutableSet<String> = mutableSetOf("Main")

    fun task(name: String) {
        dependsOnNames.add(name)
    }
}