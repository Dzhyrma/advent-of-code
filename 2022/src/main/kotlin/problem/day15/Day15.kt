package problem.day15

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 15
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay15Part1, ::solveDay15Part2)
}


fun solveDay15Part1(input: List<String>, rowIndex: Int = 2000000): Int {
    val sensorsAndBeacons = parseInput(input)
    val impossibleCoordinates = extractImpossibleRanges(sensorsAndBeacons, rowIndex)
    return impossibleCoordinates.sumOf { it.last - it.first + 1 }
}

fun solveDay15Part2(input: List<String>, max: Int = 4000000): Long {
    val sensorsAndBeacons = parseInput(input)
    val limitRange = 0 .. max
    (0 .. max).forEach { row ->
        val ranges = extractImpossibleRanges(sensorsAndBeacons, row, false)
            .map { it.intersect(limitRange) }
            .filter { !it.isEmpty() }
        when {
            ranges.isEmpty() -> {}
            ranges.size > 1 -> return ((ranges.first { it.first == 0 }.last + 1) to row).score()
            ranges[0].first > 0 -> return (0 to row).score()
            ranges[0].last < max -> return (max to row).score()
            else -> {}
        }
    }
    return -1
}

private fun Pair<Int, Int>.score() = first * 4000000L + second

private fun parseInput(input: List<String>) = input.mapNotNull { INPUT_REGEX.matchEntire(it) }
    .map { it.groupValues.drop(1).map { value -> value.toInt() } }
    .map { Pair(it[0], it[1]) to Pair(it[2], it[3]) }

private fun extractImpossibleRanges(
    sensorsAndBeacons: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    rowIndex: Int,
    removeBeacon: Boolean = true,
): List<IntRange> {
    val noOtherBeaconRanges = sensorsAndBeacons.flatMap { (sensor, beacon) ->
        val mDistance = (sensor.first - beacon.first).absoluteValue + (sensor.second - beacon.second).absoluteValue
        val halfRangeSize = mDistance - (sensor.second - rowIndex).absoluteValue
        when {
            halfRangeSize <= 0 -> emptyList()
            removeBeacon && beacon.second == rowIndex -> listOf(
                sensor.first - halfRangeSize until beacon.first,
                beacon.first + 1..sensor.first + halfRangeSize,
            ).filter { !it.isEmpty() }
            else -> listOf(sensor.first - halfRangeSize..sensor.first + halfRangeSize)
        }
    }
    return noOtherBeaconRanges.unionAll()
}

private fun List<IntRange>.unionAll(): List<IntRange> {
    val ranges = toMutableSet()
    repeat(size) {
        val range = ranges.first()
        ranges.remove(range)
        val other = ranges.find { other -> other.overlaps(range) }
        if (other != null) {
            ranges.remove(other)
            ranges.add(min(range.first, other.first)..max(range.last, other.last))
        } else {
            ranges.add(range)
        }
    }
    return ranges.toList()
}

private fun IntRange.overlaps(other: IntRange) = first in other || other.first in this

private fun IntRange.intersect(other: IntRange) = max(first, other.first) .. min(last, other.last)

private val INPUT_REGEX = Regex("Sensor at x=(.+), y=(.+): closest beacon is at x=(.+), y=(.+)")
