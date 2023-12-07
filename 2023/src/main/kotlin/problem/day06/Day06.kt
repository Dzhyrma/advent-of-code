package problem.day06

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}

fun solveDay06Part1(input: List<String>): Long {
    val data = input.parseInput1()
    return data.map { (time, distance) ->
        solveQuadraticEq(time, distance)
    }.reduce { acc, l -> acc * l }
}

fun solveDay06Part2(input: List<String>): Long {
    val (time, distance) = input.parseInput2()
    return solveQuadraticEq(time, distance)
}

private fun solveQuadraticEq(time: Long, distance: Long): Long {
    val root = sqrt(time * time - 4.0 * distance)
    val x1 = (time - root) / 2
    val x2 = (time + root) / 2
    return (ceil(x2 - 1).toLong() - floor(x1 + 1).toLong() + 1)
}

private fun List<String>.parseInput1(): List<Pair<Long, Long>> =
    map { line -> line.drop(9).trim().split(' ').filter { it.isNotEmpty() }.map { it.toLong() } }
        .let { it.first().zip(it.last()) }

private fun List<String>.parseInput2(): Pair<Long, Long> =
    map { line -> line.drop(9).filter { it.isDigit() }.toLong() }
        .let { it.first() to it.last() }