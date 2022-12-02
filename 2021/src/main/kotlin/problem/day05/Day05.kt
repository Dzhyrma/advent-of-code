package problem.day05

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

data class Point(
    val x: Int,
    val y: Int,
)

fun solveDay05Part1(input: List<String>): Int {
    return countOverlappedPoints(input)
}

fun solveDay05Part2(input: List<String>): Int {
    return countOverlappedPoints(input, true)
}

private fun countOverlappedPoints(input: List<String>, includingDiagonals: Boolean = false): Int {
    val coordinates = mutableMapOf<Point, Int>()
    var overlapCounter = 0

    input.forEach { line ->
        line.split("->")
            .map { it.trim().split(',').let { xy -> xy[0].toInt() to xy[1].toInt() } }
            .also {
                val (x0, y0) = it[0]
                val (x1, y1) = it[1]

                if (x0 == x1) {
                    y0.to(y1).forEach { y ->
                        val point = Point(x0, y)
                        coordinates[point] = (coordinates[point] ?: 0) + 1
                        if (coordinates[point] == 2) overlapCounter++
                    }
                } else if (y0 == y1) {
                    x0.to(x1).forEach { x ->
                        val point = Point(x, y0)
                        coordinates[point] = (coordinates[point] ?: 0) + 1
                        if (coordinates[point] == 2) overlapCounter++
                    }
                } else if (includingDiagonals) {
                    val dx = if (x0 < x1) 1 else -1
                    val dy = if (y0 < y1) 1 else -1
                    var (x, y) = x0 to y0
                    repeat(abs(x0 - x1) + 1) {
                        val point = Point(x, y)
                        coordinates[point] = (coordinates[point] ?: 0) + 1
                        if (coordinates[point] == 2) overlapCounter++
                        x += dx
                        y += dy
                    }
                }
            }
    }
    return overlapCounter
}

fun Int.to(next: Int): IntRange {
    return if (this > next) (next..this) else (this..next)
}