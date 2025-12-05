package problem.day05

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

fun solveDay05Part1(input: List<String>): Int {
    val (ranges, indices) = input.parseInput()
    return indices.count { index -> ranges.any { index in it } }
}

fun solveDay05Part2(input: List<String>): Long {
    val ranges = input.parseRanges(input.indexOf(""))
    val mergedRanges = mutableListOf<LongRange>()
    ranges.sortedBy { it.first }.forEach { range ->
        if (mergedRanges.isEmpty() || mergedRanges.last().last < range.first - 1) {
            mergedRanges.add(range)
        } else {
            val lastRange = mergedRanges.removeLast()
            mergedRanges.add(lastRange.first..maxOf(lastRange.last, range.last))
        }
    }
    return mergedRanges.sumOf { range -> range.last - range.first + 1 }.toLong()
}

fun List<String>.parseInput(): Pair<List<LongRange>, List<Long>> {
    val splitIndex = indexOf("")
    val ranges = parseRanges(splitIndex)
    val indices = drop(splitIndex + 1).map { it.toLong() }
    return ranges to indices
}

private fun List<String>.parseRanges(splitIndex: Int): List<LongRange> =
    take(splitIndex).map { line ->
        line.split('-').let {
            it[0].toLong()..it[1].toLong()
        }
    }
