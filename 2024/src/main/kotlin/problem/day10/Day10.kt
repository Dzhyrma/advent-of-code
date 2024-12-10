package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve

typealias Pos = Pair<Int, Int>

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)
    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

fun solveDay10Part1(input: List<String>): Int {
    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') input.dfs(rowIndex, colIndex).toSet().size else 0
        }.sum()
    }.sum()
}

fun solveDay10Part2(input: List<String>): Int {
    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') input.dfs(rowIndex, colIndex).size else 0
        }.sum()
    }.sum()
}

private fun List<String>.dfs(row: Int, col: Int, target: Char = '0', visited: Set<Pos> = setOf()): List<Pos> {
    return when {
        row !in this.indices || col !in this[row].indices ||
            (row to col) in visited || this[row][col] != target -> emptyList()
        target == '9' -> listOf(row to col)
        else -> {
            val newVisited = visited + (row to col)
            val next = target + 1
            return dfs(row - 1, col, next, newVisited) +
                dfs(row + 1, col, next, newVisited) +
                dfs(row, col - 1, next, newVisited) +
                dfs(row, col + 1, next, newVisited)
        }
    }
}
