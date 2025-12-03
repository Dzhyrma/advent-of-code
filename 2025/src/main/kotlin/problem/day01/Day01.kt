package problem.day01

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
}

fun solveDay01Part1(input: List<String>): Int = input.calculatePassword()

fun solveDay01Part2(input: List<String>): Int = input.calculatePassword(withClicks = true)

private fun List<String>.calculatePassword(withClicks: Boolean = false): Int {
    var current = 50
    var result = 0
    forEach { action ->
        when {
            action.startsWith("L") -> {
                var previous = current
                current = (current - action.drop(1).toInt())
                while (current < 0) {
                    current += 100
                    if (previous != 0 && withClicks) {
                        result++
                    }
                    previous = current
                }
            }
            action.startsWith("R") -> {
                current = current + action.drop(1).toInt()
                while (current > 99) {
                    current -= 100
                    if (current != 0 && withClicks) {
                        result++
                    }
                }
            }
        }
        if (current == 0) {
            result++
        }
    }
    return result
}
