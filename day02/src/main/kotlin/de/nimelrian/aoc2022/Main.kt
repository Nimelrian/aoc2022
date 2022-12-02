package de.nimelrian.aoc2022

import de.nimelrian.aoc2022.RoundResult.*
import de.nimelrian.aoc2022.Shape.*
import java.lang.IllegalArgumentException

fun main() = aoc {
    part("/input") { lines ->
        lines.map { parseRoundForPart1(it) }
            .map { getPointsForRound(it) }
            .sum()
    }

    part("/input") { lines ->
        lines.map { parseRoundForPart2(it) }
            .map { getPointsForRound(it) }
            .sum()
    }
}

fun parseRoundForPart1(strategy: String): Round {
    val (opponent, player) = strategy.split(" ")

    return Round(
        parseShapeForPart1(opponent),
        parseShapeForPart1(player),
    )
}

fun parseShapeForPart1(shapeCharacter: String): Shape {
    return when (shapeCharacter) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissors
        else -> throw IllegalArgumentException("Unknown input $shapeCharacter")
    }
}

fun parseRoundForPart2(strategy: String): Round {
    val (opponent, expected) = strategy.split(" ")

    val opponentShape = parseShapeForPart2(opponent)
    val expectedResult = getExpectedResultForRound(expected)
    val requiredShape = getRequiredShapeForResult(opponentShape, expectedResult)
    return Round(
        opponentShape,
        requiredShape,
    )
}

fun parseShapeForPart2(shapeCharacter: String): Shape {
    return when (shapeCharacter) {
        "A" -> Rock
        "B" -> Paper
        "C" -> Scissors
        else -> throw IllegalArgumentException("Unknown input $shapeCharacter")
    }
}

fun getExpectedResultForRound(resultCharacter: String): RoundResult {
    return when (resultCharacter) {
        "X" -> Loss
        "Y" -> Draw
        "Z" -> Win
        else -> throw IllegalArgumentException("Unknown input $resultCharacter")
    }
}

fun getRequiredShapeForResult(opponentShape: Shape, roundResult: RoundResult): Shape {
    if (roundResult == Draw) {
        return opponentShape
    }

    return when (Pair(opponentShape, roundResult)) {
        Scissors to Win,
        Paper to Loss -> Rock
        Rock to Win,
        Scissors to Loss -> Paper
        else -> Scissors
    }
}

data class Round(val opponentShape: Shape, val playerShape: Shape)

fun getPointsForRound(round: Round): Int {
    val resultPoints = getResultForRound(round).points
    return resultPoints + round.playerShape.points
}

fun getResultForRound(round: Round): RoundResult {
    val (opponent, player) = round
    if (opponent == player) {
        return Draw
    }

    return when (Pair(player, opponent)) {
        Paper to Rock,
        Rock to Scissors,
        Scissors to Paper -> Win
        else -> Loss
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
