package problem.day02

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

fun solveDay02Part1(input: List<String>): Int {
    return input.parse()
        .filter { game -> game.sets.all { it.red <= MAX_CUBES.red && it.green <= MAX_CUBES.green && it.blue <= MAX_CUBES.blue } }
        .sumOf { game -> game.index }
}

fun solveDay02Part2(input: List<String>): Int {
    return input.parse()
        .map { game ->
            Cubes(
                red = game.sets.maxOf { it.red },
                green = game.sets.maxOf { it.green },
                blue = game.sets.maxOf { it.blue },
            )
        }
        .sumOf { it.red * it.green * it.blue }
}

private fun List<String>.parse() = mapIndexed { index, line ->
    val sets = line.replaceBefore(':', "").drop(2).split("; ")
        .map { cubesString ->
            cubesString.split(", ").fold(Cubes()) { acc, entry ->
                when {
                    entry.endsWith("red") -> acc.copy(red = entry.dropLast(4).toInt())
                    entry.endsWith("green") -> acc.copy(green = entry.dropLast(6).toInt())
                    entry.endsWith("blue") -> acc.copy(blue = entry.dropLast(5).toInt())
                    else -> error("Input is incorrect")
                }
            }
        }
        .toList()
    Game(index = index + 1, sets = sets)
}

private data class Game(
    val index: Int,
    val sets: List<Cubes>,
)

private data class Cubes(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
)

private val MAX_CUBES = Cubes(red = 12, green = 13, blue = 14)