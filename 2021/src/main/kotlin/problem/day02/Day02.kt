package problem.day02

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}


fun solveDay02Part1(input: List<String>): Int {
    var horizontalPosition = 0
    var depth = 0
    navigateSubmarine(
        input,
        forwardAction = { horizontalPosition += it },
        downAction = { depth += it },
        upAction = { depth -= it },
    )
    return horizontalPosition * depth
}

fun solveDay02Part2(input: List<String>): Int {
    var aim = 0
    var horizontalPosition = 0
    var depth = 0
    navigateSubmarine(
        input,
        forwardAction = {
            horizontalPosition += it
            depth += it * aim
        },
        downAction = { aim += it },
        upAction = { aim -= it },
    )
    return horizontalPosition * depth
}

private fun navigateSubmarine(
    input: List<String>,
    forwardAction: (Int) -> Unit,
    downAction: (Int) -> Unit,
    upAction: (Int) -> Unit,
) {
    input.stream()
        .map { it.split(' ').let { list -> list[0] to list[1].toInt() } }
        .forEach { (direction, distance) ->
            when (direction) {
                "forward" -> forwardAction(distance)
                "down" -> downAction(distance)
                "up" -> upAction(distance)
                else -> error("Input is wrong")
            }
        }
}
