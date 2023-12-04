package problem.day04

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}

fun solveDay04Part1(input: List<String>): Int {
    return input
        .parseCards()
        .map { it.first.toSet().intersect(it.second).size }
        .sumOf { 1.shl(it - 1) }
}

fun solveDay04Part2(input: List<String>): Int {
    return input
        .parseCards()
        .map { it.first.toSet().intersect(it.second).size }
        .let {
            val copies = (1..it.size).map { -1 }.toMutableList()
            calculateCopies(it, copies)
            copies.sum() + it.size
        }
}

private fun calculateCopies(
    cardValues: List<Int>,
    copies: MutableList<Int>,
    index: Int = cardValues.lastIndex,
) {
    if (index == -1) return
    copies[index] = (index + 1..min(index + cardValues[index], cardValues.lastIndex))
        .sumOf { 1 + copies[it] }
    calculateCopies(cardValues, copies, index - 1)
}

private fun List<String>.parseCards() = map { line ->
    val wNumbers = line.substringAfter(": ").substringBefore(" | ").parseNumbers()
    val ourNumbers = line.substringAfter(": ").substringAfter(" | ").parseNumbers()
    wNumbers to ourNumbers
}

private fun String.parseNumbers() = split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
