package problem.day08

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}

fun solveDay08Part1(input: List<String>): Int {
    val coordinates = input.readPoints3D()

    val distances = coordinates.calculateAllDistances().map { it.value to it.key }.sortedBy { it.first }
    val edges = distances.take(1000).map { it.second }
    val circuits = mutableListOf<MutableSet<Point3D>>()
    val pointInCircuit = mutableMapOf<Point3D, Int>().withDefault { -1 }
    edges.forEach { (from, to) ->
        val fromIndex = pointInCircuit.getValue(from)
        val toIndex = pointInCircuit.getValue(to)
        when {
            fromIndex == -1 && toIndex == -1 -> {
                circuits.add(mutableSetOf(from, to))
                val circuitIndex = circuits.lastIndex
                pointInCircuit[from] = circuitIndex
                pointInCircuit[to] = circuitIndex
            }
            fromIndex == -1 -> {
                pointInCircuit[from] = toIndex
                circuits[toIndex].add(from)
            }
            toIndex == -1 -> {
                pointInCircuit[to] = fromIndex
                circuits[fromIndex].add(to)
            }
            fromIndex != toIndex -> {
                val fromCircuit = circuits[fromIndex]
                val toCircuit = circuits[toIndex]
                fromCircuit.addAll(toCircuit)
                toCircuit.forEach { pointInCircuit[it] = fromIndex }
                circuits[toIndex] = mutableSetOf()
            }
        }
    }
    return circuits.sortedBy { it.size }.takeLast(3).let { it[0].size * it[1].size * it[2].size }
}

fun solveDay08Part2(input: List<String>): Long {
    val coordinates = input.readPoints3D()

    val distances = coordinates.calculateAllDistances().map { it.value to it.key }.sortedBy { it.first }
    val circuits = mutableListOf<MutableSet<Point3D>>()
    val pointInCircuit = mutableMapOf<Point3D, Int>().withDefault { -1 }
    var isLastEdge = false
    val lastEdge =
        distances.first { (_, edge) ->
            val (from, to) = edge
            val fromIndex = pointInCircuit.getValue(from)
            val toIndex = pointInCircuit.getValue(to)
            when {
                fromIndex == -1 && toIndex == -1 -> {
                    circuits.add(mutableSetOf(from, to))
                    val circuitIndex = circuits.lastIndex
                    pointInCircuit[from] = circuitIndex
                    pointInCircuit[to] = circuitIndex
                }
                fromIndex == -1 -> {
                    pointInCircuit[from] = toIndex
                    circuits[toIndex].add(from)
                    isLastEdge = coordinates.size == circuits[toIndex].size
                }
                toIndex == -1 -> {
                    pointInCircuit[to] = fromIndex
                    circuits[fromIndex].add(to)
                    isLastEdge = coordinates.size == circuits[fromIndex].size
                }
                fromIndex != toIndex -> {
                    val fromCircuit = circuits[fromIndex]
                    val toCircuit = circuits[toIndex]
                    fromCircuit.addAll(toCircuit)
                    toCircuit.forEach { pointInCircuit[it] = fromIndex }
                    circuits[toIndex] = mutableSetOf()
                    isLastEdge = coordinates.size == fromCircuit.size
                }
            }
            isLastEdge
        }
    return lastEdge.second.from.x * lastEdge.second.to.x
}

private fun List<String>.readPoints3D(): List<Point3D> =
    map {
        val parts = it.split(",")
        Point3D(parts[0].toLong(), parts[1].toLong(), parts[2].toLong())
    }

private fun List<Point3D>.calculateAllDistances(): Map<Edge, Double> {
    val distances = mutableMapOf<Edge, Double>()
    for (i in indices) {
        for (j in i + 1 until size) {
            val from = this[i]
            val to = this[j]
            val distance =
                sqrt(((from.x - to.x) * (from.x - to.x) + (from.y - to.y) * (from.y - to.y) + (from.z - to.z) * (from.z - to.z)).toDouble())
            distances[Edge(from, to)] = distance
        }
    }
    return distances
}

private data class Point3D(
    val x: Long,
    val y: Long,
    val z: Long,
)

private data class Edge(
    val from: Point3D,
    val to: Point3D,
)
