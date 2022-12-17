package problem.day17

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.lang.Integer.max

fun main(args: Array<String>) {
    val day = 17
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay17Part1, ::solveDay17Part2)
}

private data class Rock(
    val parts: List<Pair<Int, Int>>,
) {
    val dimensions = parts.maxOf { it.first } to parts.maxOf { it.second }

    fun collidesWithWallOrRocks(x: Int, y: Int, occupiedPixels: Set<Pair<Int, Int>>) = when {
        x < 0 || x + dimensions.first > 6 || y <= 0 -> true
        else -> parts.any { occupiedPixels.contains(x + it.first to y + it.second) }
    }
}

fun solveDay17Part1(input: List<String>): Int {
    val gasJets = input.first()
    val occupiedPixels = mutableSetOf<Pair<Int, Int>>()
    return getMaxHight(gasJets, occupiedPixels).first
}

fun solveDay17Part2(input: List<String>): Long {
    val gasJets = input.first()
    val occupiedPixels = mutableSetOf<Pair<Int, Int>>()
    val (maxHeight, jetIndex, rockIndex) = getMaxHight(gasJets, occupiedPixels)
    val (newMaxHeight, _, newRockIndex) = getMaxHight(
        gasJets = gasJets,
        occupiedPixels = occupiedPixels,
        rocksIndex = rockIndex,
        maxHight = maxHeight,
        jetIndex = jetIndex,
    ) { currentMaxHeight, currentJetIndex, currentRockIndex ->
        (rockIndex == currentRockIndex)
                || (jetIndex % gasJets.length != currentJetIndex % gasJets.length)
                || (rockIndex % ROCKS.size != currentRockIndex % ROCKS.size)
                || (!occupiedPixels.matchesPattern(maxHeight, currentMaxHeight))
    }
    return ((1_000_000_000_000L - 2022) / (newRockIndex - rockIndex)) * (newMaxHeight - maxHeight) +
            getMaxHight(gasJets, mutableSetOf()) { _, _, currentRockIndex ->
                currentRockIndex < 2022 + (1_000_000_000_000L - 2022) % (newRockIndex - rockIndex)
            }.first
}

private fun Set<Pair<Int, Int>>.matchesPattern(maxHeight: Int, otherMaxHeight: Int, deltaY: Int = 20): Boolean {
    return (0 until deltaY).all { y ->
        (0..6).all { x ->
            contains(maxHeight - y to x) == contains(otherMaxHeight - y to x)
        }
    }
}

private fun getMaxHight(
    gasJets: String,
    occupiedPixels: MutableSet<Pair<Int, Int>>,
    rocksIndex: Int = 0,
    maxHight: Int = 0,
    jetIndex: Int = 0,
    continuePrediacte: (Int, Int, Int) -> Boolean = { _, _, rockIndex -> rockIndex < 2022 },
): Triple<Int, Int, Int> {
    var currentMaxHeight = maxHight
    var currentJetIndex = jetIndex
    var currentRockIndex = rocksIndex
    while (continuePrediacte(currentMaxHeight, currentJetIndex, currentRockIndex)) {
        val rock = ROCKS[currentRockIndex % ROCKS.size]
        var (rockX, rockY) = 2 to currentMaxHeight + 4
        do {
            when (gasJets[currentJetIndex]) {
                '<' -> rockX = if (rock.collidesWithWallOrRocks(rockX - 1, rockY, occupiedPixels)) rockX else rockX - 1
                '>' -> rockX = if (rock.collidesWithWallOrRocks(rockX + 1, rockY, occupiedPixels)) rockX else rockX + 1
            }
            currentJetIndex = (currentJetIndex + 1) % gasJets.length
            val canMoveDown = !rock.collidesWithWallOrRocks(rockX, rockY - 1, occupiedPixels)
            if (canMoveDown) {
                rockY--
            }
        } while (canMoveDown)
        rock.parts.forEach { occupiedPixels.add(it.first + rockX to it.second + rockY) }
        currentMaxHeight = max(currentMaxHeight, rockY + rock.dimensions.second)
        currentRockIndex++
    }
    return Triple(currentMaxHeight, currentJetIndex, currentRockIndex)
}

private val ROCK_1 = Rock(listOf(0 to 0, 1 to 0, 2 to 0, 3 to 0))
private val ROCK_2 = Rock(listOf(1 to 0, 0 to 1, 1 to 1, 2 to 1, 1 to 2))
private val ROCK_3 = Rock(listOf(0 to 0, 1 to 0, 2 to 0, 2 to 1, 2 to 2))
private val ROCK_4 = Rock(listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3))
private val ROCK_5 = Rock(listOf(0 to 0, 1 to 0, 0 to 1, 1 to 1))
private val ROCKS = listOf(ROCK_1, ROCK_2, ROCK_3, ROCK_4, ROCK_5)
