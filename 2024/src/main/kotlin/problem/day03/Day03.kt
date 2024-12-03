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
    return input.sumOf { line ->
        MUL_REGEX.findAll(line).sumOf { match ->
            val (x, y) = match.destructured
            x.toInt() * y.toInt()
        }
    }
}

fun solveDay03Part2(input: List<String>): Int {
    var enabled = true
    var totalSum = 0
    input.forEach { line ->
        INSTRUCTION_REGEX.findAll(line).forEach { match ->
            val instruction = match.value
            when {
                DO_REGEX.matches(instruction) -> enabled = true
                DONT_REGEX.matches(instruction) -> enabled = false
                MUL_REGEX.matches(instruction) && enabled -> {
                    val (x, y) = MUL_REGEX.matchEntire(instruction)!!.destructured
                    totalSum += x.toInt() * y.toInt()
                }
            }
        }
    }
    return totalSum
}

private val INSTRUCTION_REGEX = Regex("""mul\((\d+),(\d+)\)""")
private val MUL_REGEX = Regex("""mul\((\d+),(\d+)\)""")
private val DO_REGEX = Regex("""do\(\)""")
private val DONT_REGEX = Regex("""don't\(\)""")