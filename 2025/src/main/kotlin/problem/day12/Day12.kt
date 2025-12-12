package problem.day12

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 12
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay12Part1, ::solveDay12Part2)
}

fun solveDay12Part1(input: List<String>): Int {
    val areas =
        input.drop(30).map { line ->
            line.split(": ").let { it ->
                val dimensions = it[0].split("x").map { it.toInt() }
                Area(
                    width = dimensions[0],
                    height = dimensions[1],
                    presents = it[1].split(" ").map(String::toInt),
                )
            }
        }
    return areas.count { area ->
        area.width * area.height >=
            area.presents.mapIndexed { index, presentsCount -> 9 * presentsCount }.sum()
    }
}

fun solveDay12Part2(
    @Suppress("unused") input: List<String>,
): Int = 0

private data class Area(
    val width: Int,
    val height: Int,
    val presents: List<Int>,
)
