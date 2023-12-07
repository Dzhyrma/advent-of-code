package problem.day07

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2)
}

fun solveDay07Part1(input: List<String>): Long {
    return input.parseInput().solve()
}

fun solveDay07Part2(input: List<String>): Long {
    return input.parseInput(useJoker = true).solve()
}

private fun List<CamelHand>.solve() =
    sortedWith(CamelCardComparator)
        .mapIndexed { index, camelHand ->
            (index + 1) * camelHand.bid
        }
        .sum()

private fun evaluate(hand: List<Int>, useJoker: Boolean = false): Int {
    val counts = hand
        .groupBy { it }
        .map { it.key to it.value.size }
        .sortedBy { it.second }
        .reversed()
        .let { counts ->
            if (useJoker) {
                replaceJokers(counts)
            } else counts
        }
    return when {
        counts[0].second == 5 -> 6
        counts[0].second == 4 -> 5
        counts[0].second == 3 && counts[1].second == 2 -> 4
        counts[0].second == 3 -> 3
        counts[0].second == 2 && counts[1].second == 2 -> 2
        counts[0].second == 2 -> 1
        else -> 0
    }
}

private fun replaceJokers(counts: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val jokerCount = counts.find { it.first == -1 }?.second
    return if (jokerCount != null && jokerCount < 5) {
        counts.toMap()
            .toMutableMap()
            .also {
                val firstNotJoker = counts.first { (card, _) -> card != -1 }.first
                it[firstNotJoker] = it.getValue(firstNotJoker) + jokerCount
                it.remove(-1)
            }
            .entries
            .map { it.key to it.value }
            .sortedBy { it.second }
            .reversed()
    } else counts
}

private fun Char.simplify(useJoker: Boolean = false) = when (this) {
    in '2'..'9' -> this - '2'
    'T' -> 10
    'J' -> if (useJoker) -1 else 11
    'Q' -> 12
    'K' -> 13
    'A' -> 14
    else -> error("Input was wrong")
}

private fun List<String>.parseInput(useJoker: Boolean = false) = map { line ->
    line.split(' ').let { it ->
        val hand = it.first().map { char -> char.simplify(useJoker) }
        CamelHand(
            hand = hand,
            type = evaluate(hand, useJoker),
            bid = it.last().toLong(),
        )
    }
}

private object CamelCardComparator : Comparator<CamelHand> {
    override fun compare(o1: CamelHand, o2: CamelHand): Int {
        return when {
            o1.type == o2.type -> o1.hand.compareTo(o2.hand)
            else -> o1.type - o2.type
        }
    }
}

private fun List<Int>.compareTo(other: List<Int>): Int {
    return zip(other).find { it.first != it.second }?.let { it.first - it.second } ?: 0
}

private data class CamelHand(
    val hand: List<Int>,
    val type: Int,
    val bid: Long,
)