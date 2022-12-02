package problem.day14

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 14
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay14Part1, ::solveDay14Part2)
}

data class CharPair(
    val first: Char,
    val second: Char,
)

data class PairInsertion(
    val pair: CharPair,
    val charToAdd: Char,
) {
    val pairsToAdd = CharPair(pair.first, charToAdd) to CharPair(charToAdd, pair.second)
}

fun solveDay14Part1(input: List<String>): Long {
    val initialState = input.first()
    val pairInsertions = parsePairInsertions(input)
    val charCounter = countTemplateChars(initialState)
    val pairCounter = countTemplatePairs(initialState)

    repeat(10) {
        iterateInsertions(pairInsertions, pairCounter, charCounter)
    }
    return charCounter.maxOf { it.value } - charCounter.minOf { it.value }
}

fun solveDay14Part2(input: List<String>): Long {
    val initialState = input.first()
    val pairInsertions = parsePairInsertions(input)
    val charCounter = countTemplateChars(initialState)
    val pairCounter = countTemplatePairs(initialState)

    repeat(40) {
        iterateInsertions(pairInsertions, pairCounter, charCounter)
    }
    return charCounter.maxOf { it.value } - charCounter.minOf { it.value }
}

private fun iterateInsertions(
    pairInsertions: List<PairInsertion>,
    pairCounter: MutableMap<CharPair, Long>,
    charCounter: MutableMap<Char, Long>
) {
    val charCounterChanges = mutableMapOf<Char, Long>()
    val pairCounterChanges = mutableMapOf<CharPair, Long>()
    pairInsertions.forEach { insertion ->
        val pairCount = pairCounter[insertion.pair] ?: 0
        if (pairCount > 0) {
            charCounterChanges[insertion.charToAdd] = (charCounterChanges[insertion.charToAdd] ?: 0) + pairCount
            pairCounterChanges[insertion.pair] =
                (pairCounterChanges[insertion.pair] ?: 0) - pairCount
            pairCounterChanges[insertion.pairsToAdd.first] =
                (pairCounterChanges[insertion.pairsToAdd.first] ?: 0) + pairCount
            pairCounterChanges[insertion.pairsToAdd.second] =
                (pairCounterChanges[insertion.pairsToAdd.second] ?: 0) + pairCount
        }
    }
    charCounterChanges.forEach { (char, counterDelta) ->
        charCounter[char] = (charCounter[char] ?: 0) + counterDelta
    }
    pairCounterChanges.forEach { (pair, counterDelta) ->
        pairCounter[pair] = (pairCounter[pair] ?: 0) + counterDelta
    }
}

private fun countTemplatePairs(initialState: String) = initialState
    .zipWithNext()
    .map { CharPair(it.first, it.second) }
    .groupBy { it }
    .mapValues { it.value.size.toLong() }
    .toMutableMap()

private fun countTemplateChars(initialState: String) = initialState
    .groupBy { it }
    .mapValues { it.value.size.toLong() }
    .toMutableMap()

private fun parsePairInsertions(input: List<String>) = input.drop(2).map { line ->
    line.split(" -> ").let {
        PairInsertion(
            pair = it[0].let { pairString -> CharPair(pairString[0], pairString[1]) },
            charToAdd = it[1].first(),
        )
    }
}
