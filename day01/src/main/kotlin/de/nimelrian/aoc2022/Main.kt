package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        lines.chunkByPredicate(String::isEmpty)
            .map { elfSnacks ->
                elfSnacks.asSequence()
                    .filterNot { it.isEmpty() }
                    .sumOf { it.toInt() }
            }
            .max()
    }

    part("/input") { lines ->
        lines.chunkByPredicate(String::isEmpty)
            .map { elfSnacks ->
                elfSnacks.asSequence()
                    .filterNot { it.isEmpty() }
                    .sumOf { it.toInt() }
            }
            .sortedDescending()
            .take(3)
            .sum()
    }
}
