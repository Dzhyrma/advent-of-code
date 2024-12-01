package problem.day01

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
}

fun solveDay01Part1(input: List<String>): Int {
    return input.calculateTotalDistance()
}

fun solveDay01Part2(input: List<String>): Int {
    return input.calculateSimiliarityScore()
}

private fun List<String>.calculateTotalDistance() =
    map { line -> line.split(" ").mapNotNull { it.toIntOrNull() } }
        .map { it[0] to it[1] }
        .let { list ->
            list.map { it.first }.sorted().zip(list.map { it.second }.sorted())
        }
        .sumOf { abs(it.first - it.second) }

private fun List<String>.calculateSimiliarityScore() =
    map { line -> line.split(" ").mapNotNull { it.toIntOrNull() } }
        .map { it[0] to it[1] }
        .let { list ->
            val rightCountMap = list.map { it.second }.groupingBy { it }.eachCount()
            list.map { it.first }.sumOf { number -> number * rightCountMap.getOrDefault(number, 0) }
        }
