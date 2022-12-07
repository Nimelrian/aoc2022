package de.nimelrian.aoc2022

import java.lang.IllegalArgumentException
import kotlin.collections.ArrayDeque

fun main() = aoc {
    part("/input") { lines ->
        val sizesByPath = getDirectorySizes(lines)
        sizesByPath.values.filter { it < 100000 }.sum()
    }

    part("/input") { lines ->
        val sizesByPath = getDirectorySizes(lines)

        val used = sizesByPath[""]!!
        val total = 70_000_000L
        val free = total - used
        val needToFree = 30_000_000L - free

        sizesByPath.values.asSequence()
            .filter { it >= needToFree }
            .min()
    }
}

private fun getDirectorySizes(lines: Sequence<String>): MutableMap<String, Long> {
    val sizesByPath = mutableMapOf<String, Long>()

    lines.chunkByPredicate { it[0] == '$' } // This splits the input stream into chunks, with the command as the head and the output as the tail
        .drop(1) // The first chunk will always be empty, since the first input line is a command
        .fold(ArrayDeque<String>()) { path, chunk ->
            val commandString = chunk.first()
            val output = chunk.drop(1)

            val components = commandString.split(' ')
            val (_, command) = components
            val arg = components.getOrNull(2)
            when {
                command == "cd" && arg == "/" -> path.apply { clear() }
                command == "cd" && arg == ".." -> path.apply { removeLast() }
                command == "cd" -> path.apply { addLast(arg!!) }
                command == "ls" -> {
                    for (line in output) {
                        val (data, _) = line.split(' ')
                        if (data == "dir") {
                            continue
                        }

                        val size = data.toLong()

                        path.runningFold("") { cwd, child -> "$cwd/$child" }
                            .forEach { cwd ->
                                sizesByPath.merge(cwd, size, Long::plus)
                            }
                    }
                    path
                }

                else -> throw IllegalArgumentException("Unexpected command")
            }
        }
    return sizesByPath
}
