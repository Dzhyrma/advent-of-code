package problem.day11

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 11
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay11Part1, ::solveDay11Part2)
}

class Monkey(
    private val startingItems: MutableList<Long>,
    private val operation: (Long) -> Long,
    val testDivisor: Long,
    private val monkeyIfTrue: () -> Monkey,
    private val monkeyIfFalse: () -> Monkey,
) {
    var inspectedItemsCount = 0L

    fun simulateRound(worryLevelReducer: (Long) -> Long) {
        startingItems.forEach { item ->
            val newItem = operation(item)
            val newWorryLevel = worryLevelReducer(newItem)
            if (newWorryLevel % testDivisor == 0L) {
                monkeyIfTrue().startingItems.add(newWorryLevel)
            } else {
                monkeyIfFalse().startingItems.add(newWorryLevel)
            }
            inspectedItemsCount++
        }
        startingItems.clear()
    }
}

fun solveDay11Part1(input: List<String>): Long {
    val monkeys = input.parse()
    repeat(20) { monkeys.forEach { monkey -> monkey.simulateRound { it / 3 } } }
    return monkeys.map { it.inspectedItemsCount }.sorted().takeLast(2).let { it[0] * it[1] }
}

fun solveDay11Part2(input: List<String>): Long {
    val monkeys = input.parse()
    val commonMultiple = monkeys.map { it.testDivisor }.reduce { acc, divisor -> acc * divisor }
    repeat(10_000) { monkeys.forEach { monkey -> monkey.simulateRound { it % commonMultiple } } }
    return monkeys.map { it.inspectedItemsCount }.sorted().takeLast(2).let { it[0] * it[1] }
}

private fun List<String>.parse(): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()

    fun String.relevantPart() = split(":")[1].trim()

    chunked(7).forEach { input ->
        val items = input[1].relevantPart().split(", ").map { it.toLong() }.toMutableList()
        val op = input[2].relevantPart().split(" ").let {
            val firstOperand = it[2].asOperand()
            val secondOperand = it[4].asOperand()
            when (it[3]) {
                "+" -> { old: Long -> firstOperand(old) + secondOperand(old) }
                "-" -> { old: Long -> firstOperand(old) - secondOperand(old) }
                "*" -> { old: Long -> firstOperand(old) * secondOperand(old) }
                else -> throw IllegalArgumentException("Invalid operation")
            }
        }
        val testDivisor = input[3].relevantPart().split(" ")[2].toLong()
        val ifTrue = input[4].relevantPart().split(" ")[3].toInt().let { { monkeys[it] } }
        val ifFalse = input[5].relevantPart().split(" ")[3].toInt().let { { monkeys[it] } }
        monkeys.add(Monkey(items, op, testDivisor, ifTrue, ifFalse))
    }
    return monkeys
}

private fun String.asOperand() = { old: Long -> if (this == "old") old else toLong() }
