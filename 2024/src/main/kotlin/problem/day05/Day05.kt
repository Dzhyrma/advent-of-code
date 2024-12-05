package problem.day05

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

fun solveDay05Part1(input: List<String>): Int {
    val (rules, updates) = parseInput(input)
    return updates.filter { it.isOrdered(rules) }.sumOf { it[it.size / 2] }
}

fun solveDay05Part2(input: List<String>): Int {
    val (rules, updates) = parseInput(input)
    return updates.filter { !it.isOrdered(rules) }.map { it.reorder(rules) }.sumOf { it[it.size / 2] }
}

private fun parseInput(input: List<String>): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
    val rules = input.takeWhile { it != "" }
        .map { it.split("|").map(String::toInt) }
        .groupBy({ it[0] }) { it[1] }
        .mapValues { (_, pageList) -> pageList.toSet() }
    val updates = input.takeLastWhile { it != "" }.map { it.split(',').map(String::toInt) }
    return rules to updates
}

private fun List<Int>.isOrdered(rules: Map<Int, Set<Int>>): Boolean {
    val positions = withIndex().associate { it.value to it.index }
    return rules.none { (pageBefore, pagesAfter) ->
        positions.contains(pageBefore) && pagesAfter.any { pageAfter ->
            positions.contains(pageAfter) && positions.getValue(pageBefore) > positions.getValue(pageAfter)
        }
    }
}

private fun List<Int>.reorder(rules: Map<Int, Set<Int>>): List<Int> {
    return sortedWith { o1, o2 ->
        when {
            rules.containsKey(o1) && rules.getValue(o1).contains(o2) -> -1
            rules.containsKey(o2) && rules.getValue(o2).contains(o1) -> 1
            else -> o1.compareTo(o2)
        }
    }
}
