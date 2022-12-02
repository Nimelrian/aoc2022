package de.nimelrian.aoc2022

import java.lang.IllegalArgumentException

fun main() = aoc {
    part("/input") { lines ->
        lines.map { StrategyRound.from(it) }
            .map { getPointsForRound(it) }
            .sum()
    }
}

data class StrategyRound(val opponentShape: Shape, val playerShape: Shape) {
    companion object : From<String, StrategyRound> {
        override fun from(source: String): StrategyRound {
            val (opponent, player) = source.split(" ")

            return StrategyRound(
                Shape.from(opponent),
                Shape.from(player),
            )
        }
    }
}

fun getPointsForRound(round: StrategyRound): Int {
    val resultPoints = getResultForRound(round).points
    return resultPoints + round.playerShape.points
}

fun getResultForRound(round: StrategyRound): RoundResult {
    val (opponent, player) = round
    if (opponent == player) {
        return RoundResult.Draw
    }

    return when (Pair(player, opponent)) {
        Shape.Paper to Shape.Rock,
        Shape.Rock to Shape.Scissors,
        Shape.Scissors to Shape.Paper -> RoundResult.Win
        else -> RoundResult.Loss
    }
}

sealed interface RoundResult{
    val points: Int

    object Win: RoundResult {
        override val points: Int = 6
    }
    object Draw: RoundResult {
        override val points: Int = 3
    }
    object Loss: RoundResult {
        override val points: Int = 0
    }
}

sealed interface Shape {
    companion object : From<String, Shape> {
        override fun from(source: String): Shape {
            return when (source) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissors
                else -> throw IllegalArgumentException("Unknown input $source")
            }
        }
    }

    val points: Int

    object Rock: Shape {
        override val points: Int = 1
    }
    object Paper: Shape {
        override val points: Int = 2
    }
    object Scissors: Shape {
        override val points: Int = 3
    }
}
