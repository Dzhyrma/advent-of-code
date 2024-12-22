package problem.day22

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 22
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay22Part1, ::solveDay22Part2)
}

fun solveDay22Part1(input: List<String>): Long {
    return input.map { it.toLong() }.sumOf { generateSecret(it).first }
}

fun solveDay22Part2(input: List<String>): Int {
    val bestSequence = input.findBestSequence()
    return bestSequence
}

fun List<String>.findBestSequence(): Int {
    val sequences = HashMap<Int, Int>(50000, 1f).withDefault { 0 }
    val subSequences = HashMap<Int, Int>(2001, 1f)
    map { it.toLong() }.forEach { secret ->
        val prices = generateSecret(secret).second
        var seqCode = prices.ch(1) shl 5 or prices.ch(2) shl 5 or prices.ch(3) shl 5 or prices.ch(4)
        subSequences[seqCode] = prices[4]
        for (i in 5 until prices.size) {
            seqCode = seqCode shl 5 or prices.ch(i) and 0xFFFFF
            if (!subSequences.containsKey(seqCode)) {
                subSequences[seqCode] = prices[i]
            }
        }
        subSequences.forEach { (seqCode, price) -> sequences[seqCode] = sequences.getValue(seqCode) + price }
        subSequences.clear()
    }
    return sequences.maxOf { it.value }
}

private fun generateSecret(secret: Long): Pair<Long, List<Int>> {
    var s = secret
    val prices = mutableListOf<Int>()
    prices.add((s % 10L).toInt())
    repeat(2000) {
        s = s shl 6 xor s and 0xFFFFFF
        s = s shr 5 xor s
        s = s shl 11 xor s and 0xFFFFFF
        prices.add((s % 10L).toInt())
    }
    return s to prices
}

private fun List<Int>.ch(i: Int) = this[i] - this[i - 1] + 9
