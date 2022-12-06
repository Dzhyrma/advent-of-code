package problem.day06

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}


fun solveDay06Part1(input: List<String>): Int {
    return findStartOfPacket(input.first())
}

fun solveDay06Part2(input: List<String>): Int {
    return findStartOfPacket(input.first(), 14)
}

private fun findStartOfPacket(dataStream: String, packetSize: Int = 4): Int {
    val charCount = IntArray('z' - 'a' + 1) { 0 }
    val markerSet = mutableSetOf<Char>()
    dataStream.forEachIndexed { index, char ->
        charCount[char - 'a']++
        markerSet += char
        if (index >= packetSize) {
            val removedChar = dataStream[index - packetSize]
            if (--charCount[removedChar - 'a'] == 0) {
                markerSet.remove(removedChar)
            }
        }
        if (markerSet.size == packetSize) {
            return index + 1
        }
    }
    return -1
}
