package problem.day08

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}

fun solveDay08Part1(input: List<String>): Long {
    val turns = input[0]
    val pathNodes = input.parsePathNodes()
    return pathNodes.find("AAA", turns) { node -> node == "ZZZ" }
}

fun solveDay08Part2(input: List<String>): Long {
    val turns = input[0]
    val pathNodes = input.parsePathNodes()
    val startNodes = pathNodes.keys.filter { it.endsWith('A') }
    return startNodes.map { startNode ->
        pathNodes.find(startNode, turns) { node -> node.endsWith('Z') }
    }.reduce { acc, c -> lcm(acc, c) }
}

private fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b
}

private fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}

private fun Map<String, PathNode>.find(currentNode: String, turns: String, stopPredicate: (String) -> Boolean): Long {
    var current = currentNode
    var step = 0L
    while (!stopPredicate(current)) {
        current = if (turns[step.mod(turns.length)] == 'L')
            getValue(current).left
        else
            getValue(current).right
        step++
    }
    return step
}

private fun List<String>.parsePathNodes() = drop(2)
    .associate { line ->
        line.dropLast(1).split(" = (").let {
            it.first() to it.last().toPathNode()
        }
    }

private fun String.toPathNode() = split(", ").let {
    PathNode(
        left = it.first(),
        right = it.last(),
    )
}

private data class PathNode(
    val left: String,
    val right: String,
)