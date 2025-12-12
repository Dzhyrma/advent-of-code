package problem.day11

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 11
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay11Part1, ::solveDay11Part2)
}

fun solveDay11Part1(input: List<String>): Long {
    val nodesMap = input.parse()
    val incomingNodes = extractIncomingNodes(nodesMap)

    val counter = mutableMapOf<String, Long>()
    counter["you"] = 1L
    return incomingNodes.countForNode("out", counter)
}

fun solveDay11Part2(input: List<String>): Long {
    val nodesMap = input.parse()
    val incomingNodes = extractIncomingNodes(nodesMap)

    val counter = mutableMapOf<String, Long>()
    counter["svr"] = 1L
    incomingNodes.countForNode("fft", counter)
    incomingNodes.countForNode("dac", counter)

    val counterFft = mutableMapOf<String, Long>()
    counterFft["fft"] = counter["fft"] ?: 0L
    incomingNodes.countForNode("dac", counterFft)

    val counterDac = mutableMapOf<String, Long>()
    counterDac["dac"] = counter["dac"] ?: 0L
    incomingNodes.countForNode("fft", counterDac)

    val counterOut = mutableMapOf<String, Long>()
    counterOut["fft"] = counterDac["fft"] ?: 0L
    counterOut["dac"] = counterFft["dac"] ?: 0L

    return incomingNodes.countForNode("out", counterOut)
}

private fun MutableMap<String, MutableSet<String>>.countForNode(
    node: String,
    counter: MutableMap<String, Long>,
): Long {
    if (counter.containsKey(node)) {
        return counter.getValue(node)
    }

    val total =
        getOrDefault(node, emptySet()).sumOf { parentNode ->
            countForNode(parentNode, counter)
        }

    counter[node] = total
    return total
}

private fun List<String>.parse(): Map<String, Set<String>> {
    val nodesMap = mutableMapOf<String, Set<String>>()
    this.forEach { line ->
        val parts = line.split(": ")
        val node = parts[0]
        val connections = parts[1].split(" ").toSet()
        nodesMap[node] = connections
    }
    return nodesMap
}

private fun extractIncomingNodes(nodesMap: Map<String, Set<String>>): MutableMap<String, MutableSet<String>> {
    val result = mutableMapOf<String, MutableSet<String>>()

    nodesMap.forEach { (node, outgoingNodes) ->
        outgoingNodes.forEach { neighbor ->
            val incomingSet = result.getOrPut(neighbor) { mutableSetOf() }
            incomingSet.add(node)
        }
    }
    return result
}
