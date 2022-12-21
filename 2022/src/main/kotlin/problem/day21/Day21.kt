package problem.day21

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 21
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay21Part1, ::solveDay21Part2)
}

private data class Operation(
    val firstMonkey: String,
    val secondMonkey: String,
    val operation: Char,
    var marked: Boolean = false,
) {

    fun calculate(registry: Map<String, Any>, mark: String? = null): Long {
        val first = firstMonkey.shoutNumber(registry, mark)
        val second = secondMonkey.shoutNumber(registry, mark)
        marked = firstMonkey == mark
                || secondMonkey == mark
                || (registry[firstMonkey] as? Operation)?.marked ?: false
                || (registry[secondMonkey] as? Operation)?.marked ?: false

        return when (operation) {
            '+' -> first + second
            '-' -> first - second
            '*' -> first * second
            '/' -> first / second
            else -> throw IllegalArgumentException("Wrong input")
        }
    }

    fun findMistake(registry: Map<String, Any>, expectedResult: Long, mistakenMonkey: String): Long {
        val first = firstMonkey.shoutNumber(registry, mistakenMonkey)
        val second = secondMonkey.shoutNumber(registry, mistakenMonkey)
        return when {
            firstMonkey == mistakenMonkey -> firstMonkeyIsMistaken(expectedResult, second)
            secondMonkey == mistakenMonkey -> secondMonkeyIsMistaken(expectedResult, first)
            else -> {
                val firstOperation = registry[firstMonkey] as? Operation
                val secondOperation = registry[secondMonkey] as? Operation
                when {
                    firstOperation != null && firstOperation.marked -> firstOperation.findMistake(
                        registry = registry,
                        expectedResult = firstMonkeyIsMistaken(expectedResult, second),
                        mistakenMonkey = mistakenMonkey,
                    )
                    secondOperation != null && secondOperation.marked -> secondOperation.findMistake(
                        registry = registry,
                        expectedResult = secondMonkeyIsMistaken(expectedResult, first),
                        mistakenMonkey = mistakenMonkey,
                    )
                    else -> throw IllegalArgumentException("Wrong input")
                }
            }
        }
    }

    private fun firstMonkeyIsMistaken(expectedResult: Long, second: Long) = when (operation) {
        '+' -> expectedResult - second
        '-' -> expectedResult + second
        '*' -> expectedResult / second
        '/' -> expectedResult * second
        else -> throw IllegalArgumentException("Wrong input")
    }

    private fun secondMonkeyIsMistaken(expectedResult: Long, first: Long) = when (operation) {
        '+' -> expectedResult - first
        '-' -> first - expectedResult
        '*' -> expectedResult / first
        '/' -> first / expectedResult
        else -> throw IllegalArgumentException("Wrong input")
    }
}

fun solveDay21Part1(input: List<String>): Long {
    val registry = parseRegistry(input)
    return "root".shoutNumber(registry)
}

fun solveDay21Part2(input: List<String>): Long {
    val registry = parseRegistry(input)
    val (firstMonkey, secondMonkey) = (registry["root"] as Operation).let { it.firstMonkey to it.secondMonkey }
    val first = firstMonkey.shoutNumber(registry, "humn")
    val second = secondMonkey.shoutNumber(registry, "humn")
    val (firstOperation, secondOperation) = registry[firstMonkey] as Operation to registry[secondMonkey] as Operation
    return when {
        firstOperation.marked -> firstOperation.findMistake(registry, second, "humn")
        secondOperation.marked -> secondOperation.findMistake(registry, first, "humn")
        else -> throw IllegalArgumentException("Wrong input")
    }
}

private fun parseRegistry(input: List<String>) = input.map { it.split(": ") }.map {
    it[0] to (it[1].toLongOrNull() ?: REGEX.matchEntire(it[1])?.groupValues?.let { values ->
        Operation(values[1], values[3], values[2].first())
    } ?: throw IllegalArgumentException("Wrong input"))
}.toMap()

private fun String.shoutNumber(registry: Map<String, Any>, mark: String? = null) = when (val outcome = registry[this]) {
    is Long -> outcome
    is Operation -> outcome.calculate(registry, mark)
    else -> throw IllegalArgumentException("Wrong input")
}

private val REGEX = Regex("(.+) ([+\\-*/]) (.+)")
