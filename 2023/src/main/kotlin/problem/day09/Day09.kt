package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve
import problem.day08.solveDay08Part1
import problem.day08.solveDay08Part2

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}

fun solveDay09Part1(input: List<String>): Long {
    TODO()
}

fun solveDay09Part2(input: List<String>): Long {
    TODO()
}
