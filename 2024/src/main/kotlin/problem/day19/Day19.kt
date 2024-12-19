package problem.day19

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 19
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay19Part1, ::solveDay19Part2)
}

fun solveDay19Part1(input: List<String>): Int {
    val (towelPatterns, designs) = input.parseInput()
    return designs.count { countWays(it, towelPatterns) > 0 }
}

fun solveDay19Part2(input: List<String>): Long {
    val (towelPatterns, designs) = input.parseInput()
    return designs.sumOf { countWays(it, towelPatterns) }
}

private fun List<String>.parseInput() = this[0].split(", ").toSet() to this.drop(2)

private fun countWays(design: String, towelPatterns: Set<String>, memo: MutableMap<String, Long> = mutableMapOf()): Long {
    if (design.isEmpty()) return 1
    memo[design]?.let { return it }
    return towelPatterns
        .filter { design.startsWith(it) }
        .sumOf { pattern -> countWays(design.removePrefix(pattern), towelPatterns, memo) }
        .also { memo[design] = it }
}
