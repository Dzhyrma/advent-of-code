package problem.day10

import common.InputRepo
import common.readSessionCookie
import common.solve
import org.ojalgo.optimisation.ExpressionsBasedModel
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    val day = 10
    val input = InputRepo(args.readSessionCookie()).get(day = day)
    solve(day, input, ::solveDay10Part1, ::solveDay10Part2)
}

fun solveDay10Part1(input: List<String>): Int {
    val machines = input.parse()
    return machines.sumOf { machine ->
        val presses = mutableMapOf<Int, Int>().withDefault { Int.MAX_VALUE }
        presses[0] = 0
        val queue = ArrayDeque<Int>()
        queue.addLast(0)
        while (!presses.contains(machine.lightsState) && queue.isNotEmpty()) {
            val currentState = queue.removeFirst()
            machine.buttons.forEach { button ->
                val newState = currentState xor button.lightsSwitcher
                val newPresses = presses.getValue(currentState) + 1
                if (presses.getValue(newState) > newPresses) {
                    presses[newState] = newPresses
                    queue.addLast(newState)
                }
            }
        }
        presses.getValue(machine.lightsState)
    }
}

fun solveDay10Part2(input: List<String>): Int {
    val machines = input.parse()
    return machines.sumOf { machine ->
        val model = ExpressionsBasedModel()

        val buttonVariables =
            machine.buttons.mapIndexed { index, button ->
                model.newVariable("buttonPressed$index").integer().lower(0L)
            }

        machine.joltages.forEachIndexed { joltageIndex, joltage ->
            val expr = model.addExpression("joltage$joltageIndex")
            buttonVariables.forEachIndexed { index, buttonVariable ->
                if (machine.buttons[index].indices.contains(joltageIndex)) {
                    expr.set(buttonVariable, 1)
                }
            }
            expr.level(joltage.toLong())
        }

        val objective = model.addExpression("totalPresses").weight(1L)
        buttonVariables.forEach { buttonVariable ->
            objective.set(buttonVariable, 1)
        }

        val result = model.minimise()

        result.value.roundToInt()
    }
}

private fun List<String>.parse(): List<Machine> =
    map { line ->
        val lightsPart = checkNotNull(LIGHTS_REGEX.find(line)).groupValues[1]
        val lightsState =
            lightsPart
                .map { if (it == '#') '1' else '0' }
                .joinToString("")
                .toInt(2)
        val joltages =
            checkNotNull(JOLTAGE_REGEX.find(line))
                .groupValues[1]
                .split(",")
                .map { it.toInt() }

        val buttons =
            BUTTONS_REGEX.findAll(line).toList().map { match ->
                val indices = match.groupValues[1].split(",").map { it.toInt() }
                val lightsSwitcher = indices.fold(0) { acc, pos -> acc or (1 shl (lightsPart.lastIndex - pos)) }
                Button(lightsSwitcher, indices.toSet())
            }

        Machine(lightsState, joltages, buttons)
    }

private data class Machine(
    val lightsState: Int,
    val joltages: List<Int>,
    val buttons: List<Button>,
)

private data class Button(
    val lightsSwitcher: Int,
    val indices: Set<Int>,
)

private val LIGHTS_REGEX = """\[([.#]+)]""".toRegex()
private val JOLTAGE_REGEX = """\{([0-9,]+)}""".toRegex()
private val BUTTONS_REGEX = """\(([0-9,]+)\)""".toRegex()
