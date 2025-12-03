package problem.day02

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.pow

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

fun solveDay02Part1(input: List<String>): Long = input.findInvalidIds()

fun solveDay02Part2(input: List<String>): Long = input.findInvalidIds2()

private fun List<String>.findInvalidIds(): Long = first().split(',').sumOf { it.countInvalid() }

private fun String.countInvalid(): Long {
    split('-').let {
        val start = it[0].toLong()
        val end = it[1].toLong()
        return (start..end)
            .filter { num ->
                num.toString().let { str ->
                    str.length % 2 == 0 && (str.take(str.length / 2) == str.takeLast(str.length / 2))
                }
            }.sum()
    }
}

private fun List<String>.findInvalidIds2(): Long = first().split(',').sumOf { it.countInvalid2() }

private fun String.countInvalid2(): Long =
    split('-').let {
        val start = it[0].toLong()
        val end = it[1].toLong()
        sumUnique(it, end, start)
    }

private fun sumUnique(
    strings: List<String>,
    end: Long,
    start: Long,
): Long {
    val unique = mutableSetOf<Long>()
    (1..strings[1].length / 2).forEach { len ->
        val timesMin = max(2, strings[0].length / len)
        val timesMax = strings[1].length / len
        var seq =
            if (strings[0].length == strings[1].length) {
                strings[0].take(len).toLong()
            } else {
                1L * 10.0.pow(len - 1).toLong()
            }
        while (seq.toString().repeat(timesMin).toLong() <= end) {
            (timesMin..timesMax).forEach { times ->
                val num = seq.toString().repeat(times).toLong()
                if (num in start..end) {
                    unique.add(num)
                }
            }
            seq++
        }
    }
    return unique.sum()
}
