package problem.day25

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.math.BigInteger

fun main(args: Array<String>) {
    val day = 25
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay25Part1, ::solveDay25Part2)
}


fun solveDay25Part1(input: List<String>): String {
    return input.sumOf { it.toLongFromSnafu() }.toSnafu()
}

fun solveDay25Part2(input: List<String>): String {
    TODO("There is no second part")
}

private fun String.toLongFromSnafu(): Long {
    return reversed().foldIndexed(0L) { index, acc, char -> acc + POW_OF_5S[index] * SNAFU_MAP.getValue(char) }
}

private fun Long.toSnafu(): String {
    var current = this
    return POW_OF_5S.indices.reversed()
        .map { index ->
            val pow = POW_OF_5S[index]
            val halfPow = pow / 2
            when {
                current > pow * 2 + halfPow
                        || current < -pow * 2 - halfPow -> throw IllegalArgumentException("Number is too big")
                current in -halfPow..halfPow -> '0'
                current > pow + halfPow -> '2'
                current > halfPow -> '1'
                current < -pow - halfPow -> '='
                else -> '-'
            }.also { current -= POW_OF_5S[index] * SNAFU_MAP.getValue(it) }
        }
        .dropWhile { it == '0' }
        .let { String(it.toCharArray()) }
}

private val SNAFU_MAP = mapOf<Char, Long>('2' to 2, '1' to 1, '0' to 0, '-' to -1, '=' to -2)
private val POW_OF_5S = (0 until 27).map { BigInteger.valueOf(5).pow(it).toLong() }
