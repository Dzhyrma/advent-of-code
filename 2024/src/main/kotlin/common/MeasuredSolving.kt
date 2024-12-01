package common

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias Input = List<String>

fun <T> solve(
    day: Int,
    input: Input,
    solvePart1: (List<String>) -> T,
    solvePart2: (List<String>) -> T,
) {
    solvePart(day = day, part = 1, input = input, solve = solvePart1)
    solvePart(day = day, part = 2, input = input, solve = solvePart2)
}

@OptIn(ExperimentalTime::class)
private fun <T> solvePart(day: Int, part: Int, input: Input, solve: (List<String>) -> T) {
    try {
        println("\nStart solving part $part.")
        val solution = measureTimedValue { solve(input) }
        println("\nSolving part $part took ${solution.duration}.")
        println("The solution for day $day part $part is: ${solution.value}")
    } catch (ignored: NotImplementedError) {
        println("Skipped part $part.")
    }
}
