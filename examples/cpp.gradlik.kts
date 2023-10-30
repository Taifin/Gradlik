import java.io.File

task("Create file") {
    outputs {
        file("a.cpp")
    }

    runCode {
        val file = File("a.cpp")
        file.createNewFile()
        file.writeText("#include <iostream>\nint main() {\n \tstd::cout << \"Hello, World!\" << std::endl;\n \treturn 0;\n }")
    }
}

task("Build file") {
    inputs {
        task("Create file")
    }

    outputs {
        file("a.out")
    }


    runProcess("g++") {
        arg("-oa.out")
        input(inputFile("a.cpp"))
    }

}

task("Execute file") {
    dependsOn {
        task("Build file")
    }

    runProcess("./a.out")
}

