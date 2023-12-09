package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay09Part1, ::solveDay09Part2)
}

fun solveDay09Part1(input: List<String>): Long {
    return input.sumOf { line ->
        line.split(' ').map { it.toLong() }.predictNext()
    }
}

fun solveDay09Part2(input: List<String>): Long {
    return input.sumOf { line ->
        line.split(' ').map { it.toLong() }.predictFirst()
    }
}

private fun List<Long>.predictNext(): Long {
    if (all { it == 0L }) return 0
    return last() + zipWithNext { a, b -> b - a }.predictNext()
}

private fun List<Long>.predictFirst(): Long {
    if (all { it == 0L }) return 0
    return first() - zipWithNext { a, b -> b - a }.predictFirst()
}