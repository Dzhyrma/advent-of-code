package problem.day17

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 17
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay17Part1, ::solveDay17Part2)
}

fun solveDay17Part1(input: List<String>): String {
    val a = input.take(3).map { it.split(": ")[1].toLong() }.first()
    val program = input[4].split(": ")[1].split(",").map { it.toInt() }
    return program.runProgram(a).joinToString(",")
}

fun solveDay17Part2(input: List<String>): Long {
    val program = input[4].split(": ")[1].split(",").map { it.toInt() }
    var a = find_a(program) ?: 0
    // var a = 0b101_110_001_001_101_011_000_000_000_000_000_000_000_000_000_000

    while (true) {
        if (program.runProgram(a, expected = program) == program) return a
        a++
    }
}

private fun find_a(program: List<Int>, a: Long = 0): Long? {
    if (program.isEmpty()) return a
    for (i in 0..7L) {
        val newA = (a shl 3) or i
        val b = i xor 1
        val d = (b xor (newA shr b.toInt()) xor 4) and 7
        if (d.toInt() == program.last()) {
            val result = find_a(program.dropLast(1), newA)
            if (result != null) return result
        }
    }
    return null
}

private fun List<Int>.runProgram(aInit: Long, expected: List<Int>? = null): List<Int> {
    val regs = longArrayOf(aInit, 0, 0)
    val out = mutableListOf<Int>()
    var ip = 0

    while (ip < size) {
        when (this[ip]) {
            0 -> regs[0] = shiftRightOrZero(regs[0], regs.getComboOperand(this[ip + 1]))
            1 -> regs[1] = regs[1] xor this[ip + 1].toLong()
            2 -> regs[1] = regs.getComboOperand(this[ip + 1]) and 7
            3 -> if (regs[0] != 0L) {
                ip = this[ip + 1]
                continue
            }
            4 -> regs[1] = regs[1] xor regs[2]
            5 -> {
                out.add((regs.getComboOperand(this[ip + 1]) and 7L).toInt())
                if (expected != null && out[out.size - 1] != expected[out.size - 1]) return emptyList()
            }
            6 -> regs[1] = shiftRightOrZero(regs[0], regs.getComboOperand(this[ip + 1]))
            7 -> regs[2] = shiftRightOrZero(regs[0], regs.getComboOperand(this[ip + 1]))
        }
        ip += 2
    }
    return out
}

private fun shiftRightOrZero(a: Long, shift: Long): Long {
    if (shift > 64) {
        return 0
    }
    return a shr shift.toInt()
}

private fun LongArray.getComboOperand(operand: Int) = when {
    operand < 4 -> operand.toLong()
    operand < 7 -> this[operand - 4]
    else -> operand.toLong()
}
