package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        lines.chunkByPredicate(String::isEmpty)
            .map { elfSnacks -> elfSnacks
                .asSequence()
                .filterNot { it.isEmpty() }
                .sumOf { it.toInt() }
            }
            .max()
    }

    part("/input") { lines ->
        lines.chunkByPredicate(String::isEmpty)
            .map { elfSnacks -> elfSnacks
                .asSequence()
                .filterNot { it.isEmpty() }
                .sumOf { it.toInt() }
            }
            .sortedDescending()
            .take(3)
            .sum()
    }
}

/**
 * Creates a sequence of chunks determined by the given predicate. Chunks contain all elements between (and including)
 * the last matched item and the (excluding) next matched item.
 * Example: The sequence 1, 2, 3, 4, 5, 6, 7, 8 will be split into 3 chunks if the predicate `it % 3 == 0` is used:
 *
 * * Chunk 1 - 1, 2
 * * Chunk 2 - 3, 4, 5
 * * Chunk 3 - 6, 7, 8
 *
 */
fun <T> Sequence<T>.chunkByPredicate(predicate: (T) -> Boolean): Sequence<List<T>> {
    return sequence {
        val currentChunk = mutableListOf<T>()
        for (item in this@chunkByPredicate) {
            if (predicate(item)) {
                // Yield the chunked elements up to this item
                yield(currentChunk.toList())
                currentChunk.clear()
            }

            currentChunk += item
        }

        // Last chunk
        yield(currentChunk)
    }
}
