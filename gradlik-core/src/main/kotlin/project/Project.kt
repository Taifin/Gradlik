package project

interface Project {
    fun task(name: String, config: Task.Builder.() -> Unit)
}
