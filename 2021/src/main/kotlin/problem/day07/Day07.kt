package problem.day07

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2)
}


fun solveDay07Part1(input: List<String>): Int {
    return findMinimalFuelConsumption(input) { x1, x2 -> abs(x1 - x2) }
}

fun solveDay07Part2(input: List<String>): Int {
    return findMinimalFuelConsumption(input) { x1, x2 -> abs(x1 - x2).let { avg -> (avg + 1) * avg / 2 } }
}

private fun findMinimalFuelConsumption(input: List<String>, fuelCalculator: (Int, Int) -> Int): Int {
    return input.first().split(',').map { it.toInt() }.let { coordinates ->
        ((coordinates.minOrNull() ?: 0)..(coordinates.maxOrNull() ?: 0)).minOfOrNull { caveCoordinate ->
            coordinates.sumOf { fuelCalculator(it, caveCoordinate) }
        } ?: 0
    }
}