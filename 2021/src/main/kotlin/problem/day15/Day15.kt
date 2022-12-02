package problem.day15

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*

fun main(args: Array<String>) {
    val day = 15
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay15Part1, ::solveDay15Part2)
}

private val DELTA_COORDS = arrayOf(
    -1 to 0,
    0 to -1,
    1 to 0,
    0 to 1,
)

fun solveDay15Part1(input: List<String>): Int {
    val caveLength = input.size
    val caveWidth = input[0].length
    val lowestPathRisks = Array(caveLength) { Array(caveWidth) { Integer.MAX_VALUE } }
    lowestPathRisks[0][0] = 0

    val lastBestCoordinates = LinkedList<Pair<Int, Int>>()
    lastBestCoordinates.offer(0 to 0)
    while (!lastBestCoordinates.isEmpty()) {
        val (x, y) = lastBestCoordinates.poll()
        DELTA_COORDS.forEach { (dx, dy) ->
            val newX = x + dx
            val newY = y + dy
            if (newX in input[0].indices && newY in input.indices
                && lowestPathRisks[y][x] + (input[newY][newX] - '0') < lowestPathRisks[newY][newX]
            ) {
                lowestPathRisks[newY][newX] = lowestPathRisks[y][x] + (input[newY][newX] - '0')
                lastBestCoordinates.offer(newX to newY)
            }
        }
    }
    return lowestPathRisks[caveLength - 1][caveWidth - 1]
}

fun solveDay15Part2(input: List<String>): Int {
    val width = input.size
    val length = input[0].length
    val caveLength = length * 5
    val caveWidth = width * 5
    val lowestPathRisks = Array(caveLength) { Array(caveWidth) { Integer.MAX_VALUE } }
    lowestPathRisks[0][0] = 0

    val lastBestCoordinates = LinkedList<Pair<Int, Int>>()
    lastBestCoordinates.offer(0 to 0)
    while (!lastBestCoordinates.isEmpty()) {
        val (x, y) = lastBestCoordinates.poll()
        DELTA_COORDS.forEach { (dx, dy) ->
            val newX = x + dx
            val newY = y + dy
            if (newX in (0 until caveWidth) && newY in (0 until caveLength)) {
                val newRisk = lowestPathRisks[y][x] + (input[newY.mod(length)][newX.mod(width)] - '0' - 1 + newY / length + newX / width).mod(9) + 1
                if (newRisk < lowestPathRisks[newY][newX]) {
                    lowestPathRisks[newY][newX] = newRisk
                    lastBestCoordinates.offer(newX to newY)
                }
            }
        }
    }
    return lowestPathRisks[caveLength - 1][caveWidth - 1]
}
