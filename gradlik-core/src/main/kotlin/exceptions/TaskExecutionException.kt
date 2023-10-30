package exceptions

class TaskExecutionException(val task: String, message: String?, cause: Throwable?) : Exception(message, cause)