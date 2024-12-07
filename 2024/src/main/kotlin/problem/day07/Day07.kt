package problem.day07

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2)
}

fun solveDay07Part1(input: List<String>): Long {
    fun evaluateRecursive(numbers: List<Long>, target: Long, index: Int, current: Long): Boolean {
        if (index == numbers.size) return current == target

        val next = numbers[index]
        return evaluateRecursive(numbers, target, index + 1, current + next) ||
            evaluateRecursive(numbers, target, index + 1, current * next)
    }

    return input.sumOf { line ->
        val (target, numbers) = parseInput(line)
        if (evaluateRecursive(numbers, target, 1, numbers[0])) target else 0L
    }
}

fun solveDay07Part2(input: List<String>): Long {
    fun evaluateRecursive(numbers: List<Long>, target: Long, index: Int, current: Long): Boolean {
        if (index == numbers.size) return current == target

        val next = numbers[index]
        return evaluateRecursive(numbers, target, index + 1, current + next) ||
            evaluateRecursive(numbers, target, index + 1, current * next) ||
            evaluateRecursive(numbers, target, index + 1, "$current$next".toLong())
    }

    return input.sumOf { line ->
        val (target, numbers) = parseInput(line)
        if (evaluateRecursive(numbers, target, 1, numbers[0])) target else 0L
    }
}

private fun parseInput(line: String): Pair<Long, List<Long>> {
    val parts = line.split(":")
    val target = parts[0].toLong()
    val numbers = parts[1].trim().split(" ").map { it.toLong() }
    return target to numbers
}
