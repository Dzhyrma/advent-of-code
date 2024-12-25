package problem.day25

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 25
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay25Part1, ::solveDay24Part2)
}

fun solveDay25Part1(input: List<String>): Int {
    return countFittingPairs(input)
}

fun solveDay24Part2(input: List<String>): String {
    return "Merry Christmas!"
}

private fun countFittingPairs(input: List<String>): Int {
    val rows = input.filter { it != "" }.chunked(7)
    val locks = rows.filter { schematics -> schematics[0].all { it == '#' } }.map { parseHeights(it, true) }
    val keys = rows.filter { schematics -> schematics[0].all { it == '.' } }.map { parseHeights(it, false) }

    return locks.sumOf { lock ->
        keys.count { key ->
            lock.zip(key).all { (l, k) -> l + k <= 7 }
        }
    }
}

private fun parseHeights(schematic: List<String>, fromTop: Boolean) = (if (fromTop) schematic else schematic.reversed()).let { sch ->
    sch.first().mapIndexed { col, _ ->
        sch.takeWhile { it[col] == '#' }.size
    }
}
