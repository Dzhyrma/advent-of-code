package problem.day17

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.lang.Integer.max

fun main(args: Array<String>) {
    val day = 17
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay17Part1, ::solveDay17Part2)
}


fun solveDay17Part1(input: List<String>): Int {
    val (areaXRange, areaYRange) = input.first().parseTargetAreas()

    val velocitiesThatReach = calculateUniqueVelocities(areaXRange, areaYRange)
    return velocitiesThatReach.maxOf { it.value }
}

fun solveDay17Part2(input: List<String>): Int {
    val (areaXRange, areaYRange) = input.first().parseTargetAreas()

    val velocitiesThatReach = calculateUniqueVelocities(areaXRange, areaYRange)
    return velocitiesThatReach.size
}

private fun String.parseTargetAreas(): Pair<IntRange, IntRange> {
    return drop(15).split(", y=").map { rangeString ->
        rangeString.split("..").map { it.toInt() }.let { it[0]..it[1] }
    }.let { it[0] to it[1] }
}

private fun calculateUniqueVelocities(
    areaXRange: IntRange,
    areaYRange: IntRange,
) = (-1000..1000).fold(mutableMapOf<Pair<Int, Int>, Int>()) { map, initialDX ->
    map.also {
        it.putAll((-1000..1000).mapNotNull { initialDY ->
            var dX = if (areaXRange.first < 0) -initialDX else initialDX
            var dY = initialDY
            var x = 0
            var y = 0
            var maxY = 0
            var areaReached = false
            while (!areaReached && y > areaYRange.first) {
                x += dX
                y += dY
                maxY = max(maxY, y)
                if (dX > 0) dX-- else if (dX < 0) dX++
                dY--
                areaReached = x in areaXRange && y in areaYRange
            }
            if (areaReached) (initialDX to initialDY) to maxY else null
        })
    }
}