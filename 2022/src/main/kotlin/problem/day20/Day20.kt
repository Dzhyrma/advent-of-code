package problem.day20

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*

fun main(args: Array<String>) {
    val day = 20
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay20Part1, ::solveDay20Part2)
}


fun solveDay20Part1(input: List<String>): Long {
    val encoded = input.map { it.toLong() }.asTreeMap().also { it.mix() }
    return encoded.findResult()
}

fun solveDay20Part2(input: List<String>): Long {
    val encoded = input.map { it.toLong() * 811589153 }.asTreeMap().also { tree ->
        var keysToMix = tree.keys.toList()
        repeat(10) { keysToMix = tree.mix(keysToMix) }
    }
    return encoded.findResult()
}

private fun TreeMap<Double, Long>.findResult() = entries.find { it.value == 0L }?.let {
    val keys = keys.toList()
    val index = keys.binarySearch(it.key)
    val nextIndex1 = (index + 1000).mod(size)
    val nextIndex2 = (index + 2000).mod(size)
    val nextIndex3 = (index + 3000).mod(size)
    getValue(keys[nextIndex1]) + getValue(keys[nextIndex2]) + getValue(keys[nextIndex3])
} ?: -1

private fun List<Long>.asTreeMap() =
    foldIndexed(TreeMap<Double, Long>()) { index, tree, value -> tree.also { it[index.toDouble()] = value } }

private fun TreeMap<Double, Long>.mix(keysToMix: List<Double> = keys.toList()) = keysToMix.map { key ->
    val currentValue = remove(key)!!
    val currentKeys = keys.toList()
    val currentIndex = -currentKeys.binarySearch(key) - 1
    val newKey = when (val nextIndex = (currentIndex + currentValue).mod(currentKeys.size)) {
        0 -> currentKeys[nextIndex] - 1
        else -> (currentKeys[nextIndex] + currentKeys[nextIndex - 1]) / 2
    }
    this[newKey] = currentValue
    newKey
}
