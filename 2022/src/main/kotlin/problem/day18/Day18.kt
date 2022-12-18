package problem.day18

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 18
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay18Part1, ::solveDay18Part2)
}

private data class Point(
    val x: Int,
    val y: Int,
    val z: Int,
) {

    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
}

fun solveDay18Part1(input: List<String>): Int {
    val cubes = parseCubeCoordinates(input)
    return countSurfaceArea(cubes)
}

fun solveDay18Part2(input: List<String>): Int {
    val cubes = parseCubeCoordinates(input)
    val xRange = cubes.minOf { it.x }..cubes.maxOf { it.x }
    val yRange = cubes.minOf { it.y }..cubes.maxOf { it.y }
    val zRange = cubes.minOf { it.z }..cubes.maxOf { it.z }
    val knownExteriorCubes = mutableSetOf<Point>()
    xRange.forEach { x ->
        yRange.forEach { y ->
            zRange.forEach { z ->
                val cube = Point(x, y, z)
                if (!cubes.contains(cube) && !knownExteriorCubes.contains(cube)) {
                    val (isInterrior, resultingCubes) = cubes.tryFillInterior(
                        cube,
                        xRange,
                        yRange,
                        zRange,
                        knownExteriorCubes
                    )
                    if (isInterrior) {
                        cubes.addAll(resultingCubes)
                    } else {
                        knownExteriorCubes.addAll(resultingCubes)
                    }
                }
            }
        }
    }
    return countSurfaceArea(cubes)
}

private fun countSurfaceArea(cubes: MutableSet<Point>) =
    cubes.sumOf { cube -> 6 - NEIGHBORS.count { cubes.contains(cube + it) } }

private fun parseCubeCoordinates(input: List<String>) =
    input.map { it.split(',') }.map { Point(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toMutableSet()

private fun Set<Point>.tryFillInterior(
    cube: Point,
    xRange: IntRange,
    yRange: IntRange,
    zRange: IntRange,
    knownExteriorCubes: Set<Point>,
    visited: MutableSet<Point> = this.toMutableSet(),
): Pair<Boolean, Set<Point>> {
    visited.add(cube)
    if (cube.x !in xRange || cube.y !in yRange || cube.z !in zRange || knownExteriorCubes.contains(cube))
        return false to setOf(cube)

    val resultingSet = mutableSetOf(cube)
    return NEIGHBORS.asSequence()
        .map { cube + it }
        .filter { !visited.contains(it) }
        .map { tryFillInterior(it, xRange, yRange, zRange, knownExteriorCubes, visited) }
        .all {
            resultingSet.addAll(it.second)
            it.first
        }.let { it to resultingSet }
}

private val NEIGHBORS = listOf(
    Point(1, 0, 0),
    Point(-1, 0, 0),
    Point(0, 1, 0),
    Point(0, -1, 0),
    Point(0, 0, 1),
    Point(0, 0, -1),
)
