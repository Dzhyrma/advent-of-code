package problem.day04

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}

private const val BOARD_SIZE = 5

data class Line(
    var hitCount: Int = 0,
)

data class BingoNumber(
    val column: Line,
    val row: Line,
    var wasHit: Boolean = false,
)

class BingoBoard {
    private val columns = Array(BOARD_SIZE) { Line() }
    private val rows = Array(BOARD_SIZE) { Line() }
    private val numbers = HashMap<Int, BingoNumber>(BOARD_SIZE * BOARD_SIZE)
    var totalScore: Int = 0
        private set

    fun setNumber(column: Int, row: Int, number: Int) {
        numbers[number] = BingoNumber(columns[column], rows[row])
        totalScore += number
    }

    fun checkNumber(number: Int): Boolean {
        return numbers[number]?.takeIf { !it.wasHit }?.let {
            it.column.hitCount++
            it.row.hitCount++
            it.wasHit = true
            totalScore -= number
            it.column.hitCount == BOARD_SIZE || it.row.hitCount == BOARD_SIZE
        } ?: false
    }
}

fun solveDay04Part1(input: List<String>): Int {
    val numbers = prepareNumbers(input)
    val boards = prepareBoards(input)

    var bingoBoard: BingoBoard? = null
    val bingoNumber = numbers.find { number ->
        bingoBoard = boards.find { board ->
            board.checkNumber(number)
        }
        bingoBoard != null
    }

    return (bingoNumber ?: 0) * (bingoBoard?.totalScore ?: 0)
}

fun solveDay04Part2(input: List<String>): Int {
    val numbers = prepareNumbers(input)
    val boards = prepareBoards(input)
    val boardsSet = boards.toMutableSet()

    var lastBingoBoard: BingoBoard? = null
    val bingoNumber = numbers.find { number ->
        boardsSet.removeIf { board ->
            lastBingoBoard = board
            board.checkNumber(number)
        }
        boardsSet.size == 0
    }

    return (bingoNumber ?: 0) * (lastBingoBoard?.totalScore ?: 0)
}

private fun prepareBoards(input: List<String>) =
    input.drop(1).chunked(BOARD_SIZE + 1) { rows ->
        BingoBoard().apply {
            rows.drop(1).forEachIndexed { rowIndex, row ->
                row.split(' ').filter { it.isNotBlank() }.map { it.toInt() }.forEachIndexed { columnIndex, number ->
                    this.setNumber(columnIndex, rowIndex, number)
                }
            }
        }
    }

private fun prepareNumbers(input: List<String>) = input[0].split(',').map { it.toInt() }
