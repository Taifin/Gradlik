package exceptions

class UndeclaredInputException(task: String, input: String) :
    Exception("Usage of undeclared input `$input` in task `$task`!")