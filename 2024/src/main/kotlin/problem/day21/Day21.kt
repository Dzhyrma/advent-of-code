package problem.day21

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 21
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay21Part1, ::solveDay21Part2)
}

fun solveDay21Part1(input: List<String>): Long {
    return calculateComplexities(input)
}

fun solveDay21Part2(input: List<String>): Long {
    return calculateComplexities(input, 25)
}

private fun calculateComplexities(input: List<String>, depth: Int = 2): Long {
    return input.sumOf { code ->
        println(code)
        val length = shortestNumpadSequence(code, depth)
        val num = code.filter { it.isDigit() }.toLong()
        val result = length * num
        println("$length * $num = $result")
        result
    }
}

private fun shortestNumpadSequence(code: String, depth: Int): Long {
    var current = 'A'
    val cache = mutableMapOf<Int, MutableMap<String, Long>>().withDefault { mutableMapOf() }
    return code.map { next ->
        val dx = KEYPAD.getValue(next).second - KEYPAD.getValue(current).second
        val dy = KEYPAD.getValue(next).first - KEYPAD.getValue(current).first
        val hor = ">".repeat(max(0, dx)) + "<".repeat(max(0, -dx))
        val ver = "v".repeat(max(0, dy)) + "^".repeat(max(0, -dy))
        (
            if ((current == '0' || current == 'A') && (next == '7' || next == '4' || next == '1')) {
                shortestDirSequence(ver + hor + 'A', depth, cache)
            } else if ((current == '7' || current == '4' || current == '1') && (next == '0' || next == 'A')) {
                shortestDirSequence(hor + ver + 'A', depth, cache)
            } else {
                min(
                    shortestDirSequence(ver + hor + 'A', depth, cache),
                    shortestDirSequence(hor + ver + 'A', depth, cache),
                )
            }
            ).also { current = next }
    }.sum()
}

private fun shortestDirSequence(
    code: String,
    depth: Int = 2,
    cache: MutableMap<Int, MutableMap<String, Long>>,
): Long {
    if (depth <= 0) return code.length.toLong()
    val levelCache = cache.getValue(depth)
    if (levelCache.containsKey(code)) return levelCache.getValue(code)
    var current = 'A'
    return code.map { next ->
        val dx = DIRECTIONAL_KEYPAD.getValue(next).second - DIRECTIONAL_KEYPAD.getValue(current).second
        val dy = DIRECTIONAL_KEYPAD.getValue(next).first - DIRECTIONAL_KEYPAD.getValue(current).first
        val hor = ">".repeat(max(0, dx)) + "<".repeat(max(0, -dx))
        val ver = "v".repeat(max(0, dy)) + "^".repeat(max(0, -dy))
        (
            if ((current == 'A' || current == '^') && next == '<') {
                shortestDirSequence(ver + hor + 'A', depth - 1, cache)
            } else if ((next == 'A' || next == '^') && current == '<') {
                shortestDirSequence(hor + ver + 'A', depth - 1, cache)
            } else {
                min(
                    shortestDirSequence(ver + hor + 'A', depth - 1, cache),
                    shortestDirSequence(hor + ver + 'A', depth - 1, cache),
                )
            }
            ).also { current = next }
    }.sum().also { sum ->
        cache[depth] = levelCache
        levelCache[code] = sum
    }
}

private val KEYPAD = mapOf(
    '7' to Pair(0, 0), '8' to Pair(0, 1), '9' to Pair(0, 2),
    '4' to Pair(1, 0), '5' to Pair(1, 1), '6' to Pair(1, 2),
    '1' to Pair(2, 0), '2' to Pair(2, 1), '3' to Pair(2, 2),
    '0' to Pair(3, 1), 'A' to Pair(3, 2),
)
private val DIRECTIONAL_KEYPAD = mapOf(
    '^' to Pair(0, 1),
    'A' to Pair(0, 2),
    '<' to Pair(1, 0),
    'v' to Pair(1, 1),
    '>' to Pair(1, 2),
)
