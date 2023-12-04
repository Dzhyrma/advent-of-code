package problem.day03

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 3
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay03Part1, ::solveDay03Part2)
}

fun solveDay03Part1(input: List<String>): Int {
    return input
        .parseNumbers()
        .filter { it.isAdjacentToSymbols(input) }
        .sumOf { it.toInt(input) }
}

fun solveDay03Part2(input: List<String>): Int {
    return input
        .parseNumbers()
        .let { numbers ->
            input
                .findAllGears(numbers)
                .sumOf {
                    it.number1.toInt(input) * it.number2.toInt(input)
                }
        }
}

private fun List<String>.parseNumbers() = flatMapIndexed { y, line ->
    line.foldIndexed(mutableListOf<NumberXY>()) { x, numbers, char ->
        if (char.isDigit()) {
            when {
                x == 0 || !line[x - 1].isDigit() -> numbers.add(NumberXY(x, y, 1))
                else -> numbers.add(numbers.removeLast().let { it.copy(length = it.length + 1) })
            }
        }
        numbers
    }
}

private fun List<String>.findAllGears(numbers: List<NumberXY>) = flatMapIndexed { y, line ->
    line.foldIndexed(listOf<Gear>()) { x, gears, char ->
        val adjacentNumbers = numbers.getAdjacentNumbers(x, y)
        if (char == '*' && adjacentNumbers.size == 2)
            gears + Gear(adjacentNumbers.first(), adjacentNumbers.last())
        else
            gears
    }
}

private fun List<NumberXY>.getAdjacentNumbers(x: Int, y: Int) = filter {
    x in (it.x - 1..it.x + it.length) &&
            y in (it.y - 1..it.y + 1)
}

private fun List<String>.isSymbol(x: Int, y: Int) =
    y in indices && x in get(y).indices && !get(y)[x].isDigit() && get(y)[x] != '.'

private data class NumberXY(
    val x: Int,
    val y: Int,
    val length: Int,
) {

    fun isAdjacentToSymbols(input: List<String>) = (-1..length).any { dx ->
        input.isSymbol(x + dx, y - 1) || input.isSymbol(x + dx, y + 1)
    } || input.isSymbol(x - 1, y) || input.isSymbol(x + length, y)

    fun toInt(input: List<String>) = input[y].substring(x until x + length).toInt()
}

private data class Gear(
    val number1: NumberXY,
    val number2: NumberXY,
)