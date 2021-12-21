package problem.day21

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.max

fun main(args: Array<String>) {
    val day = 21
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay21Part1, ::solveDay21Part2)
}

interface Dice {

    val rollCount: Int

    fun roll(times: Int = 1): Int
}

class DetermenisticDice(
    private val maxValue: Int = 100,
) : Dice {
    var currentValue: Int = 0
    override var rollCount: Int = 0
        private set

    override fun roll(times: Int): Int {
        rollCount += times
        return (1..times).sumOf {
            (currentValue + 1).also {
                currentValue += 1
                currentValue %= maxValue
            }
        }
    }
}

fun solveDay21Part1(input: List<String>): Int {
    val playerPositions = parse(input)

    var player1Position = playerPositions.first - 1
    var player2Position = playerPositions.second - 1
    val dice = DetermenisticDice()
    var player1Score = 0
    var player2Score = 0
    while (true) {
        player1Position = (player1Position + dice.roll(3)) % 10
        player1Score += player1Position + 1
        if (player1Score >= 1000) {
            return dice.rollCount * player2Score
        }

        player2Position = (player2Position + dice.roll(3)) % 10
        player2Score += player2Position + 1
        if (player2Score >= 1000) {
            return dice.rollCount + player1Score
        }
    }
}

private val DICE_RESULTS = mapOf(
    3 to 1,
    4 to 3,
    5 to 6,
    6 to 7,
    7 to 6,
    8 to 3,
    9 to 1,
)

fun solveDay21Part2(input: List<String>): Long {
    val playerPositions = parse(input)

    var states = mapOf(((playerPositions.first - 1) to (playerPositions.second - 1) to (0 to 0)) to 1L)
    var player1Wins = 0L
    var player2Wins = 0L
    do {
        val newStates = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Long>()
        states.forEach { (positions, scores), universes ->
            DICE_RESULTS.forEach { (diceRoll, multiplier) ->
                val newPlayer1Position = (positions.first + diceRoll) % 10
                val newPlayer1Score = scores.first + newPlayer1Position + 1
                if (newPlayer1Score >= 21)
                    player1Wins += universes * multiplier
                else {
                    val key = (newPlayer1Position to positions.second) to (newPlayer1Score to scores.second)
                    newStates[key] = newStates.getOrDefault(key, 0) + universes * multiplier
                }
            }
        }
        states = mutableMapOf()
        newStates.forEach { (positions, scores), universes ->
            DICE_RESULTS.forEach { (diceRoll, multiplier) ->
                val newPlayer2Position = (positions.second + diceRoll) % 10
                val newPlayer2Score = scores.second + newPlayer2Position + 1
                if (newPlayer2Score >= 21)
                    player2Wins += universes * multiplier
                else {
                    val key = (positions.first to newPlayer2Position) to (scores.first to newPlayer2Score)
                    states[key] = states.getOrDefault(key, 0) + universes * multiplier
                }
            }
        }
    } while (states.isNotEmpty())
    return max(player1Wins, player2Wins)
}

private fun parse(input: List<String>): Pair<Int, Int> {
    return input[0].drop(28).toInt() to input[1].drop(28).toInt()
}
