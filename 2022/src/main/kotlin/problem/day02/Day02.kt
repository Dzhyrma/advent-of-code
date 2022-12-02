package problem.day02

import common.InputRepo
import common.readSessionCookie
import common.solve
import problem.day02.Outcome.*

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

enum class Outcome(
    val value: Int,
) {
    LOST(0),
    DRAW(3),
    WON(6),
}

fun solveDay02Part1(input: List<String>): Int {
    return input
        .map { (it[0] - 'A') to (it[2] - 'X') }
        .sumOf { (elfsRps, yourRps) ->
            when {
                elfsRps == yourRps -> DRAW
                yourRps == (elfsRps + 1) % 3 -> WON
                else -> LOST
            }.value + yourRps + 1
        }
}

fun solveDay02Part2(input: List<String>): Int {
    return input
        .map { (it[0] - 'A') to it[2].toOutcome() }
        .sumOf { (elfsRpsEncoded, outcome) ->
            when (outcome) {
                DRAW -> elfsRpsEncoded
                LOST -> (elfsRpsEncoded + 2) % 3
                WON -> (elfsRpsEncoded + 1) % 3
            } + 1 + outcome.value
        }
}

private fun Char.toOutcome(): Outcome = when (this) {
    'X' -> LOST
    'Y' -> DRAW
    'Z' -> WON
    else -> error("Wrong input")
}
