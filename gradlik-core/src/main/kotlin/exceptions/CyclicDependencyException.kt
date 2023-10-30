package exceptions

class CyclicDependencyException(task: String, directDependencies: List<String>) :
    Exception("Cyclic dependency found with task '$task'! Direct dependencies are: $directDependencies")