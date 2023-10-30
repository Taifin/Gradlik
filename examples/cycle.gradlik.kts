task("1") {
    dependsOn {
        task("10")
    }

    runCode {
        println("1")
    }
}

for (i in 2..10) {
    task(i.toString()) {
        dependsOn { task((i - 1).toString()) }

        runCode {
            println(i)
        }
    }
}


