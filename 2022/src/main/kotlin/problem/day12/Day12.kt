package problem.day12

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val day = 12
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay12Part1, ::solveDay12Part2)
}

private data class Point(val row: Int, val col: Int) {

    operator fun plus(other: Point) = Point(row + other.row, col + other.col)

    fun isInRange(map: List<String>) = row in map.indices && col in map[row].indices
}

private val DELTAS = listOf(
    Point(1, 0),
    Point(-1, 0),
    Point(0, 1),
    Point(0, -1),
)

fun solveDay12Part1(input: List<String>): Int {
    val start = input.findCharCoordinates('S')
    return input.bfs(start)
}

fun solveDay12Part2(input: List<String>): Int {
    return input.flatMapIndexed { rowIndex: Int, row: String ->
        row.mapIndexed { colIndex, char ->
            if (char == 'S' || char == 'a') input.bfs(Point(rowIndex, colIndex)) else Int.MAX_VALUE
        }
    }.min()
}

private fun List<String>.findCharCoordinates(charToSearch: Char): Point {
    forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, char ->
            if (char == charToSearch) return Point(rowIndex, columnIndex)
        }
    }
    throw IllegalArgumentException("Wrong input")
}

private fun List<String>.bfs(start: Point): Int {
    val queue = LinkedList<Pair<Point, Int>>().apply { add(start to 0) }
    val visited = mutableSetOf<Point>()
    while (queue.isNotEmpty()) {
        val (current, distance) = queue.poll()
        if (!visited.contains(current)) {
            val currentChar = this[current.row][current.col].let { if (it == 'S') 'a' else it }
            DELTAS.forEach { delta ->
                val next = current + delta
                when {
                    !next.isInRange(this) -> {}
                    this[next.row][next.col] == 'E' && ('z' - currentChar).absoluteValue <= 1 -> return distance + 1
                    currentChar + 1 - this[next.row][next.col] >= 0 -> queue.offer(next to distance + 1)
                }
            }
            visited.add(current)
        }
    }
    return Int.MAX_VALUE
}
