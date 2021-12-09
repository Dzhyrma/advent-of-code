package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay09Part1, ::solveDay09Part2)
}


fun solveDay09Part1(input: List<String>): Int {
    return input.findAndConvertLowestPoints { x, y -> input[y][x] - '0' + 1 }.sum()
}

fun solveDay09Part2(input: List<String>): Int {
    return input.findAndConvertLowestPoints { x, y -> getBasinSize(input, x, y) }.sorted().takeLast(3).let {
        it[0] * it[1] * it[2]
    }
}

private fun List<String>.findAndConvertLowestPoints(convert: (Int, Int) -> Int) = mapIndexed { y, line ->
    line.mapIndexed { x, digitChar ->
        if ((y == 0 || digitChar < this[y - 1][x])
            && (y == this.size - 1 || digitChar < this[y + 1][x])
            && (x == 0 || digitChar < this[y][x - 1])
            && (x == line.length - 1 || digitChar < this[y][x + 1])
        ) convert(x, y) else 0
    }
}.reduce { acc, list -> acc + list }

private fun getBasinSize(
    input: List<String>,
    x: Int,
    y: Int,
    visited: MutableSet<Pair<Int, Int>> = mutableSetOf(),
): Int {
    if (visited.contains(x to y)) return 0
    visited.add(x to y)
    val h = input[y][x]
    if (h == '9') return 0
    return (if (y > 0 && input[y - 1][x] - h > 0) getBasinSize(input, x, y - 1, visited) else 0) +
            (if (y < input.size - 1 && input[y + 1][x] - h > 0) getBasinSize(input, x, y + 1, visited) else 0) +
            (if (x > 0 && input[y][x - 1] - h > 0) getBasinSize(input, x - 1, y, visited) else 0) +
            (if (x < input[y].length - 1 && input[y][x + 1] - h > 0) getBasinSize(input, x + 1, y, visited) else 0) + 1
}