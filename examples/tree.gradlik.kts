task("root") {
    outputs {
        file("root 1")
        file("root 2")
        file("root 3")
    }
}

task("n1") {
    inputs {
        task("root")
    }

    runCode {
        println("n1 files: $${inputFiles()}")
    }

    outputs {
        file("root 1")
        file("n1 2")
    }
}

task("n2") {
    inputs {
        task("n1")
    }

    runCode {
        println("n2 files: $${inputFiles()}")
    }

    outputs {
        file("root 1")
        file("n1 2")
        file("n2 3")
    }
}

task("n3") {
    inputs {
        task("n1")
    }

    runCode {
        println("n3 files: $${inputFiles()}")
    }

    outputs {
        file("root 1")
        file("n1 2")
        file("n3 3")
    }
}

task("n4") {
    inputs {
        task("root")
    }

    runCode {
        println("n4 files: ${inputFiles()}")
    }

    outputs {
        file("root 1")
        file("root 2")
        file("root 3")
    }
}

task("n5") {
    inputs {
        task("n4")
        task("n2")
    }

    runCode {
        println("n5 files: $${inputFiles()}")
    }

    outputs {
        file("root 1")
    }
}
