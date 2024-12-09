package problem.day09

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.TreeMap
import kotlin.math.min

typealias FileInfo = Pair<Int, Int>

fun main(args: Array<String>) {
    val day = 9
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay09Part1, ::solveDay09Part2)
}

fun solveDay09Part1(input: List<String>): Long {
    val memoryHash = input[0].map { it.digitToInt() }.toIntArray()
    var memoryIndex = 0L
    var memoryLastFileIndex = ((memoryHash.size - 1) shr 1) shl 1
    var checksum = 0L
    memoryHash.forEachIndexed { index, length ->
        when {
            memoryLastFileIndex < index -> return@forEachIndexed // everything already moved
            // file
            index % 2 == 0 -> {
                checksum += fileHash(index shr 1, length, memoryIndex)
                memoryIndex += length
            }
            // empty memory space
            else -> {
                var emptyMemoryLength = length
                while (emptyMemoryLength > 0 && memoryLastFileIndex >= index) {
                    val fileChunkLength = min(memoryHash[memoryLastFileIndex], emptyMemoryLength)
                    checksum += fileHash(memoryLastFileIndex shr 1, fileChunkLength, memoryIndex)
                    memoryHash[memoryLastFileIndex] -= fileChunkLength
                    emptyMemoryLength -= fileChunkLength
                    memoryIndex += fileChunkLength
                    if (memoryHash[memoryLastFileIndex] == 0) memoryLastFileIndex -= 2
                }
            }
        }
    }
    return checksum
}

fun solveDay09Part2(input: List<String>): Long {
    val (files, emptyBlocks) = parseMemoryState(input)

    defrag(files, emptyBlocks)

    return files.mapIndexed { fileId, (fileBlockIndex, fileLength) ->
        fileHash(fileId, fileLength, fileBlockIndex.toLong())
    }.sum()
}

private fun parseMemoryState(input: List<String>): Pair<Array<FileInfo>, TreeMap<Int, Int>> {
    val files = Array((input[0].length + 1) shr 1) { DUMMY_FILE }
    val emptyBlocks = TreeMap<Int, Int>()
    input[0].foldIndexed(0) { index, memoryBlockIndex, c ->
        val length = c.digitToInt()
        if (index % 2 == 0) {
            files[index shr 1] = memoryBlockIndex to length
        } else {
            emptyBlocks[memoryBlockIndex] = length
        }
        memoryBlockIndex + length
    }
    return Pair(files, emptyBlocks)
}

private fun defrag(files: Array<FileInfo>, emptyBlocks: TreeMap<Int, Int>) {
    files.indices.reversed().forEach { fileId ->
        val (fileBlockIndex, fileLength) = files[fileId]
        emptyBlocks.firstNotNullOfOrNull { (emptySpaceIndex, emptyBlockSize) ->
            if (emptyBlockSize >= fileLength) emptySpaceIndex to emptyBlockSize else null
        }?.let { (emptySpaceIndex, emptyBlockSize) ->
            if (fileBlockIndex > emptySpaceIndex) {
                files[fileId] = emptySpaceIndex to fileLength
                emptyBlocks.remove(emptySpaceIndex)
                if (emptyBlockSize > fileLength) {
                    emptyBlocks[emptySpaceIndex + fileLength] = emptyBlockSize - fileLength
                }
            }
        }
    }
}

private fun fileHash(fileId: Int, fileLength: Int, memoryBlockIndex: Long) =
    (fileId * ((memoryBlockIndex shl 1) + fileLength - 1) * fileLength) shr 1

private val DUMMY_FILE = FileInfo(0, 0)
