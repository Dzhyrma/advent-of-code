package problem.day05

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

private val MOVE_REGEX = Regex("move (.*) from (.*) to (.*)")

fun solveDay05Part1(input: List<String>): String {
    val stacks = getStacks(input)
    getMoves(input)
        .forEach { move -> repeat(move[0]) { stacks[move[2]].push(stacks[move[1]].pop()) } }
    return stacks.map { it.peek() }.joinToString("")
}

fun solveDay05Part2(input: List<String>): String {
    val stacks = getStacks(input)
    val tempStack = Stack<Char>()
    getMoves(input)
        .forEach { move ->
            repeat(move[0]) { tempStack.push(stacks[move[1]].pop()) }
            repeat(move[0]) { stacks[move[2]].push(tempStack.pop()) }
        }
    return stacks.map { it.peek() }.joinToString("")
}

private fun getStacks(input: List<String>): List<Stack<Char>> {
    val numberOfStacks = getNumberOfStacks(input)
    return (1..numberOfStacks).map { Stack<Char>() }.also { stacks ->
        input
            .takeWhile { !it.startsWith(" 1") }
            .asReversed()
            .forEach { row ->
                row.chunked(4) { it[1] }.forEachIndexed { index, crate -> if (crate != ' ') stacks[index].push(crate) }
            }
    }
}

private fun getMoves(input: List<String>) = input
    .filter { it.startsWith("move") }
    .mapNotNull { MOVE_REGEX.matchEntire(it) }
    .map { match -> match.groupValues.drop(1).mapIndexed { index, str -> str.toInt() - if (index > 0) 1 else 0 } }
    .filter { it.size == 3 }

private fun getNumberOfStacks(input: List<String>) =
    input.first { it.startsWith(" 1") }.split(' ').last { it.isNotEmpty() }.toInt()
