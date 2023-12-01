package problem.day01

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
}

fun solveDay01Part1(input: List<String>): Int {
    return input.sumOfFirstAndSecondDigits()
}

fun solveDay01Part2(input: List<String>): Int {
    return input.sumOfFirstAndSecondDigitsImproved()
}

private fun List<String>.sumOfFirstAndSecondDigits() =
    map { line -> line.first { it.isDigit() } to line.last { it.isDigit() } }
        .sumOf { (it.first - '0') * 10 + (it.second - '0') }

private fun List<String>.sumOfFirstAndSecondDigitsImproved() =
    sumOf { line ->
        val firstDigit = DIGIT_MAP.entries.mapIndexed { index, (word, digit) ->
            index to min(line.indexOf(word).orBiggestNumber(), line.indexOf(digit).orBiggestNumber())
        }.minBy { it.second }.first + 1
        val lastDigit = DIGIT_MAP.entries.mapIndexed { index, (word, digit) ->
            index to max(line.lastIndexOf(word).orSmallestNumber() + word.length - 1, line.lastIndexOf(digit).orSmallestNumber())
        }.maxBy { it.second }.first + 1
        firstDigit * 10 + lastDigit
    }

private fun Int.orBiggestNumber() = if (this == -1) Int.MAX_VALUE else this

private fun Int.orSmallestNumber() = if (this == -1) Int.MIN_VALUE else this

private val DIGIT_MAP = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)