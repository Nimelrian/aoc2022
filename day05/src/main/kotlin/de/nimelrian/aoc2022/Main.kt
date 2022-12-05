package de.nimelrian.aoc2022

import java.util.ArrayList
import java.util.Deque
import java.util.LinkedList

fun main() = aoc {
    part("/input") { lines ->
        val (stacks, commands) = parseInput(lines)
        commands.forEach { CraneMover9000.executeCommand(stacks, it)  }
        stacks.map { it.peek() }.joinToString("")
    }

    part("/input") { lines ->
        val (stacks, commands) = parseInput(lines)
        commands.forEach { CraneMover9001.executeCommand(stacks, it)  }
        stacks.map { it.peek() }.joinToString("")
    }
}

fun interface Crane {
    fun executeCommand(stacks: List<CrateStack>, command: MoveCommand)
}

object CraneMover9000 : Crane {
    override fun executeCommand(stacks: List<CrateStack>, command: MoveCommand) {
        val (count, sourceIndex, destinationIndex) = command
        val source = stacks[sourceIndex - 1]
        val destination = stacks[destinationIndex - 1]

        repeat(count) {
            destination.push(source.pop())
        }
    }
}

object CraneMover9001 : Crane {
    override fun executeCommand(stacks: List<CrateStack>, command: MoveCommand) {
        val (count, sourceIndex, destinationIndex) = command
        val source = stacks[sourceIndex - 1]
        val destination = stacks[destinationIndex - 1]

        val pickedUpCrates = ArrayList<Crate>(count)
        repeat(count) { pickedUpCrates.add(source.pop()) }
        pickedUpCrates.asReversed().forEach { destination.push(it) }
    }
}

typealias Crate = Char
typealias CrateStack = Deque<Crate>

data class ParsedInput(val stacks: List<CrateStack>, val commands: List<MoveCommand>)

fun parseInput(lines: Sequence<String>): ParsedInput {
    val stacks = mutableListOf<CrateStack>()
    val stackIndexLinePattern = "^(\\s\\d\\s+)*\$".toRegex()

    val stackSetupParser = { line: String ->
        if (!stackIndexLinePattern.matches(line)) {
            line.chunked(4).forEachIndexed { index, crate ->
                val stack = stacks.getOrNull(index) ?: LinkedList<Crate>().also { stacks.add(it) }
                tryParseCrate(crate)?.let { stack.addLast(it) }
            }
        }
    }

    val commands = mutableListOf<MoveCommand>()

    var parsingSetup = true
    for (line in lines) {
        if (line.isBlank()) {
            parsingSetup = false
            continue
        }

        if (parsingSetup) {
            stackSetupParser(line)
        } else {
            commands.add(MoveCommand.from(line))
        }
    }

    return ParsedInput(stacks, commands)
}

fun tryParseCrate(input: String): Crate? = input.takeIf { it.isNotBlank() }
    ?.get(1)

data class MoveCommand(val count: Int, val sourceIndex: Int, val destinationIndex: Int) {
    companion object : From<String, MoveCommand> {
        private val PATTERN = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

        override fun from(source: String): MoveCommand {
            val (count, sourceIndex, destinationIndex) = PATTERN.find(source)!!.destructured
            return MoveCommand(count.toInt(), sourceIndex.toInt(), destinationIndex.toInt())
        }

    }
}
