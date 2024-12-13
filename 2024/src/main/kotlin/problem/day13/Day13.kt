package problem.day13

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 13
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay13Part1, ::solveDay13Part2)
}

fun solveDay13Part1(input: List<String>): Long {
    return input.calculateMinimumTokens()
}

fun solveDay13Part2(input: List<String>): Long {
    return input.calculateMinimumTokens(adjust = true)
}

private fun List<String>.calculateMinimumTokens(adjust: Boolean = false): Long =
    (indices step 4).sumOf { i ->
        val buttonA = this[i].split(": ")[1].split(", ").map { it.substring(2).toInt() }
        val buttonB = this[i + 1].split(": ")[1].split(", ").map { it.substring(2).toInt() }
        val prize = this[i + 2].split(": ")[1].split(", ")
            .map { it.substring(2).toLong() + if (adjust) 10_000_000_000_000L else 0 }

        findSolution(buttonA[0], buttonA[1], buttonB[0], buttonB[1], prize[0], prize[1])
    }

private fun findSolution(dxA: Int, dyA: Int, dxB: Int, dyB: Int, prizeX: Long, prizeY: Long): Long {
    val a = (dxB * prizeY - dyB * prizeX) / (dyA * dxB - dxA * dyB)
    val b = (prizeX - a * dxA) / dxB
    if ((prizeX - a * dxA) % dxB == 0L && (dxB * prizeY - dyB * prizeX) % (dyA * dxB - dxA * dyB) == 0L) {
        return 3 * a + b
    }
    return 0
}
