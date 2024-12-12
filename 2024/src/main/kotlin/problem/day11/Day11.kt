package problem.day11

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.math.BigInteger

fun main(args: Array<String>) {
    val day = 11
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay11Part1, ::solveDay11Part2)
}

fun solveDay11Part1(input: List<String>): Long {
    return input[0].split(" ").sumOf { evolveStones(it, 25, MEMO) }
}

fun solveDay11Part2(input: List<String>): Long {
    return input[0].split(" ").sumOf { evolveStones(it, 75, MEMO) }
}

private fun evolveStones(
    stone: String,
    blinksLeft: Int,
    memory: MutableMap<String, MutableMap<Int, Long>>,
): Long {
    if (blinksLeft == 0) return 1L
    val stoneMemo = memory.getValue(stone)
    stoneMemo[blinksLeft]?.also { return it }
    val result = when {
        stone == "0" -> evolveStones("1", blinksLeft - 1, memory)
        stone.length % 2 == 0 -> {
            val mid = stone.length / 2
            val left = stone.substring(0, mid)
            val right = stone.substring(mid)
            evolveStones(left, blinksLeft - 1, memory) +
                evolveStones(BigInteger(right).toString(), blinksLeft - 1, memory)
        }
        else -> {
            evolveStones((BigInteger(stone) * BI_2024).toString(), blinksLeft - 1, memory)
        }
    }
    stoneMemo[blinksLeft] = result
    memory[stone] = stoneMemo
    return result
}

private val BI_2024 = BigInteger.valueOf(2024)
private val MEMO = mutableMapOf<String, MutableMap<Int, Long>>().withDefault { mutableMapOf() }
