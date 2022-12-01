package de.nimelrian.aoc2022

typealias Solver = (Sequence<String>) -> Any

class Aoc {
    private val parts = arrayListOf<AocPart>()

    fun part(inputClasspath: String, solver: Solver): AocPart {
        val part = AocPart(inputClasspath, solver)
        parts.add(part)
        return part
    }

    fun run() {
        parts.forEachIndexed { index, part ->
            val solution = part.run()
            println("Solution for part ${index + 1}: $solution")
        }
    }
}

class AocPart(
    private val input: String,
    private val solver: Solver,
) {
    fun run(): Any =
        (javaClass.getResourceAsStream(input) ?: throw IllegalArgumentException("No resource on classpath at $input"))
            .bufferedReader()
            .lineSequence()
            .let(solver)
}

fun aoc(init: Aoc.() -> Unit) = Aoc().run {
    init()
    run()
}
