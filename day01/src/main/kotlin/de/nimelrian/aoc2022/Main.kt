package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        val wholeInput = lines.joinToString("\n")
        wholeInput.split("\n\n")
            .map { elfSnacks ->
                elfSnacks.split("\n")
                    .map(String::toInt)
                    .sum()
            }
            .max()
    }

    part("/input") { lines ->
        val wholeInput = lines.joinToString("\n")
        wholeInput.split("\n\n")
            .asSequence()
            .map { elfSnacks ->
                elfSnacks.split("\n")
                    .map(String::toInt)
                    .sum()
            }
            .sortedDescending()
            .take(3)
            .sum()
    }
}
