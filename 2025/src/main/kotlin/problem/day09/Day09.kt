package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay09Part1, ::solveDay09Part2)
}

fun solveDay09Part1(input: List<String>): Long {
    val coordinates = input.readCoordinates()
    return coordinates.maxOf { (x1, y1) ->
        coordinates.maxOf { (x2, y2) ->
            (abs(x1 - x2) + 1).toLong() * (abs(y1 - y2) + 1)
        }
    }
}

fun solveDay09Part2(input: List<String>): Long {
    val coordinates = input.readCoordinates()
    val polygonEdges = (coordinates + listOf(coordinates.first())).zipWithNext()

    val containsCache = mutableMapOf<Pair<Int, Int>, Boolean>()

    fun isPointInPolygon(
        x: Int,
        y: Int,
    ): Boolean {
        val key = x to y
        return containsCache.getOrPut(key) {
            var inside = false
            var j = coordinates.size - 1

            for (i in coordinates.indices) {
                val xi = coordinates[i].first
                val yi = coordinates[i].second
                val xj = coordinates[j].first
                val yj = coordinates[j].second

                if ((xi == x && yi == y) || (xj == x && yj == y)) {
                    return@getOrPut true
                }

                if ((yi > y) != (yj > y)) {
                    val dx = xi - xj
                    val dy = yi - yj
                    val crossProduct = (x - xj).toLong() * dy - (y - yj).toLong() * dx

                    if ((dy > 0 && crossProduct < 0) || (dy < 0 && crossProduct > 0)) {
                        inside = !inside
                    }
                }
                j = i
            }
            inside
        }
    }

    fun segmentsIntersect(
        p1: Pair<Int, Int>,
        p2: Pair<Int, Int>,
        p3: Pair<Int, Int>,
        p4: Pair<Int, Int>,
    ): Boolean {
        val (x1, y1) = p1
        val (x2, y2) = p2
        val (x3, y3) = p3
        val (x4, y4) = p4

        if (p1 == p3 || p1 == p4 || p2 == p3 || p2 == p4) {
            return false
        }

        val denom = (x1 - x2).toLong() * (y3 - y4) - (y1 - y2).toLong() * (x3 - x4)

        if (denom == 0L) {
            if (x1 == x2 && x3 == x4 && x1 == x3) {
                val minY1 = minOf(y1, y2)
                val maxY1 = maxOf(y1, y2)
                val minY3 = minOf(y3, y4)
                val maxY3 = maxOf(y3, y4)
                return maxOf(minY1, minY3) < minOf(maxY1, maxY3)
            } else if (y1 == y2 && y3 == y4 && y1 == y3) {
                val minX1 = minOf(x1, x2)
                val maxX1 = maxOf(x1, x2)
                val minX3 = minOf(x3, x4)
                val maxX3 = maxOf(x3, x4)
                return maxOf(minX1, minX3) < minOf(maxX1, maxX3)
            }
            return false
        }

        val t = ((x1 - x3).toLong() * (y3 - y4) - (y1 - y3).toLong() * (x3 - x4)).toDouble() / denom
        val u = -((x1 - x2).toLong() * (y1 - y3) - (y1 - y2).toLong() * (x1 - x3)).toDouble() / denom

        return t > 0 && t < 1 && u > 0 && u < 1
    }

    return coordinates
        .flatMapIndexed { index, (x1, y1) ->
            (index + 1 until coordinates.size).map {
                val (x2, y2) = coordinates[it]
                val area = (abs(x1 - x2) + 1).toLong() * (abs(y1 - y2) + 1)
                area to ((x1 to y1) to (x2 to y2))
            }
        }.sortedByDescending { it.first }
        .first { (_, coords) ->
            val minX = minOf(coords.first.first, coords.second.first)
            val maxX = maxOf(coords.first.first, coords.second.first)
            val minY = minOf(coords.first.second, coords.second.second)
            val maxY = maxOf(coords.first.second, coords.second.second)

            val rectEdges =
                listOf(
                    (minX to minY) to (maxX to minY),
                    (maxX to minY) to (maxX to maxY),
                    (maxX to maxY) to (minX to maxY),
                    (minX to maxY) to (minX to minY),
                )

            isPointInPolygon(minX, minY) &&
                isPointInPolygon(maxX, minY) &&
                isPointInPolygon(maxX, maxY) &&
                isPointInPolygon(minX, maxY) &&
                rectEdges.all { rectEdge ->
                    polygonEdges.all { polyEdge ->
                        !segmentsIntersect(rectEdge.first, rectEdge.second, polyEdge.first, polyEdge.second)
                    }
                }
        }.first
}

private fun List<String>.readCoordinates(): List<Pair<Int, Int>> =
    map { line ->
        val parts = line.split(",")
        Pair(parts[0].toInt(), parts[1].toInt())
    }
