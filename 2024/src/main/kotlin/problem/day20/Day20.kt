package problem.day20

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 20
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay20Part1, ::solveDay20Part2)
}

fun solveDay20Part1(input: List<String>, save: Int = 100): Int {
    return input.countCheats(save, 2)
}

fun solveDay20Part2(input: List<String>, save: Int = 100): Int {
    return input.countCheats(save, 20)
}

private fun List<String>.parseRacetrack(): Pair<Array<CharArray>, Pair<Int, Int>> {
    val grid = map { it.toCharArray() }.toTypedArray()
    var end: Pair<Int, Int>? = null

    for (y in indices) {
        for (x in this[y].indices) {
            when (this[y][x]) {
                'E' -> end = Pair(x, y)
            }
        }
    }

    return grid to end!!
}

private fun List<String>.countCheats(save: Int, cheats: Int): Int {
    val (grid, end) = parseRacetrack()
    val rows = grid.size
    val cols = grid[0].size
    val dToEnd = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }

    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
    val queue: Queue<Triple<Int, Int, Int>> = LinkedList()
    val visited = mutableSetOf<Pair<Int, Int>>()

    dToEnd[end.second][end.first] = 0
    queue.add(Triple(end.first, end.second, 0))

    while (queue.isNotEmpty()) {
        val (x, y, dist) = queue.poll()
        if (visited.contains(Pair(x, y))) continue
        dToEnd[y][x] = dist
        visited.add(Pair(x, y))

        for ((dx, dy) in directions) {
            val nx = x + dx
            val ny = y + dy
            if (nx in 0 until cols && ny in 0 until rows) {
                if (grid[ny][nx] != '#') {
                    queue.add(Triple(nx, ny, dist + 1))
                }
            }
        }
    }

    var counter = 0
    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            if (dToEnd[y][x] != Int.MAX_VALUE) {
                for (cy in (max(0, y - cheats)..min(rows - 1, y + cheats))) {
                    for (cx in (max(0, x - cheats)..min(cols - 1, x + cheats))) {
                        val distance = abs(cx - x) + abs(cy - y)
                        if (distance > cheats) continue
                        if (dToEnd[cy][cx] == Int.MAX_VALUE) continue
                        if (dToEnd[y][x] - dToEnd[cy][cx] >= save + distance) counter++
                    }
                }
            }
        }
    }

    return counter
}
