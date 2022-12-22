package problem.day22

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 22
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay22Part1, ::solveDay22Part2)
}

private data class Move(
    val col: Int,
    val row: Int,
    val facing: Int,
) {

    fun getPassword() = 1000 * (row + 1) + 4 * (col + 1) + facing
}

fun solveDay22Part1(input: List<String>): Int {
    val board = input.dropLast(2).let {
        val maxWidth = it.maxOf { row -> row.length }
        it.map { row -> row + String(CharArray(maxWidth - row.length) { ' ' }) }
    }
    val pathMap = mutableMapOf<Pair<Int, Int>, Int>()
    return findPassword(board, input.last(), pathMap).also {
        printBoard(board, pathMap)
    }
}

fun solveDay22Part2(input: List<String>): Int {
    val board = input.dropLast(2).let {
        val maxWidth = it.maxOf { row -> row.length }
        it.map { row -> row + String(CharArray(maxWidth - row.length) { ' ' }) }
    }
    val wrapMap = mutableMapOf<Move, Move>().apply {
        (0 until 50).forEach { index ->
            // right
            this[Move(0, index, 0)] = Move(99, 149 - index, 2)
            this[Move(100, 50 + index, 0)] = Move(100 + index, 49, 3)
            this[Move(100, 100 + index, 0)] = Move(149, 49 - index, 2)
            this[Move(50, 150 + index, 0)] = Move(50 + index, 149, 3)
            // down
            this[Move(index, 0, 1)] = Move(100 + index, 0, 1)
            this[Move(50 + index, 150, 1)] = Move(49, 150 + index, 2)
            this[Move(100 + index, 50, 1)] = Move(99, 50 + index, 2)
            // left
            this[Move(49, index, 2)] = Move(0, 149 - index, 0)
            this[Move(49, 50 + index, 2)] = Move(index, 100, 1)
            this[Move(149, 100 + index, 2)] = Move(50, 49 - index, 0)
            this[Move(149, 150 + index, 2)] = Move(50 + index, 0, 1)
            // up
            this[Move(index, 99, 3)] = Move(50, 50 + index, 0)
            this[Move(50 + index, 199, 3)] = Move(0, 150 + index, 0)
            this[Move(100 + index, 199, 3)] = Move(index, 199, 3)
        }
    }
    val pathMap = mutableMapOf<Pair<Int, Int>, Int>()
    return findPassword(board, input.last(), pathMap, wrapMap).also {
        printBoard(board, pathMap)
    }
}

private fun findPassword(
    board: List<String>,
    path: String,
    mutableMap: MutableMap<Pair<Int, Int>, Int>,
    wrapMap: Map<Move, Move>? = null,
): Int {
    var pos = Move(board[0].indexOf('.'), 0, 0)

    path.splitToSequence(Regex("(?=[LR])")).forEach { instruction ->
        val numTiles = if (instruction[0] == 'R' || instruction[0] == 'L') {
            pos = pos.copy(facing = (pos.facing + if (instruction[0] == 'L') 3 else 1) % 4)
            instruction.drop(1).toInt()
        } else {
            instruction.toInt()
        }
        (1..numTiles).asSequence().map {
            mutableMap[pos.col to pos.row] = pos.facing
            move(board, pos, wrapMap)?.also { pos = it }
        }.all { it != null }
    }
    mutableMap[pos.col to pos.row] = -1

    return pos.getPassword()
}

private fun move(board: List<String>, pos: Move, wrapMap: Map<Move, Move>?): Move? {
    if (board[pos.row][pos.col] == '#') return null
    val nextMove = Move(
        col = (pos.col + DELTAS[pos.facing].first).mod(board[0].length),
        row = (pos.row + DELTAS[pos.facing].second).mod(board.size),
        facing = pos.facing,
    ).let { wrapMap?.get(it) ?: it }
    return when (board[nextMove.row][nextMove.col]) {
        '.' -> nextMove
        else -> move(board, nextMove, wrapMap)
    }
}

private fun printBoard(
    board: List<String>,
    pathMap: MutableMap<Pair<Int, Int>, Int>
) {
    board.forEachIndexed { row, s ->
        s.forEachIndexed { col, c ->
            print(
                when (pathMap[col to row]) {
                    -1 -> 'X'
                    0 -> '>'
                    1 -> 'v'
                    2 -> '<'
                    3 -> '^'
                    else -> c
                }
            )
        }
        println()
    }
}

private val DELTAS = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
