package problem.day11

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 11
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay11Part1, ::solveDay11Part2)
}

private const val SIZE = 10
private const val SIZE_SQAURED = SIZE * SIZE
private val DELTA_COORDINATES = arrayOf(
    -1 to -1,
    -1 to 0,
    -1 to 1,
    0 to -1,
    0 to 1,
    1 to -1,
    1 to 0,
    1 to 1,
)

fun solveDay11Part1(input: List<String>): Int {
    val state = input.map { it.toMutableList() }

    return (1..100).sumOf { state.tickAndCountFlashes() }
}

fun solveDay11Part2(input: List<String>): Int {
    val state = input.map { it.toMutableList() }

    return (1..1000).first { SIZE_SQAURED == state.tickAndCountFlashes() }
}

private fun List<MutableList<Char>>.tickAndCountFlashes(): Int {
    val flashSet = increaseLevelsByOne()
    return increaseNeighborLevelsByOneAndCount(flashSet)
}

private fun List<MutableList<Char>>.increaseLevelsByOne(): Set<Pair<Int, Int>> {
    return mapIndexed { y, line ->
        line.mapIndexedNotNull { x, level ->
            if (level == '9') {
                line[x] = '0'
                x to y
            } else {
                line[x] = level + 1
                null
            }
        }
    }.fold(mutableSetOf()) { acc, flashCoordinates ->
        acc.also { it.addAll(flashCoordinates) }
    }
}

private fun List<MutableList<Char>>.increaseNeighborLevelsByOneAndCount(flashSet: Set<Pair<Int, Int>>): Int {
    if (flashSet.isEmpty()) return 0

    val newFlashSet = flashSet.map { (x, y) ->
        DELTA_COORDINATES.mapNotNull { (dx, dy) ->
            val newX = x + dx
            val newY = y + dy
            if (newX !in 0 until SIZE
                || newY !in 0 until SIZE
                || this[newY][newX] == '0'
            )
                null
            else if (this[newY][newX] < '9') {
                this[newY][newX] = this[newY][newX] + 1
                null
            } else {
                this[newY][newX] = '0'
                newX to newY
            }
        }
    }.fold(mutableSetOf<Pair<Int, Int>>()) { acc, flashCoordinates ->
        acc.also { it.addAll(flashCoordinates) }
    }
    return flashSet.size + increaseNeighborLevelsByOneAndCount(newFlashSet)
}