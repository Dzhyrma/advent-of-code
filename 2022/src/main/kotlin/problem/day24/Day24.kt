package problem.day24

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*
import kotlin.math.max

fun main(args: Array<String>) {
    val day = 24
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay24Part1, ::solveDay24Part2)
}

private data class Point(
    val row: Int,
    val col: Int,
) {
    operator fun plus(other: Point) = Point(row + other.row, col + other.col)
}

fun solveDay24Part1(input: List<String>): Int {
    val blizzardMap = input.drop(1).dropLast(1).map { it.drop(1).dropLast(1).toList() }
    val start = Point(-1, 0)
    val goal = Point(blizzardMap.size - 1, blizzardMap[0].size - 1)
    return blizzardMap.findFastestPath(start, goal) + 1
}

fun solveDay24Part2(input: List<String>): Int {
    val blizzardMap = input.drop(1).dropLast(1).map { it.drop(1).dropLast(1).toList() }
    val firstPass = blizzardMap.findFastestPath(
        start = Point(-1, 0),
        goal = Point(blizzardMap.size - 1, blizzardMap[0].size - 1),
    ) + 1
    val secondPass = blizzardMap.findFastestPath(
        start = Point(blizzardMap.size, blizzardMap[0].size - 1),
        goal = Point(0, 0),
        startTime = firstPass,
    ) + 1
    return blizzardMap.findFastestPath(
        start = Point(-1, 0),
        goal = Point(blizzardMap.size - 1, blizzardMap[0].size - 1),
        startTime = secondPass,
    ) + 1
}

private fun List<List<Char>>.findFastestPath(start: Point, goal: Point, startTime: Int = 0): Int {
    val width = this[0].size
    val blizzardCycle = lcm(size, width)
    val visited = (0 until blizzardCycle).map { mutableSetOf<Point>() }
    visited[startTime % blizzardCycle].add(start)
    val nextMovesQueue = LinkedList<Pair<Point, Int>>().also { it.offer(start to startTime) }
    do {
        val (current, timePassed) = nextMovesQueue.poll()
        if (current == goal) return timePassed
        val nextTimePassed = timePassed + 1
        DELTAS
            .map { current + it }
            .filter {
                !visited[nextTimePassed % blizzardCycle].contains(it)
                        && ((it.row in 0 until this.size
                        && it.col in 0 until width
                        && !isThereBlizzard(it, nextTimePassed))
                        || it == Point(-1, 0)
                        || it == Point(size, width - 1))
            }
            .forEach {
                nextMovesQueue.offer(it to nextTimePassed)
                visited[nextTimePassed % blizzardCycle].add(it)
            }
    } while (nextMovesQueue.isNotEmpty())
    return -1
}

private fun List<List<Char>>.isThereBlizzard(pos: Point, timePassed: Int): Boolean =
    this[pos.row][(pos.col + timePassed).mod(this[0].size)] == '<'
            || this[pos.row][(pos.col - timePassed).mod(this[0].size)] == '>'
            || this[(pos.row + timePassed).mod(size)][pos.col] == '^'
            || this[(pos.row - timePassed).mod(size)][pos.col] == 'v'

private fun lcm(a: Int, b: Int): Int {
    val gcd = (max(a, b) downTo 1).asSequence().filter { a % it == 0 && b % it == 0 }.first()
    return a * b / gcd
}

private val DELTAS = listOf(
    Point(1, 0),
    Point(0, 1),
    Point(0, 0),
    Point(-1, 0),
    Point(0, -1),
)
