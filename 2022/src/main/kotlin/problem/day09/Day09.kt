package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay09Part1, ::solveDay09Part2)
}


fun solveDay09Part1(input: List<String>): Int {
    val directions = parseDirections(input)
    return calculateNumberOfVisistedPointsByTail(directions)
}

fun solveDay09Part2(input: List<String>): Int {
    val directions = parseDirections(input)
    return calculateNumberOfVisistedPointsByTail(directions, 10)
}

private fun parseDirections(input: List<String>) = input
    .map { it.split(" ") }
    .flatMap { (direction, moves) -> (1..moves.toInt()).map { direction } }

private fun calculateNumberOfVisistedPointsByTail(directions: List<String>, ropeSize: Int = 2): Int {
    val rope = Array(ropeSize) { Pair(0, 0) }
    return directions
        .map { direction ->
            when (direction) {
                "U" -> rope.move(0, 0, 1)
                "D" -> rope.move(0, 0, -1)
                "L" -> rope.move(0, -1, 0)
                "R" -> rope.move(0, 1, 0)
            }
            rope.last()
        }
        .toSet().size
}

private fun Array<Pair<Int, Int>>.move(index: Int, dx: Int, dy: Int) {
    this[index] = Pair(this[index].first + dx, this[index].second + dy)
    if (index == size - 1) return
    val distanceX = this[index].first - this[index + 1].first
    val distanceY = this[index].second - this[index + 1].second
    when {
        distanceX.absoluteValue == 2 && distanceY.absoluteValue == 2 -> move(index + 1, distanceX / 2, distanceY / 2)
        distanceX.absoluteValue == 2 -> move(index + 1, distanceX / 2, distanceY)
        distanceY.absoluteValue == 2 -> move(index + 1, distanceX, distanceY / 2)
    }
}
