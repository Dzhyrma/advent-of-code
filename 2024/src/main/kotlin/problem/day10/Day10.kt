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
    fun bfs(row: Int, col: Int, targetHeight: Char, visited: MutableSet<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        if (row !in input.indices || col !in input[row].indices ||
            (row to col) in visited || input[row][col] != targetHeight
        ) {
            return emptySet()
        }
        visited.add(row to col)
        val nextHeight = targetHeight + 1
        return bfs(row - 1, col, nextHeight, visited) +
            bfs(row + 1, col, nextHeight, visited) +
            bfs(row, col - 1, nextHeight, visited) +
            bfs(row, col + 1, nextHeight, visited) +
            if (targetHeight == '9') setOf(row to col) else emptySet()
    }

    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') bfs(rowIndex, colIndex, '0', mutableSetOf()).size else 0
        }.sum()
    }.sum()
}

fun solveDay10Part2(input: List<String>): Int {
    fun dfs(row: Int, col: Int, targetHeight: Char, visited: Set<Pair<Int, Int>>): Int {
        if (row !in input.indices || col !in input[row].indices ||
            (row to col) in visited || input[row][col] != targetHeight
        ) {
            return 0
        }
        val newVisited = visited + (row to col)
        if (targetHeight == '9') return 1
        val nextHeight = targetHeight + 1
        return dfs(row - 1, col, nextHeight, newVisited) +
            dfs(row + 1, col, nextHeight, newVisited) +
            dfs(row, col - 1, nextHeight, newVisited) +
            dfs(row, col + 1, nextHeight, newVisited)
    }

    return input.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            if (char == '0') dfs(rowIndex, colIndex, '0', emptySet()) else 0
        }.sum()
    }.sum()
}
