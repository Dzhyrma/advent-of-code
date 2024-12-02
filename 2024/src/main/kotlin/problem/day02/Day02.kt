package problem.day02

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

fun solveDay02Part1(input: List<String>): Int {
    return input.countSafeReports()
}

fun solveDay02Part2(input: List<String>): Int {
    return input.countSafeReports(tolerateOneBadLevel = true)
}

private fun List<String>.countSafeReports(tolerateOneBadLevel: Boolean = false) =
    map { line -> line.split(" ").mapNotNull { it.toIntOrNull() } }
        .count { levels ->
            levels.allLevelsAreSafe() ||
                    (tolerateOneBadLevel && levels.indices.any { indexToDrop ->
                        (levels.dropLast(levels.size - indexToDrop) + levels.drop(indexToDrop + 1))
                            .allLevelsAreSafe()
                    })
        }

private fun List<Int>.allLevelsAreSafe() = zipWithNext { a, b -> a - b }
    .let { diffs ->
        diffs.all { abs(it) <= 3 && it != 0 } && (diffs.all { it > 0 } || diffs.all { it < 0 })
    }