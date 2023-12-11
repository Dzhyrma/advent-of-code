package problem.day11

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 11
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay11Part1, ::solveDay11Part2)
}

fun solveDay11Part1(input: List<String>): Long {
    return findTotalGalaxyDistances(input)
}

fun solveDay11Part2(input: List<String>, multiplier: Long = 1000000): Long {
    return findTotalGalaxyDistances(input, multiplier)
}

private fun findTotalGalaxyDistances(input: List<String>, multiplier: Long = 2L): Long {
    val emptyRowIndices = input.mapIndexedNotNull { index, line ->
        if (line.all { it == '.' }) index else null
    }
    val emptyColumnIndices = (0 until input[0].length).filter { index ->
        input.all { it[index] == '.' }
    }
    val gCoords = input.mapIndexed { rowIndex, line ->
        line.mapIndexedNotNull { columnIndex, char ->
            if (char == '#') rowIndex to columnIndex else null
        }
    }.reduce { acc, pairs -> acc + pairs }
    return (0 until gCoords.size - 1).sumOf { i ->
        (i + 1 until gCoords.size).sumOf { j ->
            val rowIndices = min(gCoords[i].first, gCoords[j].first)..max(gCoords[i].first, gCoords[j].first)
            val yDistance =
                rowIndices.last - rowIndices.first + emptyRowIndices.count { it in rowIndices } * (multiplier - 1)
            val columnIndices = min(gCoords[i].second, gCoords[j].second)..max(gCoords[i].second, gCoords[j].second)
            val xDistance =
                columnIndices.last - columnIndices.first + emptyColumnIndices.count { it in columnIndices } * (multiplier - 1)
            yDistance + xDistance
        }
    }
}
