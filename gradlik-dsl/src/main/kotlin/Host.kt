import exceptions.CyclicDependencyException
import exceptions.TaskExecutionException
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import mu.KotlinLogging
import project.ProjectImpl
import java.io.File
import java.util.*
import kotlin.script.experimental.api.ScriptDiagnostic

fun main(vararg args: String) {
    gradlikMain(*args)
}

fun gradlikMain(vararg args: String) {
    val parser = ArgParser("Gradlik")

    val task by parser.argument(ArgType.String, "task")
    val logger = KotlinLogging.logger {}

    parser.parse(args)

    val taskByColon = task.split(":")

    val (path, srcTask) = if (taskByColon.size == 1) {
        listOf(taskByColon[0], "Main")
    } else taskByColon

    logger.debug("Input args: path='$path', task='$srcTask'")

    println(System.getProperty("user.dir"))

    val scriptFile = File(path)
    if (!scriptFile.exists() || !scriptFile.isFile || scriptFile.extension != "kts") {
        val reason = when {
            !scriptFile.exists() -> "file does not exist"
            !scriptFile.isFile -> "it is not a file"
            scriptFile.extension != "kts" -> "it is not a script"
            else -> "something wrong :("
        }
        logger.error { "Given file '$path' is invalid! Possible reason: $reason. Please, provide a valid path to '.gradlik.kts' file." }
        return
    }

    val project = ProjectImpl()

    logger.debug("Preparing to evaluate script '$path'")

    val result = ProjectScriptHost.eval(scriptFile.readText(), project)

    result.reports.forEach {
        if (it.severity >= ScriptDiagnostic.Severity.ERROR) {
            logger.error {
                "Critical error occurred in the script, find details below:\n\t${it.message}"
            }
            return
        }

        if (it.severity == ScriptDiagnostic.Severity.WARNING) {
            logger.debug {
                "Warning message in the script:\n\t${it.message}"
            }
        }
    }

    logger.debug { "Report is fine, creating a graph with source task='$srcTask'" }

    val executionOrder: Stack<String>
    try {
        executionOrder = project.assembleGraph(srcTask)
    } catch (e: CyclicDependencyException) {
        logger.error { e.message }
        return
    }

    logger.debug { "Executing tasks in the given order:\n\t${executionOrder.reversed()}" }

    try {
        project.executeGraph(executionOrder)
    } catch (e: TaskExecutionException) {
        logger.error { "Execution of task '${e.task}' interrupted with an exception, message:\n\t${e.message}" }
        return
    }
}
