package problem.day23

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 23
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay23Part1, ::solveDay23Part2)
}

fun solveDay23Part1(input: List<String>): Int {
    val connections = input.parseInput()
    return connections.countSetsWithT()
}

fun solveDay23Part2(input: List<String>): String {
    val connections = input.parseInput()
    return connections.findLanPartyPassword()
}

private fun List<String>.parseInput() = flatMap { it.split("-").let { (a, b) -> listOf(a to b, b to a) } }
    .groupBy({ it.first }, { it.second })
    .mapValues { (_, v) -> v.toSet() }
    .withDefault { emptySet() }

private fun Map<String, Set<String>>.isConnected(group: Set<String>): Boolean =
    group.all { node -> this.getValue(node).containsAll(group - node) }

private fun Map<String, Set<String>>.countSetsWithT() = keys
    .filter { it.startsWith("t") }
    .flatMap { a -> getValue(a).flatMap { b -> getValue(b).map { c -> setOf(a, b, c) } } }
    .distinct()
    .filter { it.size == 3 }
    .count { isConnected(it) }

private fun Map<String, Set<String>>.findLanPartyPassword(): String {
    val cliques = mutableListOf<Set<String>>()
    bronKerbosch(emptySet(), keys.toMutableSet(), mutableSetOf(), cliques)
    return cliques.maxBy { it.size }.sorted().joinToString(",")
}

private fun Map<String, Set<String>>.bronKerbosch(
    r: Set<String>,
    p: MutableSet<String>,
    x: MutableSet<String>,
    cliques: MutableList<Set<String>>,
) {
    if (p.isEmpty() && x.isEmpty()) {
        cliques.add(r)
        return
    }
    val pivot = (p + x).first()
    val nonNeighbors = p - (this.getValue(pivot))
    nonNeighbors.forEach { v ->
        bronKerbosch(
            r = r + v,
            p = p.intersect(this.getValue(v)).toMutableSet(),
            x = x.intersect(this.getValue(v)).toMutableSet(),
            cliques = cliques,
        )
        p -= v
        x += v
    }
}
