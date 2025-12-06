package problem.day06

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.text.toLong

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}

fun solveDay06Part1(input: List<String>): Long {
    val numbers =
        input
            .take(input.size - 1)
            .map { line -> line.split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toLong() } }
    val operations = input.last().split("\\s+".toRegex()).map { it[0] }
    return numbers[0].foldIndexed(0) { index, acc, _ ->
        numbers.drop(1).fold(numbers[0][index]) { acc, nums ->
            when (operations[index]) {
                '*' -> acc * nums[index]
                '+' -> acc + nums[index]
                else -> error("Unknown operation: ${operations[index]}")
            }
        } + acc
    }
}

fun solveDay06Part2(input: List<String>): Long {
    val newInput =
        (0..input.maxOf { it.length } - 1)
            .map { index -> input.map { it.getOrElse(index) { ' ' } }.toCharArray().let { String(it).trim() } }
    var lastOperation = ' '
    return newInput
        .fold(0L to 0L) { (total, group), line ->
            when {
                line.isBlank() -> (total + group) to 0L
                !line.last().isDigit() -> {
                    lastOperation = line.last()
                    total to line.dropLast(1).trim().toLong()
                }
                else -> {
                    val number = line.toLong()
                    when (lastOperation) {
                        '*' -> total to group * number
                        '+' -> total to group + number
                        else -> error("Unknown operation: $lastOperation")
                    }
                }
            }
        }.let { (total, group) -> total + group }
}
