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
    val depthMeasurements = input.map { it.toInt() }
    return countDeeperMeasurements(depthMeasurements)
}

fun solveDay01Part2(input: List<String>): Int {
    val depthMeasurements = input.map { it.toInt() }
    val complexDepthMeasurments = depthMeasurements
        .drop(2)
        .mapIndexed { index, depth -> depth + depthMeasurements[index] + depthMeasurements[index + 1] }
    return countDeeperMeasurements(complexDepthMeasurments)
}

private fun countDeeperMeasurements(depthMeasurements: List<Int>) = depthMeasurements
    .drop(1)
    .mapIndexed { index, depth -> depth > depthMeasurements[index] }
    .count { it }
