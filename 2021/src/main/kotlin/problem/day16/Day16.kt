package problem.day16

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.lang.StringBuilder

fun main(args: Array<String>) {
    val day = 16
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay16Part1, ::solveDay16Part2)
}

data class Packet(
    val version: Int,
    val type: Int,
    val bitLength: Int,
    val subPackets: List<Packet> = emptyList(),
    val binaryValue: String = "",
) {
    val versionSum: Int by lazy { version + subPackets.sumOf { it.versionSum } }

    val value: Long by lazy {
        when (type) {
            0 -> subPackets.sumOf { it.value }
            1 -> subPackets.fold(1) { acc, packet -> acc * packet.value }
            2 -> subPackets.minOf { it.value }
            3 -> subPackets.maxOf { it.value }
            4 -> binaryValue.toLong(2)
            5 -> if (subPackets[0].value > subPackets[1].value) 1 else 0
            6 -> if (subPackets[0].value < subPackets[1].value) 1 else 0
            7 -> if (subPackets[0].value == subPackets[1].value) 1 else 0
            else -> error("wrong type")
        }
    }
}

fun solveDay16Part1(input: List<String>): Int {
    val packet = input
        .first()
        .toBinaryFromHex()
        .parsePacket()
    return packet.versionSum
}

fun solveDay16Part2(input: List<String>): Long {
    val packet = input
        .first()
        .toBinaryFromHex()
        .parsePacket()
    return packet.value
}

private fun String.toBinaryFromHex() = toBigInteger(16).toString(2)
    .let { String(CharArray(length * 4 - it.length) { '0' }) + it }

private fun String.parsePacket(startIndex: Int = 0): Packet {
    val version = substring(startIndex until startIndex + 3).toInt(2)
    val type = substring(startIndex + 3 until startIndex + 6).toInt(2)
    var binaryValue = ""
    var bitLength = 6
    val subPackets = mutableListOf<Packet>()
    when (type) {
        4 -> {
            binaryValue = drop(startIndex + 6)
                .chunkedSequence(5)
                .takeWhileInclusive { it[0] == '1' }
                .map { it.drop(1) }
                .fold(StringBuilder()) { acc, bits -> acc.append(bits) }
                .toString()
            bitLength += binaryValue.length / 4 * 5
        }
        else -> {
            bitLength++
            when (get(startIndex + 6)) {
                '0' -> {
                    val lengthBits = substring(startIndex + bitLength until startIndex + bitLength + 15).toInt(2)
                    bitLength += 15
                    var deltaIndex = 0
                    while (deltaIndex < lengthBits) {
                        val subPacket = parsePacket(startIndex + bitLength + deltaIndex)
                        subPackets.add(subPacket)
                        deltaIndex += subPacket.bitLength
                    }
                    bitLength += lengthBits
                }
                else -> {
                    val lengthSubPackets = substring(startIndex + bitLength until startIndex + bitLength + 11).toInt(2)
                    bitLength += 11
                    repeat(lengthSubPackets) {
                        val subPacket = parsePacket(startIndex + bitLength)
                        subPackets.add(subPacket)
                        bitLength += subPacket.bitLength
                    }
                }
            }
        }
    }
    return Packet(version, type, bitLength, subPackets, binaryValue)
}

private fun <T> Sequence<T>.takeWhileInclusive(pred: (T) -> Boolean): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}