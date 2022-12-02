package problem.day01

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
}


fun solveDay01Part1(input: List<String>): Int {
    return calculateCaloriesPerElf(input)
        .max()
}

fun solveDay01Part2(input: List<String>): Int {
    return calculateCaloriesPerElf(input)
        .sorted()
        .takeLast(3)
        .sum()
}

private fun calculateCaloriesPerElf(input: List<String>) = (input + "")
    .map { it.toIntOrNull() }
    .scan(0) { acc, next -> next?.let { it + acc } ?: 0 }
    .keepOnlySumsOfCalories()

private fun List<Int>.keepOnlySumsOfCalories() = filterIndexed { index, _ -> index < size - 1 && this[index + 1] == 0 }
