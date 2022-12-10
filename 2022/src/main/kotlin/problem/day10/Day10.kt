package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

private val IMPORTANT_CYCLES = listOf(20, 60, 100, 140, 180, 220)

fun solveDay10Part1(input: List<String>): Int {
    val instructions = input.map { it.split(" ") }
    val xValues = mutableListOf(1).apply { addSpritePositions(instructions) }
    return IMPORTANT_CYCLES.sumOf { it * xValues[it - 1] }
}

fun solveDay10Part2(input: List<String>): Int {
    val instructions = input.map { it.split(" ") }
    val xValues = mutableListOf(1).apply { addSpritePositions(instructions) }
    (0 until 6).forEach { row ->
        (0 until 40).forEach { col ->
            val index = row * 40 + col
            print(if (col in (xValues[index] - 1..xValues[index] + 1)) '#' else '.')
        }
        println()
    }
    return -1
}

private fun MutableList<Int>.addSpritePositions(
    instructions: List<List<String>>,
) {
    instructions.forEach {
        when (it[0]) {
            "addx" -> {
                add(last())
                add(last() + it[1].toInt())
            }
            else -> add(last())
        }
    }
}
