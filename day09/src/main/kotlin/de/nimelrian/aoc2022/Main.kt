package de.nimelrian.aoc2022

import de.nimelrian.aoc2022.Vector.Companion.DOWN
import de.nimelrian.aoc2022.Vector.Companion.LEFT
import de.nimelrian.aoc2022.Vector.Companion.RIGHT
import de.nimelrian.aoc2022.Vector.Companion.UP
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

fun main() = aoc {
    part("/input") { lines ->
        solveForRopeLength(2, lines)
    }
    part("/input") { lines ->
        solveForRopeLength(10, lines)
    }
}

private fun solveForRopeLength(ropeLength: Int, lines: Sequence<String>): Int {
    val seen = mutableSetOf(Vector(0, 0))
    val rope = List(ropeLength) { Vector(0, 0) }
    for (line in lines) {
        val (direction, count) = parseMove(line)
        repeat(count) {
            applyMove(rope, direction)
            seen.add(rope.last().copy())
        }
    }
    return seen.size
}

data class Move(val direction: Vector, val count: Int)

data class Vector(var x: Int, var y: Int) {
    companion object {
        val UP = Vector(0, 1)
        val RIGHT = Vector(1, 0)
        val DOWN = Vector(0, -1)
        val LEFT = Vector(-1, 0)
    }
    operator fun plusAssign(other: Vector) {
        this.x += other.x
        this.y += other.y
    }

    operator fun plus(other: Vector): Vector {
        return Vector(this.x + other.x, this.y + other.y)
    }
}

private fun parseMove(line: String): Move {
    val (directionString, countString) = line.split(' ')
    val direction = when (directionString) {
        "U" -> UP
        "R" -> RIGHT
        "D" -> DOWN
        "L" -> LEFT
        else -> throw IllegalArgumentException("Unknown direction $directionString")
    }
    return Move(direction, countString.toInt())
}

private fun applyMove(rope: List<Vector>, move: Vector) {
    rope.first() += move
    rope.zipWithNext().forEach { (current, next) ->
        val xDistance = current.x - next.x
        val yDistance = current.y - next.y
        if (max(xDistance.absoluteValue, yDistance.absoluteValue) > 1) {
            next += Vector(xDistance.sign, yDistance.sign)
        }
    }
}
