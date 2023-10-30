import project.Project
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "gradlik.kts",
    compilationConfiguration = ScriptWithGradlikConfiguration::class
)
abstract class GradlikScript(project: Project) : Project by project

object ScriptWithGradlikConfiguration : ScriptCompilationConfiguration(
    {
        baseClass(GradlikScript::class)

        defaultImports(Project::class, java.io.File::class)

        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }

        ide {
            acceptedLocations(ScriptAcceptedLocation.Everywhere)
        }
    }
)


