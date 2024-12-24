package problem.day24

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.math.BigInteger

fun main(args: Array<String>) {
    val day = 24
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay24Part1, ::solveDay24Part2)
}

fun solveDay24Part1(input: List<String>): String {
    return solveGates(input)
}

fun solveDay24Part2(input: List<String>): String {
    println(convertToDot(input))
    printnBinaryValues(input)
    val result = listOf("z06", "ksv", "nbd", "kbs", "z20", "tqq", "z39", "ckb").sorted().joinToString(separator = ",")
    return result
}

private fun solveGates(input: List<String>): String {
    val (initialValues, operations) = input.partition { it.contains(":") }

    val wireValues = initialValues.associate {
        val (wire, value) = it.split(": ")
        wire to value.toInt()
    }.toMutableMap()

    val gates = operations.drop(1).map { line ->
        val (operation, output) = line.split(" -> ")
        val parts = operation.split(" ")
        Pair(parts, output)
    }

    fun evaluateWire(wire: String): Int {
        if (wireValues.containsKey(wire)) return wireValues[wire]!!
        val (inputs, output) = gates.find { it.second == wire } ?: error("No gate producing wire $wire")
        val (operand1, operation, operand2) = inputs
        val a = evaluateWire(operand1)
        val b = evaluateWire(operand2)
        wireValues[output] = when (operation) {
            "AND" -> a and b
            "OR" -> a or b
            "XOR" -> a xor b
            else -> error("Unknown operation ${inputs[1]}")
        }
        return wireValues.getValue(output)
    }

    val binaryResult = gates
        .mapNotNull { gate -> gate.second.takeIf { it.startsWith("z") } }
        .sorted()
        .joinToString(separator = "") { evaluateWire(it).toString() }
        .reversed().also { println(it) }

    return BigInteger(binaryResult, 2).toString()
}

private fun printnBinaryValues(input: List<String>) {
    val (inputs, _) = input.parseInput()
    val inputWiresX = inputs.filterKeys {
        it.startsWith("x")
    }.entries.sortedByDescending { it.key }.map { it.value }.joinToString(separator = "").also { println(it) }
    val inputWiresY = inputs.filterKeys {
        it.startsWith("y")
    }.entries.sortedByDescending { it.key }.map { it.value }.joinToString(separator = "").also { println(it) }
    BigInteger(inputWiresX, 2).plus(BigInteger(inputWiresY, 2)).toString(2).also { println(it) }
}

private fun List<String>.parseInput(): Pair<Map<String, Int>, Map<String, Pair<String, String>>> {
    val inputs = mutableMapOf<String, Int>()
    val connections = mutableMapOf<String, Pair<String, String>>()

    this.forEach { line ->
        when {
            line.contains(":") -> {
                val (wire, value) = line.split(": ")
                inputs[wire] = value.toInt()
            }
            line.contains("->") -> {
                val (gate, output) = line.split(" -> ")
                val gateType = when {
                    "AND" in gate -> "AND"
                    "OR" in gate -> "OR"
                    "XOR" in gate -> "XOR"
                    else -> throw IllegalArgumentException("Unknown gate in: $gate")
                }
                val input = gate.split(" ")[0] + " " + gate.split(" ")[2]
                connections[output] = input to gateType
            }
        }
    }
    return inputs to connections
}

fun convertToDot(inputLines: List<String>): String {
    val dotOutput = StringBuilder()
    dotOutput.appendLine("digraph G {")

    for (line in inputLines) {
        val trimmedLine = line.trim()
        if (": " in trimmedLine) {
            continue
        }
        if ("->" in trimmedLine) {
            val parts = trimmedLine.split(" -> ")
            val gateOperation = parts[0].split(" ")
            val outputWire = parts[1]

            if (gateOperation.size == 3) {
                val input1 = gateOperation[0]
                val operation = gateOperation[1]
                val input2 = gateOperation[2]

                // Add edges with labels
                dotOutput.appendLine("  \"$outputWire $operation\" [label=$operation];")
                dotOutput.appendLine("  \"$input1\" -> \"$outputWire $operation\";")
                dotOutput.appendLine("  \"$input2\" -> \"$outputWire $operation\";")
                dotOutput.appendLine("  \"$outputWire $operation\" -> \"$outputWire\";")
            } else {
                // Handle cases where the gate has a single input
                val input1 = gateOperation[0]
                dotOutput.appendLine("  \"$input1\" -> \"$outputWire\";")
            }
        }
    }

    dotOutput.appendLine("}")
    return dotOutput.toString()
}
