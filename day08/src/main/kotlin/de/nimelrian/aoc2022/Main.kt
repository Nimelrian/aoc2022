package de.nimelrian.aoc2022

fun main() = aoc {
    part("/input") { lines ->
        val (squareSize, trees) = readTreeGrid(lines)

        trees.countIndexed { index, treeHeight ->
            val treeX = index % squareSize
            val treeY = index / squareSize
            // edges of the grid are always visible
            if (treeX == 0 || treeY == 0 || treeX == squareSize - 1 || treeY == squareSize - 1) {
                return@countIndexed true
            }

            (0 until treeY).all { trees.get(treeX, it) < treeHeight } // up
                    || ((treeY + 1) until squareSize).all { trees.get(treeX, it) < treeHeight } // down
                    || (0 until treeX).all { trees.get(it, treeY) < treeHeight } // left
                    || ((treeX + 1) until squareSize).all { trees.get(it, treeY) < treeHeight } //right
        }
    }

    part("/input") { lines ->
        val (squareSize, trees) = readTreeGrid(lines)

        trees.mapIndexed { index, treeHeight ->
            val treeX = index % squareSize
            val treeY = index / squareSize
            // edges of the grid always have one viewing distance of 0, so the whole product turns out to be zero
            if (treeX == 0 || treeY == 0 || treeX == squareSize - 1 || treeY == squareSize - 1) {
                return@mapIndexed 0
            }

            val distanceUp = ((treeY - 1) downTo 0).asSequence()
                .takeWhileInclusive { trees.get(treeX, it) < treeHeight }.count()

            val distanceDown = ((treeY + 1) until squareSize).asSequence()
                .takeWhileInclusive { trees.get(treeX, it) < treeHeight }.count()

            val distanceLeft = ((treeX - 1) downTo 0).asSequence()
                .takeWhileInclusive { trees.get(it, treeY) < treeHeight }.count()

            val distanceRight = ((treeX + 1) until squareSize).asSequence()
                .takeWhileInclusive { trees.get(it, treeY) < treeHeight }.count()

            val scenicScore = distanceUp * distanceDown * distanceLeft * distanceRight
            scenicScore
        }.max()
    }
}

private fun readTreeGrid(lines: Sequence<String>): Pair<Int, GridView<Int>> {
    var squareSize = 0
    val trees = lines.flatMap {
        squareSize = it.length
        it.asSequence()
    }
        .map { it.digitToInt() }
        .toList()
        .asGrid(squareSize)
    return Pair(squareSize, trees)
}

class GridView<T>(private val list: List<T>, private val gridSize: Int) : List<T> by list {
    init {
        require(gridSize * gridSize == list.size)
    }

    fun get(x: Int, y: Int): T = this[y * gridSize + x]
}

fun <T> List<T>.asGrid(gridSize: Int) = GridView(this, gridSize)

inline fun <T> Iterable<T>.countIndexed(predicate: (index: Int, T) -> Boolean): Int {
    if (this is Collection && isEmpty()) return 0

    var count = 0
    var index = 0
    for (element in this) if (predicate(index++, element)) ++count
    return count
}
