package problem.day07

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2)
}

fun solveDay07Part1(input: List<String>): Int {
    val beams = input.map { mutableSetOf<Int>() }
    beams[0].add(input[0].indexOf('S'))
    var count = 0
    (1..input.lastIndex).forEach { y ->
        beams[y - 1].forEach { x ->
            val char = input[y][x]
            if (char == '^') {
                beams[y].add(x - 1)
                beams[y].add(x + 1)
                count++
            } else {
                beams[y].add(x)
            }
        }
    }
    return count
}

fun solveDay07Part2(input: List<String>): Long {
    val beams = input.map { mutableSetOf<Int>() }
    val timelines = input.map { LongArray(input[0].length) { 0L } }
    val initBeamIndex = input[0].indexOf('S')
    beams[0].add(initBeamIndex)
    timelines[0][initBeamIndex] = 1
    (1..input.lastIndex).forEach { y ->
        beams[y - 1].forEach { x ->
            val char = input[y][x]
            if (char == '^') {
                beams[y].add(x - 1)
                timelines[y][x - 1] += timelines[y - 1][x]
                beams[y].add(x + 1)
                timelines[y][x + 1] += timelines[y - 1][x]
            } else {
                beams[y].add(x)
                timelines[y][x] += timelines[y - 1][x]
            }
        }
    }
    return timelines.last().sum()
}
