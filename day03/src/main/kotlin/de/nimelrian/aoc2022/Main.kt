package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        lines.map { line ->
            val pivot = line.length / 2
            val (first, second) = line.chunked(pivot)

            val itemsInFirst = first.toSet()
            val itemsInSecond = second.toSet()
            val itemsInBoth = itemsInFirst.intersect(itemsInSecond)
            itemsInBoth.single()
        }
            .map { item ->
                if (item.isUpperCase()) {
                    item - 'A' + 1 + 26
                } else {
                    item - 'a' + 1
                }
            }
            .sum()
    }

    part("/input") { lines ->
        lines.chunked(3)
            .map { group ->
                val (first, second, third) = group
                val itemsInFirst = second.toSet()
                val itemsInSecond = first.toSet()
                val badge = third.toSet().intersect(itemsInFirst).intersect(itemsInSecond)
                badge.single()
            }
            .map { item ->
                if (item.isUpperCase()) {
                    item - 'A' + 1 + 26
                } else {
                    item - 'a' + 1
                }
            }
            .sum()
    }
}
