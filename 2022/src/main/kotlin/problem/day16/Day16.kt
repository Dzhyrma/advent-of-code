package problem.day16

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 16
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay16Part1, ::solveDay16Part2)
}


fun solveDay16Part1(input: List<String>): Int {
    val (flows, edges) = parseFlowsAndEdges(input)
    val improvedEdges = extractOnlyImportantValves(flows, edges)
    return improvedEdges.findBestRoute(flows)
}

fun solveDay16Part2(input: List<String>): Int {
    val (flows, edges) = parseFlowsAndEdges(input)
    val improvedEdges = extractOnlyImportantValves(flows, edges)
    return (improvedEdges.keys - "AA").partition().maxOf { (valveGroup1, valveGroup2) ->
        val flowValue1 = improvedEdges.findBestRoute(flows, timeLeft = 26, notVisited = valveGroup1.toMutableSet())
        val flowValue2 = improvedEdges.findBestRoute(flows, timeLeft = 26, notVisited = valveGroup2.toMutableSet())
        flowValue1 + flowValue2
    }
}

private fun parseFlowsAndEdges(input: List<String>): Pair<Map<String, Int>, Map<String, Set<String>>> {
    val parsedInput = input
        .map {
            INPUT_REGEX.matchEntire(it)?.groupValues ?: throw IllegalArgumentException("Wrong input")
        }
        .map { it[1] to (it[2].toInt() to it[3].split(", ").toSet()) }
    val flows = parsedInput.associate { (valve, values) -> valve to values.first }
    val edges = parsedInput.associate { (valve, values) -> valve to values.second }
    return Pair(flows, edges)
}

private fun extractOnlyImportantValves(
    flows: Map<String, Int>,
    edges: Map<String, Set<String>>
): Map<String, Map<String, Int>> {
    val importantValves = flows.filter { it.value > 0 }.map { it.key }
    val improvedEdges = (importantValves + "AA").associateWith { source ->
        importantValves
            .filter { it != source }
            .mapNotNull { destination -> edges.bfs(source, destination)?.let { destination to it } }
            .toMap()
    }
    return improvedEdges
}

private fun <T> Set<T>.partition(): List<Pair<Set<T>, Set<T>>> {
    return when {
        isEmpty() -> listOf(Pair(emptySet(), emptySet()))
        else -> {
            val partitions = (this - first()).partition()
            val partitionsWithFirst = partitions.map { Pair(it.first + first(), it.second) }
            val partitionsWithoutFirst = partitions.map { Pair(it.first, it.second + first()) }
            partitionsWithFirst + partitionsWithoutFirst
        }
    }
}

private fun Map<String, Set<String>>.bfs(
    current: String,
    destination: String,
    visited: Set<String> = setOf(current),
): Int? {
    val neighbors = this[current]
    return when {
        neighbors == null -> return null
        neighbors.contains(destination) -> return 1
        else -> neighbors.filter { !visited.contains(it) }
            .mapNotNull { bfs(it, destination, visited + it) }
            .minOrNull()
            ?.let { it + 1 }
    }
}

private fun Map<String, Map<String, Int>>.findBestRoute(
    flows: Map<String, Int>,
    currentValve: String = "AA",
    currentFlow: Int = 0,
    timeLeft: Int = 30,
    notVisited: MutableSet<String> = keys.toMutableSet().also { it.remove("AA") },
): Int {
    val restFlowSum = timeLeft * currentFlow
    if (timeLeft <= 0) return restFlowSum
    val neigbourValves = this[currentValve] ?: return restFlowSum
    val toVisitNext = notVisited intersect neigbourValves.keys
    if (toVisitNext.isEmpty()) return restFlowSum
    return toVisitNext.maxOf { nextValve ->
        val timeToTravelAndTurnOnNextValve = neigbourValves.getValue(nextValve) + 1
        notVisited.remove(nextValve)
        findBestRoute(
            flows = flows,
            currentValve = nextValve,
            currentFlow = currentFlow + flows.getValue(nextValve),
            timeLeft = timeLeft - timeToTravelAndTurnOnNextValve,
            notVisited = notVisited,
        ).also { notVisited.add(nextValve) } + (timeToTravelAndTurnOnNextValve * currentFlow)
    }
}

private val INPUT_REGEX = Regex("Valve (.+) has flow rate=(.+); tunnels? leads? to valves? (.+)")
