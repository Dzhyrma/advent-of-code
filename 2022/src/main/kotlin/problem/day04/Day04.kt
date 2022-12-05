package problem.day04

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.lang.Integer.max
import java.lang.Integer.min

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}


fun solveDay04Part1(input: List<String>): Int {
    return elvesAssignedRanges(input)
        .count { (firstRange, secondRange) -> firstRange.contains(secondRange) || secondRange.contains(firstRange) }
}

fun solveDay04Part2(input: List<String>): Int {
    return elvesAssignedRanges(input)
        .count { (firstRange, secondRange) -> doRangeOverlap(firstRange, secondRange) }
}

private fun elvesAssignedRanges(input: List<String>) = input
    .map { it.substringBefore(',') to it.substringAfter(',') }
    .map { (firstElf, secondElf) -> firstElf.toRange() to secondElf.toRange() }

private fun String.toRange() = substringBefore('-').toInt()..substringAfter('-').toInt()

private fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last
private fun doRangeOverlap(first: IntRange, second: IntRange) =
    min(first.last, second.last) - max(first.first, second.first) >= 0

