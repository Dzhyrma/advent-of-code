package problem.day18

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val day = 18
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay18Part1, ::solveDay18Part2)
}

fun solveDay18Part1(input: List<String>, gridSize: Int = 71, takeBytes: Int = 1024): Int {
    return input.parse(gridSize, takeBytes).minStepsToExit(gridSize)
}

fun solveDay18Part2(input: List<String>, gridSize: Int = 71, takeBytes: Int = 1024): String {
    val grid = input.parse(gridSize, takeBytes)
    var nextIndex = takeBytes
    while (grid.minStepsToExit(gridSize) != -1) {
        val (x, y) = input[nextIndex++].split(',').map { it.toInt() }
        grid[y][x] = false
    }
    return input[nextIndex - 1]
}

private fun List<String>.parse(gridSize: Int, bytes: Int) = Array(gridSize) { BooleanArray(gridSize) { true } }.also {
    take(bytes).map { row ->
        val (x, y) = row.split(",").map { it.toInt() }
        it[y][x] = false
    }
}

private fun Array<BooleanArray>.minStepsToExit(gridSize: Int): Int {
    val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

    val queue = PriorityQueue(compareBy<Triple<Int, Int, Int>> { it.third })
    queue.add(Triple(0, 0, 0))
    val visited = mutableSetOf(0 to 0)

    while (queue.isNotEmpty()) {
        val (x, y, steps) = queue.poll()

        if (x == gridSize - 1 && y == gridSize - 1) return steps

        for ((dx, dy) in directions) {
            val nx = x + dx
            val ny = y + dy
            if (nx in 0 until gridSize && ny in 0 until gridSize && this[ny][nx] && (nx to ny) !in visited) {
                visited.add(nx to ny)
                queue.add(Triple(nx, ny, steps + 1))
            }
        }
    }

    return -1
}
