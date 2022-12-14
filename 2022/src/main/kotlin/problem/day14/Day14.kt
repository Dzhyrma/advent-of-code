package problem.day14

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 14
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay14Part1, ::solveDay14Part2)
}

private data class Path(
    val pointA: Pair<Int, Int>,
    val pointB: Pair<Int, Int>,
) {
    private val xRange = min(pointA.first, pointB.first)..max(pointA.first, pointB.first)
    private val yRange = min(pointA.second, pointB.second)..max(pointA.second, pointB.second)

    val points: List<Pair<Int, Int>>
        get() = when (pointA.first) {
            pointB.first -> yRange.map { Pair(pointA.first, it) }
            else -> xRange.map { Pair(it, pointA.second) }
        }
}

fun solveDay14Part1(input: List<String>): Int {
    val paths = parsePaths(input)
    val maxY = paths.maxOf { max(it.pointB.second, it.pointA.second) }
    val occupiedPointsBySand = pourSand(maxY, paths)
    print(paths, occupiedPointsBySand)
    return occupiedPointsBySand.size
}

fun solveDay14Part2(input: List<String>): Int {
    val paths = parsePaths(input)
    val minX = paths.minOf { min(it.pointB.first, it.pointA.first) }
    val maxX = paths.maxOf { max(it.pointB.first, it.pointA.first) }
    val maxY = paths.maxOf { max(it.pointB.second, it.pointA.second) } + 2
    val newPaths = paths + listOf(Path(Pair(minX - maxY, maxY), Pair(maxX + maxY, maxY)))
    val occupiedPointsBySand = pourSand(maxY, newPaths)
    print(newPaths, occupiedPointsBySand)
    return occupiedPointsBySand.size
}

private fun parsePaths(input: List<String>) = input.flatMap { row ->
    row.split(" -> ")
        .map { it.split(',') }
        .map { Pair(it[0].toInt(), it[1].toInt()) }
        .zipWithNext { first, second -> Path(first, second) }
}

private fun pourSand(
    maxY: Int,
    paths: List<Path>,
): MutableSet<Pair<Int, Int>> {
    val pathPoints = paths.flatMapTo(mutableSetOf()) { it.points }
    val sandPoints = mutableSetOf<Pair<Int, Int>>()
    var sandPoint = Pair(500, 0)
    while (sandPoint.second <= maxY && !sandPoints.contains(sandPoint)) {
        val down = Pair(sandPoint.first, sandPoint.second + 1)
        val downLeft = Pair(sandPoint.first - 1, sandPoint.second + 1)
        val downRight = Pair(sandPoint.first + 1, sandPoint.second + 1)
        sandPoint = when {
            !sandPoints.contains(down) && !pathPoints.contains(down) -> down
            !sandPoints.contains(downLeft) && !pathPoints.contains(downLeft) -> downLeft
            !sandPoints.contains(downRight) && !pathPoints.contains(downRight) -> downRight
            else -> {
                sandPoints.add(sandPoint)
                Pair(500, 0)
            }
        }
    }
    return sandPoints
}

private fun print(
    paths: List<Path>,
    occupiedPoints: MutableSet<Pair<Int, Int>>
) {
    val minXX = min(paths.minOf { min(it.pointB.first, it.pointA.first) }, occupiedPoints.minOf { it.first })
    val maxXX = max(paths.maxOf { max(it.pointB.first, it.pointA.first) }, occupiedPoints.maxOf { it.first })
    val minYY = min(paths.minOf { min(it.pointB.second, it.pointA.second) }, occupiedPoints.minOf { it.second })
    val maxYY = max(paths.maxOf { max(it.pointB.second, it.pointA.second) }, occupiedPoints.maxOf { it.second })
    val pathPoints = paths.flatMapTo(mutableSetOf()) { it.points }
    (minYY..maxYY).forEach { y ->
        (minXX..maxXX).forEach { x ->
            val point = Pair(x, y)
            when {
                point.first == 500 && point.second == 0 -> print("X")
                pathPoints.contains(point) -> print("#")
                occupiedPoints.contains(point) -> print("o")
                else -> print(".")
            }
        }
        println()
    }
}
