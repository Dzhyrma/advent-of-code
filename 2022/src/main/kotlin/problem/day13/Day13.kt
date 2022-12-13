package problem.day13

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 13
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay13Part1, ::solveDay13Part2)
}


fun solveDay13Part1(input: List<String>) = input
    .chunked(3)
    .mapIndexed { index, strings ->
        val left = strings[0].parse()
        val right = strings[1].parse()
        if (isLeftBeforeRight(left, right) == true) index + 1 else 0
    }
    .sum()

fun solveDay13Part2(input: List<String>) = input
    .chunked(3)
    .flatMap { listOf(it[0].parse(), it[1].parse()) }
    .let { it + listOf(EXTRA_PACKET_1, EXTRA_PACKET_2) }
    .sortedWith { a, b ->
        when (isLeftBeforeRight(a, b)) {
            true -> -1
            false -> 1
            else -> 0
        }
    }
    .let { (it.indexOf(EXTRA_PACKET_1) + 1) * (it.indexOf(EXTRA_PACKET_2) + 1) }

private fun String.parse() = mutableListOf<Any>().also { parseList(0, it) }

private fun String.parseList(currentIndex: Int, mutableList: MutableList<Any>): Int {
    var nextIndex = currentIndex + 1
    val number = StringBuilder()
    while (this[nextIndex] != ']') {
        when (val char = this[nextIndex]) {
            ',' -> {
                if (number.isNotEmpty()) {
                    mutableList.add(number.toString().toInt())
                    number.clear()
                }
            }
            '[' -> {
                val nextList = mutableListOf<Any>()
                nextIndex = parseList(nextIndex, nextList)
                mutableList.add(nextList)
            }
            in '0'..'9' -> number.append(char)
            else -> throw IllegalArgumentException("Wrong input")
        }
        nextIndex++
    }
    if (number.isNotEmpty()) mutableList.add(number.toString().toInt())
    return nextIndex
}

private fun isLeftBeforeRight(left: Any?, right: Any?): Boolean? = when {
    left is Int && right is Int -> if (left == right) null else left < right
    left is Int && right is List<*> -> isLeftBeforeRight(listOf(left), right)
    left is List<*> && right is Int -> isLeftBeforeRight(left, listOf(right))
    left is List<*> && right is List<*> -> {
        val result = left.zip(right)
            .asSequence()
            .map { (first, second) -> isLeftBeforeRight(first, second) }
            .firstOrNull { it != null }
        result ?: if (left.size != right.size) left.size < right.size else null
    }
    else -> null
}

private val EXTRA_PACKET_1 = listOf(listOf(2))
private val EXTRA_PACKET_2 = listOf(listOf(6))
