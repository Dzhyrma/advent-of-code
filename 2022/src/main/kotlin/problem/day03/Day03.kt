package problem.day03

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 3
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay03Part1, ::solveDay03Part2)
}


fun solveDay03Part1(input: List<String>): Int {
    return input
        .map { it.take(it.length / 2) to it.takeLast(it.length / 2) }
        .map { it.first.toSet() to it.second.toSet() }
        .map { it.first.intersect(it.second).first() }
        .sumOf { it.toItemPriority() }
}

fun solveDay03Part2(input: List<String>): Int {
    return input
        .map { it.toSet() }
        .chunked(3) { it.reduce { commonItems, nextRucksack -> commonItems intersect nextRucksack }.first() }
        .sumOf { it.toItemPriority() }
}

private fun Char.toItemPriority() = when (this) {
    in 'a'..'z' -> this - 'a' + 1
    else -> this - 'A' + 27
}
