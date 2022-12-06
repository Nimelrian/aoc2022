package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        indexOfFirstDistinctWindow(lines, 4)
    }

    part("/input") { lines ->
        indexOfFirstDistinctWindow(lines, 14)
    }
}

private fun indexOfFirstDistinctWindow(lines: Sequence<String>, windowSize: Int) =
    lines.flatMap { it.windowedSequence(windowSize) }
        // add windowSize since we get the index of the window, not of the last character in the window
        .indexOfFirst { window -> window.toSet().size == windowSize } + windowSize
