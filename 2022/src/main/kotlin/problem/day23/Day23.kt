package problem.day23

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 23
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay23Part1, ::solveDay23Part2)
}

private data class Point(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun solveDay23Part1(input: List<String>): Int {
    var elfStates = parseElfPositions(input)
    (0 until 10).forEach { round ->
        elfStates = elfStates.let { elves ->
            val newPossibleStates = calculateNewPossibleStates(elves, round)
            elves.map { elf -> nextElfPosition(elf, elves, round).takeIf { newPossibleStates[it] == 1 } ?: elf }.toSet()
        }
    }
    val area =
        (elfStates.maxOf { it.x } - elfStates.minOf { it.x } + 1) * (elfStates.maxOf { it.y } - elfStates.minOf { it.y } + 1)
    return area - elfStates.size
}

fun solveDay23Part2(input: List<String>): Int {
    var elfStates = parseElfPositions(input)
    var round = 0
    do {
        var moveWasMade = false
        elfStates = elfStates.let { elves ->
            val newPossibleStates = calculateNewPossibleStates(elves, round)
            elves.map { elf ->
                (nextElfPosition(elf, elves, round).takeIf { newPossibleStates[it] == 1 } ?: elf).also {
                    moveWasMade = moveWasMade || it != elf
                }
            }.toSet()
        }
        round++
    } while (moveWasMade)
    return round
}

private fun parseElfPositions(input: List<String>) = input.flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, char -> if (char == '#') Point(x, y) else null }
}.toSet()

private fun calculateNewPossibleStates(
    elves: Set<Point>,
    round: Int,
) = elves
    .map { elf -> nextElfPosition(elf, elves, round) }
    .fold(mutableMapOf<Point, Int>()) { acc, elf ->
        acc.also { it[elf] = it.getOrDefault(elf, 0) + 1 }
    }

private fun nextElfPosition(elf: Point, elves: Set<Point>, round: Int) = when {
    ALL_DIRECTIONS.none { elves.contains(elf + it) } -> elf
    else -> (round..round + 3).map { CHECKS[it % 4] }.firstNotNullOfOrNull { (checks, delta) ->
        (elf + delta).takeIf { checks.all { !elves.contains(elf + it) } }
    } ?: elf
}

private val N_CHECKS = listOf(Point(-1, -1), Point(0, -1), Point(1, -1))
private val S_CHECKS = listOf(Point(-1, 1), Point(0, 1), Point(1, 1))
private val W_CHECKS = listOf(Point(-1, -1), Point(-1, 0), Point(-1, 1))
private val E_CHECKS = listOf(Point(1, -1), Point(1, 0), Point(1, 1))
private val CHECKS = listOf(
    N_CHECKS to Point(0, -1),
    S_CHECKS to Point(0, 1),
    W_CHECKS to Point(-1, 0),
    E_CHECKS to Point(1, 0),
)
private val ALL_DIRECTIONS = (N_CHECKS + S_CHECKS + W_CHECKS + E_CHECKS).toSet()
