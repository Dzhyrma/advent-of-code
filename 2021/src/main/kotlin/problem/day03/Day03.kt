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
    val (gammaRate, epsilonRate) = input
        .toBitCounterArray()
        .fold(0 to 0) { (gammaRate, epsilonRate), bitCount ->
            val (mostCommonBit, leastCommonBit) = if (bitCount * 2 > input.size) (1 to 0) else (0 to 1)
            ((gammaRate shl 1) + mostCommonBit) to ((epsilonRate shl 1) + leastCommonBit)
        }
    return gammaRate * epsilonRate
}

private fun List<String>.toBitCounterArray(): IntArray =
    fold(IntArray(this[0].length)) { counts, bits ->
        bits.forEachIndexed { index, bit ->
            counts[index] += if (bit == '1') 1 else 0
        }
        counts
    }

fun solveDay03Part2(input: List<String>): Int {
    val oxygenGeneratorRating =
        filterInputByBitCriteria(input) { values, index -> values.count { it[index] == '1' } * 2 >= values.size }
    val co2ScrubberRating =
        filterInputByBitCriteria(input) { values, index -> values.count { it[index] == '1' } * 2 < values.size }
    return oxygenGeneratorRating * co2ScrubberRating
}

private fun filterInputByBitCriteria(input: List<String>, bitCriteria: (List<String>, Int) -> Boolean): Int {
    var values = input
    var index = 0
    do {
        val mostCommonBit = if (bitCriteria(values, index)) '1' else '0'
        values = values.filter { it[index] == mostCommonBit }
        index++
    } while (values.size > 1 && index < input[0].length)
    return values.first().toInt(2)
}
