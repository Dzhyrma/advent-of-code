package problem.day08

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}


fun solveDay08Part1(input: List<String>): Int {
    val grid = input.map { it.map { c -> c - '0' } }
    val (height, width) = grid.size to grid.first().size
    val visibleTrees = Array(height) { Array(width) { false } }
    val (maxHightsFromLeft, maxHightsFromRight) = Array(width) { Int.MIN_VALUE } to Array(width) { Int.MIN_VALUE }
    val (maxHightsFromTop, maxHightsFromBottom) = Array(height) { Int.MIN_VALUE } to Array(height) { Int.MIN_VALUE }

    for (y in 0 until height) {
        for (x in 0 until width) {
            visibleTrees[y][x] = visibleTrees[y][x]
                    || maxHightsFromLeft[y] < grid[y][x]
                    || maxHightsFromTop[x] < grid[y][x]
            val (x2, y2) = width - 1 - x to height - 1 - y
            visibleTrees[y2][x2] = visibleTrees[y2][x2]
                    || maxHightsFromRight[y2] < grid[y2][x2]
                    || maxHightsFromBottom[x2] < grid[y2][x2]
            maxHightsFromLeft[y] = max(maxHightsFromLeft[y], grid[y][x])
            maxHightsFromRight[y2] = max(maxHightsFromRight[y2], grid[y2][x2])
            maxHightsFromTop[x] = max(maxHightsFromTop[x], grid[y][x])
            maxHightsFromBottom[x2] = max(maxHightsFromBottom[x2], grid[y2][x2])
        }
    }

    return visibleTrees.sumOf { row -> row.count { it } }
}

fun solveDay08Part2(input: List<String>): Int {
    val grid = input.map { it.map { c -> c - '0' } }
    return (grid.indices).maxOf { y ->
        grid[y].indices.maxOf { x ->
            grid.scenicScore(x, y)
        }
    }
}

private fun List<List<Int>>.scenicScore(x: Int, y: Int) =
    scenicScore(this[y][x], x + 1, y, 1, 0) *
            scenicScore(this[y][x], x - 1, y, -1, 0) *
            scenicScore(this[y][x], x, y + 1, 0, 1) *
            scenicScore(this[y][x], x, y - 1, 0, -1)

private fun List<List<Int>>.scenicScore(height: Int, x: Int, y: Int, dx: Int, dy: Int): Int = when {
    y < 0 || y >= size || x < 0 || x >= this[y].size -> 0
    this[y][x] >= height -> 1
    else -> scenicScore(height, x + dx, y + dy, dx, dy) + 1
}
