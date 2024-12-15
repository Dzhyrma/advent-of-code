package problem.day14

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 14
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay14Part1, ::solveDay14Part2)
}

fun solveDay14Part1(input: List<String>, width: Int = 101, height: Int = 103): Int {
    return input.parseRobots().calculateSafetyFactor(100, width, height)
}

fun solveDay14Part2(input: List<String>): Int {
    var min = Int.MAX_VALUE
    var minI = -1L
    repeat(10000) { i ->
        val res = input.parseRobots().calculateSafetyFactor(i.toLong())
        if (res < min) {
            min = res
            minI = i.toLong()
        }
    }
    println(minI)
    input.parseRobots().calculateSafetyFactor(minI, log = true)
    return min
}

private fun List<String>.parseRobots() = map { line ->
    val (px, py, vx, vy) = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
        .find(line)!!
        .destructured
        .toList()
        .map { it.toInt() }
    Robot(px, py, vx.toLong(), vy.toLong())
}

private fun List<Robot>.calculateSafetyFactor(
    seconds: Long = 100,
    width: Int = 101,
    height: Int = 103,
    log: Boolean = false,
): Int {
    val toPrint = Array(height) { CharArray(width) { '.' } }

    // Define quadrants
    val midX = width / 2
    val midY = height / 2

    var q1 = 0
    var q2 = 0
    var q3 = 0
    var q4 = 0

    forEach { robot ->
        robot.x = (((robot.x + robot.vx * seconds) % width + width) % width).toInt()
        robot.y = (((robot.y + robot.vy * seconds) % height + height) % height).toInt()
        toPrint[robot.y][robot.x] = '#'
        if (robot.x != midX && robot.y != midY) {
            when {
                robot.x < midX && robot.y < midY -> q1++
                robot.x >= midX && robot.y < midY -> q2++
                robot.x < midX && robot.y >= midY -> q3++
                else -> q4++
            }
        }
    }

    if (log) {
        toPrint.forEach { println(String(it)) }
    }

    return q1 * q2 * q3 * q4
}

private data class Robot(var x: Int, var y: Int, val vx: Long, val vy: Long)
