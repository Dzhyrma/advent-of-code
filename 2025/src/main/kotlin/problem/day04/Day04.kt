package problem.day04

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.collections.indices

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}

fun solveDay04Part1(input: List<String>): Int = input.removePaperRolls()

fun solveDay04Part2(input: List<String>): Int = input.removePaperRolls(repeatTimes = input.size)

private fun List<String>.removePaperRolls(repeatTimes: Int = 1): Int {
    var rolls = map { it.toCharArray() }
    var result = 0
    repeat(repeatTimes) {
        val newRolls = rolls.map { it.copyOf() }
        rolls.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '@') {
                    val count =
                        DX.indices.count { dir ->
                            val nx = x + DX[dir]
                            val ny = y + DY[dir]
                            ny in rolls.indices && nx in rolls[ny].indices && rolls[ny][nx] == '@'
                        }
                    if (count < 4) {
                        result++
                        newRolls[y][x] = '.'
                    }
                }
            }
        }
        rolls = newRolls
    }
    return result
}

private val DX = listOf(-1, 0, 1, -1, 1, -1, 0, 1)
private val DY = listOf(-1, -1, -1, 0, 0, 1, 1, 1)
