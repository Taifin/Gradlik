task("one") {
    inputs {
        file("a")
        file("b")
    }

    outputs {
        file("c")
    }

    runCode {
        println("Task one")
    }
}

task("two") {
    inputs {
        task("one")
    }

    runCode {
        println("Task two")
    }
}

task("three") {
    inputs {
        task("one")
    }

    runCode {
        println("Task three")
    }
}

task("four") {
    inputs {
        task("two")
        task("three")
    }

    runCode {
        println("Task four")
    }
}