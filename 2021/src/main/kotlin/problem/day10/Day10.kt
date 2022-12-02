package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

private val BRACKET_MAP = mapOf('[' to ']', '(' to ')', '{' to '}', '<' to '>')
private val CORRUPTION_PRICE_MAP = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val COMPLETION_PRICE_MAP = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)


fun solveDay10Part1(input: List<String>): Int {
    return input.sumOf { line ->
        val bracketStack = Stack<Char>()
        val corrupted = line.find {
            if (BRACKET_MAP.containsKey(it)) {
                bracketStack.add(it)
                false
            } else if (BRACKET_MAP[bracketStack.peek()] == it) {
                bracketStack.pop()
                false
            } else true
        }
        corrupted?.let { CORRUPTION_PRICE_MAP[it] } ?: 0
    }
}

fun solveDay10Part2(input: List<String>): Long {
    return input.mapNotNull { line ->
        val bracketStack = Stack<Char>()
        val corrupted = line.find {
            if (BRACKET_MAP.containsKey(it)) {
                bracketStack.add(BRACKET_MAP[it])
                false
            } else if (bracketStack.peek() == it) {
                bracketStack.pop()
                false
            } else true
        }
        if (corrupted != null) null else {
            bracketStack.foldRight(0L) { char, acc -> acc * 5 + COMPLETION_PRICE_MAP[char]!! }
        }
    }.sorted().let { it[it.size / 2] }
}
