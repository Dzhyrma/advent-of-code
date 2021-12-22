package problem.day22

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 22
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay22Part1, ::solveDay22Part2)
}

private val CUBOID_REGEX = """.*x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)""".toRegex()

data class Cuboid(
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange,
) {
    private val fullSize: Long by lazy {
        (xRange.last - xRange.first + 1).toLong() *
                (yRange.last - yRange.first + 1).toLong() *
                (zRange.last - zRange.first + 1).toLong()
    }

    private val isEmpty = xRange.isEmpty() || yRange.isEmpty() || zRange.isEmpty()

    val isBig = xRange.first < -50 || yRange.first < -50 || zRange.first < -50
            || xRange.last > 50 || yRange.last > 50 || zRange.last > 50

    val cutOffCuboids: MutableList<Cuboid> = mutableListOf()

    val size: Long
        get() = fullSize - cutOffCuboids.sumOf { it.size }

    fun add(other: Cuboid) {
        val intersected = intersect(other)
        if (!intersected.isEmpty) {
            cutOffCuboids.forEach { cuboid -> cuboid.remove(intersected) }
        }
    }

    fun remove(other: Cuboid) {
        val intersected = intersect(other)
        if (!intersected.isEmpty) {
            cutOffCuboids.forEach { cuboid -> cuboid.remove(intersected) }
            cutOffCuboids.add(intersected)
        }
    }

    private fun intersect(other: Cuboid) = Cuboid(
        xRange intersect other.xRange,
        yRange intersect other.yRange,
        zRange intersect other.zRange,
    )
}

fun solveDay22Part1(input: List<String>): Long {
    val mainCuboid = Cuboid(
        Int.MIN_VALUE..Int.MAX_VALUE,
        Int.MIN_VALUE..Int.MAX_VALUE,
        Int.MIN_VALUE..Int.MAX_VALUE,
    )

    input.forEach { line ->
        val (on, cuboid) = line.parseCuboid()

        if (!cuboid.isBig) {
            if (on) {
                mainCuboid.remove(cuboid)
            } else {
                mainCuboid.add(cuboid)
            }
        }
    }

    return mainCuboid.cutOffCuboids.sumOf { it.size }
}

fun solveDay22Part2(input: List<String>): Long {
    val mainCuboid = Cuboid(
        Int.MIN_VALUE..Int.MAX_VALUE,
        Int.MIN_VALUE..Int.MAX_VALUE,
        Int.MIN_VALUE..Int.MAX_VALUE,
    )

    input.forEach { line ->
        val (on, cuboid) = line.parseCuboid()

        if (on) {
            mainCuboid.remove(cuboid)
        } else {
            mainCuboid.add(cuboid)
        }
    }

    return mainCuboid.cutOffCuboids.sumOf { it.size }
}

private fun String.parseCuboid(): Pair<Boolean, Cuboid> {
    return (this[1] == 'n') to CUBOID_REGEX.matchEntire(this).let {
        checkNotNull(it)
        Cuboid(
            it.groupValues[1].toInt()..it.groupValues[2].toInt(),
            it.groupValues[3].toInt()..it.groupValues[4].toInt(),
            it.groupValues[5].toInt()..it.groupValues[6].toInt(),
        )
    }
}

private infix fun IntRange.intersect(other: IntRange): IntRange {
    return max(first, other.first)..min(last, other.last)
}