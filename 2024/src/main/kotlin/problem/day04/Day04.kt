package problem.day04

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}

fun solveDay04Part1(input: List<String>): Int {
    fun findWord(x: Int, y: Int, dx: Int, dy: Int) = WORD.foldIndexed(true) { i, acc, letter ->
        val nx = x + i * dx
        val ny = y + i * dy
        acc && ny in input.indices && nx in input[ny].indices && input[ny][nx] == letter
    }
    return input.indices.sumOf { y ->
        input[y].indices.sumOf { x ->
            DIRECTIONS.count { (dx, dy) -> findWord(x, y, dx, dy) }
        }
    }
}

fun solveDay04Part2(input: List<String>): Long {
    fun isValidMas(str: String) = str == "MAS" || str == "SAM"
    return (1 until input.size - 1).sumOf { y ->
        (1 until input[y].length - 1).sumOf { x ->
            if (input[y][x] != 'A'
                || !isValidMas("${input[y - 1][x - 1]}A${input[y + 1][x + 1]}")
                || !isValidMas("${input[y - 1][x + 1]}A${input[y + 1][x - 1]}")
            ) 0L else 1L
        }
    }
}

private const val WORD = "XMAS"
private val DIRECTIONS = listOf(
    Pair(0, 1),   // Right
    Pair(0, -1),  // Left
    Pair(1, 0),   // Down
    Pair(-1, 0),  // Up
    Pair(1, 1),   // Diagonal: Down-Right
    Pair(-1, -1), // Diagonal: Up-Left
    Pair(1, -1),  // Diagonal: Down-Left
    Pair(-1, 1)   // Diagonal: Up-Right
)