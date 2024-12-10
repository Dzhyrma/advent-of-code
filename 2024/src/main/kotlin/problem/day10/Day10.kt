package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)
    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

fun solveDay10Part1(input: List<String>): Int {
    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') input.dfs(rowIndex, colIndex, '0', setOf()).toSet().size else 0
        }.sum()
    }.sum()
}

fun solveDay10Part2(input: List<String>): Int {
    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') input.dfs(rowIndex, colIndex, '0', setOf()).size else 0
        }.sum()
    }.sum()
}

fun List<String>.dfs(row: Int, col: Int, targetHeight: Char, visited: Set<Pair<Int, Int>>): List<Pair<Int, Int>> {
    return when {
        row !in this.indices || col !in this[row].indices ||
            (row to col) in visited || this[row][col] != targetHeight -> emptyList()
        targetHeight == '9' -> listOf(row to col)
        else -> {
            val newVisited = visited + (row to col)
            val nextHeight = targetHeight + 1
            return dfs(row - 1, col, nextHeight, newVisited) +
                dfs(row + 1, col, nextHeight, newVisited) +
                dfs(row, col - 1, nextHeight, newVisited) +
                dfs(row, col + 1, nextHeight, newVisited)
        }
    }
}
