package problem.day12

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 12
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay12Part1, ::solveDay12Part2)
}

enum class NodeType {
    START,
    BIG,
    SMALL,
}

data class Node(
    val name: String,
    val type: NodeType,
)

private val START_NODE = Node("start", NodeType.START)
private val END_NODE = Node("end", NodeType.SMALL)

fun solveDay12Part1(input: List<String>): Int {
    val graph = extractGraph(input)

    return graph.countPossiblePaths(START_NODE, END_NODE)
}

fun solveDay12Part2(input: List<String>): Int {
    val graph = extractGraph(input)

    return graph.countPossiblePaths(START_NODE, END_NODE, canVisitSmallNodeTwice = true)
}

private fun extractGraph(input: List<String>) = input.map { line ->
    line.split('-').map { nodeName ->
        when {
            nodeName == "start" -> START_NODE
            nodeName == "end" -> END_NODE
            nodeName.all { it.isUpperCase() } -> Node(nodeName, NodeType.BIG)
            else -> Node(nodeName, NodeType.SMALL)
        }
    }.let { it[0] to it[1] }
}.fold(mutableMapOf<Node, MutableSet<Node>>()) { acc, (node1, node2) ->
    acc.also {
        it.getOrPut(node1) { mutableSetOf() }.add(node2)
        it.getOrPut(node2) { mutableSetOf() }.add(node1)
    }
}

private fun Map<Node, Set<Node>>.countPossiblePaths(
    start: Node,
    end: Node,
    visited: MutableSet<Node> = mutableSetOf(),
    canVisitSmallNodeTwice: Boolean = false,
): Int {
    return when {
        start == end -> 1
        start.type == NodeType.START && visited.contains(start) -> 0
        start.type == NodeType.SMALL && visited.contains(start) -> {
            get(start)
                ?.takeIf { canVisitSmallNodeTwice }
                ?.sumOf { countPossiblePaths(it, end, visited, false) }
                ?: 0
        }
        start.isSmallOrStart() -> {
            visited.add(start)
            get(start)?.sumOf { countPossiblePaths(it, end, visited, canVisitSmallNodeTwice) }?.also {
                visited.remove(start)
            } ?: 0
        }
        else -> get(start)?.sumOf { countPossiblePaths(it, end, visited, canVisitSmallNodeTwice) } ?: 0
    }
}

private fun Node.isSmallOrStart() = type == NodeType.SMALL || type == NodeType.START