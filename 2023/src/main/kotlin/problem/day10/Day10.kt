package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

fun solveDay10Part1(input: List<String>): Long {
    val distances = Array(input.size) { IntArray(input[0].length) { Int.MIN_VALUE } }
    solvePart1(input, distances)
    return distances.maxOf { it.max() }.toLong()
}

fun solveDay10Part2(input: List<String>): Long {
    val distances = Array(input.size) { IntArray(input[0].length) { Int.MIN_VALUE } }
    solvePart1(input, distances)
    val fixedInput = input.replaceStartingSymbol(findStartingPoint(input))
    return distances.mapIndexed { col, column ->
        var isInLoop = false
        var curvedPipe: Char? = null
        column.mapIndexed { row, i ->
            if (i != Int.MIN_VALUE) {
                when {
                    fixedInput[col][row] == '|' -> isInLoop = !isInLoop
                    curvedPipe == null && fixedInput[col][row] != '-' -> curvedPipe = fixedInput[col][row]
                    (curvedPipe == 'L' && fixedInput[col][row] == '7')
                            || (curvedPipe == 'F' && fixedInput[col][row] == 'J') -> {
                        isInLoop = !isInLoop
                        curvedPipe = null
                    }
                    curvedPipe != null && fixedInput[col][row] != '-' -> curvedPipe = null
                }
                0
            } else if (isInLoop) 1 else 0
        }.sum()
    }.sum().toLong()
}

private fun List<String>.replaceStartingSymbol(startPoint: Pair<Int, Int>): List<String> {
    val connectedTop = startPoint.first - 1 in indices
            && (this[startPoint.first - 1][startPoint.second] == '|'
            || this[startPoint.first - 1][startPoint.second] == 'F'
            || this[startPoint.first - 1][startPoint.second] == '7')
    val connectedBottom = startPoint.first + 1 in indices
            && (this[startPoint.first + 1][startPoint.second] == '|'
            || this[startPoint.first + 1][startPoint.second] == 'J'
            || this[startPoint.first + 1][startPoint.second] == 'L')
    val connectedLeft = startPoint.second - 1 in this[0].indices
            && (this[startPoint.first][startPoint.second - 1] == '-'
            || this[startPoint.first][startPoint.second - 1] == 'F'
            || this[startPoint.first][startPoint.second - 1] == 'L')
    val connectedRight = startPoint.second + 1 in this[0].indices
            && (this[startPoint.first][startPoint.second + 1] == '-'
            || this[startPoint.first][startPoint.second + 1] == 'J'
            || this[startPoint.first][startPoint.second + 1] == '7')
    val actualSymbol = when {
        connectedLeft && connectedTop -> 'J'
        connectedLeft && connectedRight -> '-'
        connectedLeft && connectedBottom -> '7'
        connectedTop && connectedRight -> 'L'
        connectedTop && connectedBottom -> '|'
        connectedRight && connectedBottom -> 'F'
        else -> error("Wrong input")
    }
    return mapIndexed { col, line -> if (col == startPoint.first) line.replace('S', actualSymbol) else line }
}

private fun solvePart1(input: List<String>, distances: Array<IntArray>) {
    val startPoint = findStartingPoint(input)
    distances[startPoint.first][startPoint.second] = 0
    fillDistances(startPoint, input, distances)
}

private fun findStartingPoint(input: List<String>) =
    input.mapIndexed { col, line -> col to line.indexOf('S') }.first { it.second >= 0 }

private fun fillDistances(
    startPoint: Pair<Int, Int>,
    input: List<String>,
    distances: Array<IntArray>,
    predicate: (Pair<Int, Int>, Pair<Int, Int>, Int) -> Boolean = { prevCoord, nextCoord, i ->
        distances[nextCoord.first][nextCoord.second] == Int.MIN_VALUE
                && CONNECTED[i].contains(input[nextCoord.first][nextCoord.second])
                && (input[prevCoord.first][prevCoord.second] == 'S'
                || CONNECTED[(i + 2) % 4].contains(input[prevCoord.first][prevCoord.second]))
    }
) {
    var points = listOf(startPoint)
    while (points.isNotEmpty()) {
        val nextPoints = mutableListOf<Pair<Int, Int>>()
        points.forEach { point ->
            (0..3).forEach { i ->
                val coord = (point.first + DY[i]) to (point.second + DX[i])
                if (coord.first in distances.indices
                    && coord.second in distances[0].indices
                    && predicate(point, coord, i)
                ) {
                    distances[coord.first][coord.second] = distances[point.first][point.second] + 1
                    nextPoints.add(coord)
                }
            }
        }
        points = nextPoints
    }
}

private fun List<String>.printLabyrinth(distances: Array<IntArray>? = null) = forEachIndexed { col, line ->
    line
        .replace('F', '╔')
        .replace('J', '╝')
        .replace('L', '╚')
        .replace('7', '╗')
        .replace('-', '═')
        .replace('|', '║')
        .mapIndexed { row, char -> if (distances != null && distances[col][row] != Int.MIN_VALUE) '#' else char }
        .also { println(String(it.toCharArray())) }
}

private val DX = intArrayOf(-1, 0, 1, 0)
private val DY = intArrayOf(0, 1, 0, -1)
private val CONNECTED = listOf(
    setOf('F', 'L', '-'),
    setOf('J', 'L', '|'),
    setOf('J', '7', '-'),
    setOf('F', '7', '|'),
)