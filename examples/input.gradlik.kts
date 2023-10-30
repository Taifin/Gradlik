task("incorrect") {
    inputs {
        file("a.kts")

        runCode {
            println(inputFile("b.kts"))
        }
    }
}