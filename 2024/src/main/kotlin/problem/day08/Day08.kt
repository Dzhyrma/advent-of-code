package problem.day08

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}

fun solveDay08Part1(input: List<String>): Int {
    val antennas = input.parseAntennas()
    return antennas.fintAntinodes(input.size, input[0].length).size
}

fun solveDay08Part2(input: List<String>): Int {
    val antennas = input.parseAntennas()
    return antennas.fintAntinodes(input.size, input[0].length, resonate = true).size
}

private fun List<String>.parseAntennas() = mapIndexed { y, row ->
    row.mapIndexedNotNull { x, char ->
        if (char.isLetterOrDigit()) Antenna(x, y, char) else null
    }
}.flatten()

private fun List<Antenna>.fintAntinodes(gridHeight: Int, gridWidth: Int, resonate: Boolean = false): Set<Pair<Int, Int>> {
    val antinodes = mutableSetOf<Pair<Int, Int>>()
    for (i in indices) {
        for (j in i + 1 until size) {
            val a1 = this[i]
            val a2 = this[j]
            if (a1.freq != a2.freq) continue

            val dx = a2.x - a1.x
            val dy = a2.y - a1.y

            fun addAntinodes(startX: Int, startY: Int, xStep: Int, yStep: Int) {
                var ax = startX
                var ay = startY
                do {
                    if (ax in 0 until gridWidth && ay in 0 until gridHeight) antinodes.add(ax to ay) else break
                    ax -= xStep
                    ay -= yStep
                } while (resonate)
            }
            addAntinodes(a1.x - dx, a1.y - dy, dx, dy)
            addAntinodes(a2.x + dx, a2.y + dy, -dx, -dy)
        }
    }
    if (resonate) antinodes.addAll(this.map { it.x to it.y })
    return antinodes
}

private data class Antenna(val x: Int, val y: Int, val freq: Char)
