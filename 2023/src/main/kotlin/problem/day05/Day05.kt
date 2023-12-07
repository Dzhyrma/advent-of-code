package problem.day05

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

fun solveDay05Part1(input: List<String>): Long {
    val data = input.parseInput()
    return data.mappings.fold(data.seeds.toMutableList()) { acc, mappings ->
        acc.indices.forEach { i ->
            mappings.find { acc[i] in it.sourceRange }?.also {
                acc[i] = acc[i] + it.delta
            }
        }
        acc
    }.min()
}

fun solveDay05Part2(input: List<String>): Long {
    val data = input.parseInput()
    val seeds = data.seeds.zipWithNext().filterIndexed { index, _ -> index.mod(2) == 0 }
        .map { it.first until it.first + it.second }
        .sortedBy { it.first }
    return seeds.minOf { applyMapping(it, data.mappings) }
}

private fun applyMapping(
    sourceRange: LongRange,
    mappings: List<List<Mapping>>,
    level: Int = 0,
    mappingIndex: Int = 0
): Long {
    if (sourceRange.isEmpty()) return Long.MAX_VALUE
    if (level >= mappings.size || mappingIndex >= mappings[level].size) return sourceRange.first
    return mappings[level][mappingIndex].let { mapping ->
        val intersectionRange = mapping.sourceRange intersect sourceRange
        if (intersectionRange.isEmpty())
            applyMapping(sourceRange, mappings, level, mappingIndex + 1)
        else {
            val destinationRange = (intersectionRange.first + mapping.delta)..(intersectionRange.last + mapping.delta)
            val leftLeftover = sourceRange.first until intersectionRange.first
            val rightLeftover = intersectionRange.last + 1..sourceRange.last
            min(
                applyMapping(destinationRange, mappings, level + 1),
                min(
                    applyMapping(leftLeftover, mappings, level, mappingIndex + 1),
                    applyMapping(rightLeftover, mappings, level, mappingIndex + 1)
                )
            )
        }
    }
}

private fun List<String>.parseInput() = Data(
    seeds = first().substring(7).split(' ').map { it.toLong() },
    mappings = drop(2)
        .filter { !it.contains(':') }
        .map { line -> line.split(' ').filter { it.isNotEmpty() }.map { it.toLong() } }
        .fold(mutableListOf<MutableList<Mapping>>(mutableListOf())) { acc, numbers ->
            if (numbers.isEmpty())
                acc.add(mutableListOf())
            else
                acc.last().add(
                    Mapping(
                        sourceRange = numbers[1] until (numbers[1] + numbers[2]),
                        delta = numbers[0] - numbers[1],
                    )
                )
            acc
        },
)

private infix fun LongRange.intersect(other: LongRange): LongRange {
    return max(first, other.first)..min(last, other.last)
}

private data class Data(
    val seeds: List<Long>,
    val mappings: List<List<Mapping>>,
)

private data class Mapping(
    val sourceRange: LongRange,
    val delta: Long,
)