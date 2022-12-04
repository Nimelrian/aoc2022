package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        lines.map { line ->
            line.split(',')
                .map { it.toIntRange() }
        }
            .count { (first, second) -> doRangesOverlapCompletely(first, second) }
    }

    part("/input") { lines ->
        lines.map { line ->
            line.split(',')
                .map { it.toIntRange() }
        }
            .count { (first, second) -> doRangesOverlap(first, second) }
    }
}

fun String.toIntRange(): IntRange {
    val (start, end) = this.split('-')
    return IntRange(start.toInt(), end.toInt())
}

fun doRangesOverlapCompletely(first: IntRange, second: IntRange) = first.isSubrangeOf(second) || second.isSubrangeOf(first)
fun doRangesOverlap(first: IntRange, second: IntRange) = first.overlaps(second)

fun IntRange.isSubrangeOf(other: IntRange): Boolean {
    return other.contains(this.first) && other.contains(this.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return other.contains(this.last)
            || other.contains(this.first)
            || this.contains(other.first)
            || this.contains(other.last)
}
