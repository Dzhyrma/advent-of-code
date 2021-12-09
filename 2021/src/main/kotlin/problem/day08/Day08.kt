package problem.day08

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}


fun solveDay08Part1(input: List<String>): Int {
    return input
        .map { it.split(" | ").drop(1).first() }
        .sumOf { line ->
            line.split(' ').count { it.length == 7 || (it.length in 2..4) }
        }
}

fun solveDay08Part2(input: List<String>): Int {
    return input
        .map { line -> line.split(" | ").let { it[0].toSets() to it[1].toSets() } }
        .sumOf { (inputSets, decodeSets) ->
            val digitSets = Array<Set<Char>>(10) { emptySet() }
            digitSets[1] = inputSets.first { it.size == 2 }
            digitSets[4] = inputSets.first { it.size == 4 }
            digitSets[7] = inputSets.first { it.size == 3 }
            digitSets[8] = inputSets.first { it.size == 7 }
            digitSets[3] = inputSets.first { it.size == 5 && (it intersect digitSets[1]).size == 2 }
            digitSets[9] = inputSets.first { it.size == 6 && (it intersect digitSets[4]).size == 4 }
            val bdSet = digitSets[4] - digitSets[1]
            digitSets[5] = inputSets.first { it.size == 5 && (it intersect bdSet).size == 2 }
            digitSets[2] = inputSets.first { it.size == 5 && it != digitSets[3] && it != digitSets[5] }
            digitSets[6] = inputSets.first {
                it.size == 6 && (it intersect digitSets[1]).size == 1 && (it intersect bdSet).size == 2
            }
            digitSets[0] = inputSets.first { it.size == 6 && it != digitSets[9] && it != digitSets[6] }

            decodeSets.map { setToDecode ->
                digitSets.indexOfFirst { it.containsAll(setToDecode) && setToDecode.containsAll(it) }
            }.reduce { acc, digit -> acc * 10 + digit }
        }
}

private fun String.toSets() = split(' ').map { it.toSet() }