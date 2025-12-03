package problem.day03

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 3
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay03Part1, ::solveDay03Part2)
}

fun solveDay03Part1(input: List<String>): Long = input.biggestSubNumber(numberOfDigits = 2)

fun solveDay03Part2(input: List<String>): Long = input.biggestSubNumber(numberOfDigits = 12)

private fun List<String>.biggestSubNumber(numberOfDigits: Int): Long =
    fold(0) { acc, line ->
        var result = 0L
        var prevDigitIndex = -1
        repeat(numberOfDigits) { i ->
            prevDigitIndex += line.drop(prevDigitIndex + 1).dropLast(numberOfDigits - i - 1).findBiggestCharacterIndex() + 1
            result = result * 10 + (line[prevDigitIndex] - '0').toLong()
        }
        acc + result
    }

private fun String.findBiggestCharacterIndex() =
    foldIndexed(0) { index, prevIndex, c ->
        if (this[prevIndex] < c) {
            index
        } else {
            prevIndex
        }
    }
