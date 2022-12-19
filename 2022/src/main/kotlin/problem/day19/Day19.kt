package problem.day19

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.ceil

fun main(args: Array<String>) {
    val day = 19
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay19Part1, ::solveDay19Part2)
}


data class RobotCosts(
    val oreRobot: List<Int>,
    val clayRobot: List<Int>,
    val obsidianRobot: List<Int>,
    val geodeRobot: List<Int>,
)

fun solveDay19Part1(input: List<String>): Int {
    val blueprints = parseBlueprints(input)
    return blueprints.mapIndexed { index, blueprint ->
        blueprint.simulate() * (index + 1)
    }.sum()
}

fun solveDay19Part2(input: List<String>): Int {
    val blueprints = parseBlueprints(input)
    return blueprints.take(3)
        .map { blueprint -> blueprint.simulate(minutesLeft = 32).also { println(it) } }
        .reduce { acc, value -> value * acc }
}

private fun parseBlueprints(input: List<String>): List<RobotCosts> {
    val blueprints = input.map { REGEX.matchEntire(it)?.groupValues ?: throw IllegalArgumentException("Wrong input") }
        .map { values -> values.drop(1).map { it.toInt() } }
        .map {
            RobotCosts(
                oreRobot = listOf(it[0], 0, 0, 0),
                clayRobot = listOf(it[1], 0, 0, 0),
                obsidianRobot = listOf(it[2], it[3], 0, 0),
                geodeRobot = listOf(it[4], 0, it[5], 0),
            )
        }
    return blueprints
}

private fun RobotCosts.simulate(
    robots: List<Int> = listOf(1, 0, 0, 0),
    resources: List<Int> = listOf(0, 0, 0, 0),
    minutesLeft: Int = 24,
): Int {
    if (minutesLeft == 0) return resources[3]

    val buildRobotInNDays = { robotCosts: List<Int>, robotIndex: Int, n: Int ->
        simulate(
            robots = robots.mapIndexed { index, value -> if (index == robotIndex) value + 1 else value },
            resources = resources.mapIndexed { index, value -> value + robots[index] * (n + 1) - robotCosts[index] },
            minutesLeft = minutesLeft - 1 - n
        )
    }
    val buildOreRobot = { n: Int -> buildRobotInNDays(oreRobot, 0, n) }
    val buildClayRobot = { n: Int -> buildRobotInNDays(clayRobot, 1, n) }
    val buildObsidianRobot = { n: Int -> buildRobotInNDays(obsidianRobot, 2, n) }
    val buildGeodeRobot = { n: Int -> buildRobotInNDays(geodeRobot, 3, n) }

    fun estimateMinutesNeededToBuildRobot(
        robotBlueprint: List<Int>,
        vararg resourceIndices: Int,
    ) = resourceIndices.maxOf {
        when {
            resources[it] >= robotBlueprint[it] -> 0
            else -> ceil(((robotBlueprint[it] - resources[it]).toDouble() / robots[it])).toInt()
        }
    }

    val possibilities = mutableListOf<() -> Int>()
    if (resources[0] >= geodeRobot[0] && resources[2] >= geodeRobot[2]) {
        return buildGeodeRobot(0)
    } else if (robots[2] > 0) {
        val n = estimateMinutesNeededToBuildRobot(geodeRobot, 0, 2)
        if (n < minutesLeft) {
            possibilities.add { buildGeodeRobot(n) }
        }
    }
    if (resources[0] >= obsidianRobot[0] && resources[1] >= obsidianRobot[1]) {
        if (robots[2] == 0) return buildObsidianRobot(0)
        possibilities.add { buildObsidianRobot(0) }
    } else if (robots[1] > 0) {
        val n = estimateMinutesNeededToBuildRobot(obsidianRobot, 0, 1)
        if (n < minutesLeft) {
            possibilities.add { buildObsidianRobot(n) }
        }
    }
    if (resources[0] >= clayRobot[0]) {
        if (robots[1] == 0) return buildClayRobot(0)
        possibilities.add { buildClayRobot(0) }
    } else {
        val n = estimateMinutesNeededToBuildRobot(clayRobot, 0)
        if (n < minutesLeft) {
            possibilities.add { buildClayRobot(n) }
        }
    }
    if (resources[0] >= oreRobot[0]) {
        possibilities.add { buildOreRobot(0) }
    } else {
        val n = estimateMinutesNeededToBuildRobot(oreRobot, 0)
        if (n < minutesLeft) {
            possibilities.add { buildOreRobot(n) }
        }
    }
    return possibilities.maxOfOrNull { it() } ?: (resources[3] + robots[3] * minutesLeft)
}

private val REGEX = Regex(
    ".+Each ore robot costs (.+) ore. " +
            "Each clay robot costs (.+) ore. " +
            "Each obsidian robot costs (.+) ore and (.+) clay. " +
            "Each geode robot costs (.+) ore and (.+) obsidian."
)
