package problem.day23

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*
import kotlin.math.abs
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 23
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay23Part1, ::solveDay23Part2)
}


enum class AmphipodType(
    val movePrice: Int,
) {
    Amber(1),
    Bronze(10),
    Copper(100),
    Desert(1000),
}

data class Amphipod(
    val type: AmphipodType,
) {
    private var moveCount = 0

    val canMove: Boolean
        get() = moveCount < 2

    fun move() {
        moveCount++
    }

    fun undo() {
        moveCount--
    }
}

interface HallwayCell {

    val blocking: Boolean

    fun peek(): Amphipod?

    fun canTake(): Boolean

    fun take(): Pair<Amphipod, Int>

    fun canPut(amphipod: Amphipod): Boolean

    fun put(amphipod: Amphipod): Int
}

data class Room(
    val type: AmphipodType,
    val stack: Stack<Amphipod>,
) : HallwayCell {
    private val maxSize: Int = stack.size
    private var wrongAmphipodsCount = maxSize - stack.indexOfFirst { it.type != type }

    val ready
        get() = wrongAmphipodsCount <= 0

    val full
        get() = stack.size == maxSize

    override val blocking: Boolean
        get() = false

    override fun peek(): Amphipod? = stack.peek()

    override fun canTake(): Boolean = wrongAmphipodsCount > 0

    override fun take(): Pair<Amphipod, Int> {
        if (wrongAmphipodsCount > 0)
            wrongAmphipodsCount--
        return stack.pop().let { amphipod -> amphipod to (maxSize - stack.size) * amphipod.type.movePrice }
    }

    override fun canPut(amphipod: Amphipod): Boolean {
        return ready && amphipod.type == type
    }

    override fun put(amphipod: Amphipod): Int {
        if (amphipod.type != type || !ready)
            wrongAmphipodsCount++
        stack.push(amphipod)
        return (maxSize - stack.size + 1) * amphipod.type.movePrice
    }
}

data class HallwayCellDefault(
    var amphipod: Amphipod? = null,
) : HallwayCell {

    override val blocking: Boolean
        get() = amphipod != null

    override fun peek() = amphipod

    override fun canTake() = amphipod?.canMove ?: false

    override fun take(): Pair<Amphipod, Int> = (amphipod!! to 0).also { amphipod = null }

    override fun canPut(amphipod: Amphipod): Boolean = this.amphipod == null

    override fun put(amphipod: Amphipod): Int {
        this.amphipod = amphipod
        return 0
    }

}

class Burrow(
    aRoom: Room,
    bRoom: Room,
    cRoom: Room,
    dRoom: Room,
) {
    private val rooms = arrayOf(aRoom, bRoom, cRoom, dRoom)
    private val hallway = arrayOf(
        HallwayCellDefault(),
        HallwayCellDefault(),
        aRoom,
        HallwayCellDefault(),
        bRoom,
        HallwayCellDefault(),
        cRoom,
        HallwayCellDefault(),
        dRoom,
        HallwayCellDefault(),
        HallwayCellDefault(),
    )

    private var moveCount = 0
    private var minMoveCount = Int.MAX_VALUE

    fun simulate(): Int {
        moveCount = 0
        minMoveCount = Int.MAX_VALUE

        simulateTurns()

        return minMoveCount
    }

    private fun simulateTurns() {
        if (rooms.all { it.ready && it.full }) {
            minMoveCount = min(minMoveCount, moveCount)
            return
        }

        hallway.forEachIndexed { index, hallwayCell ->
            if (hallwayCell.canTake()) {
                hallwayCell.peek()?.also { amphipod ->
                    var blocked = false
                    var nextIndex = index
                    while (nextIndex > 0 && !blocked) {
                        nextIndex--
                        val nextCell = hallway[nextIndex]

                        if (nextCell.canPut(amphipod) && nextCell.javaClass != hallwayCell.javaClass) {
                            val movePrice = abs(index - nextIndex) * amphipod.type.movePrice
                            tryMove(hallwayCell, nextCell, amphipod, movePrice)
                        }

                        blocked = nextCell.blocking
                    }

                    blocked = false
                    nextIndex = index + 1
                    while (nextIndex < hallway.size && !blocked) {
                        val nextCell = hallway[nextIndex]

                        if (nextCell.canPut(amphipod) && nextCell.javaClass != hallwayCell.javaClass) {
                            val movePrice = abs(index - nextIndex) * amphipod.type.movePrice
                            tryMove(hallwayCell, nextCell, amphipod, movePrice)
                        }

                        blocked = nextCell.blocking
                        nextIndex++
                    }
                }
            }
        }
    }

    private fun tryMove(
        hallwayCell: HallwayCell,
        nextCell: HallwayCell,
        amphipod: Amphipod,
        movePrice: Int,
    ) {
        val (_, takePrice) = hallwayCell.take()
        val putPrice = nextCell.put(amphipod)
        val totalMovePrice = takePrice + putPrice + movePrice
        moveCount += totalMovePrice
        amphipod.move()

        simulateTurns()

        amphipod.undo()
        moveCount -= totalMovePrice
        hallwayCell.put(nextCell.take().first)
    }
}


fun solveDay23Part1(input: List<String>): Int {
    val burrow = parseBurrow(input)
    return burrow.simulate()
}

fun solveDay23Part2(input: List<String>): Int {
    val burrow = parseBurrow(input, true)
    return burrow.simulate()
}

private fun parseBurrow(input: List<String>, extraLines: Boolean = false): Burrow {
    val finalInput = if (extraLines) input.toMutableList().also {
        it.addAll(
            3, listOf(
                "  #D#C#B#A#",
                "  #D#B#A#C#"
            )
        )
    } else input
    return (0..3).map {
        val stack = Stack<Amphipod>()
        if (extraLines) {
            stack.push(Amphipod(AmphipodType.values()[finalInput[5][3 + it * 2] - 'A']))
            stack.push(Amphipod(AmphipodType.values()[finalInput[4][3 + it * 2] - 'A']))
        }
        stack.push(Amphipod(AmphipodType.values()[finalInput[3][3 + it * 2] - 'A']))
        stack.push(Amphipod(AmphipodType.values()[finalInput[2][3 + it * 2] - 'A']))
        Room(AmphipodType.values()[it], stack)
    }.let { Burrow(it[0], it[1], it[2], it[3]) }
}