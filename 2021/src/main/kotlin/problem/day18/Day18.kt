package problem.day18

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 18
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay18Part1, ::solveDay18Part2)
}

data class Snailfish(
    var parent: Snailfish?,
    var number: Int? = null,
    var pair: Pair<Snailfish, Snailfish>? = null,
) {
    val magnitude: Int
        get() {
            return pair?.let { it.first.magnitude * 3 + it.second.magnitude * 2 } ?: number ?: error("Wrong input")
        }

    private val isPairOfNumbers: Boolean
        get() {
            return pair?.let { it.first.number != null && it.second.number != null } ?: false
        }

    operator fun plus(snailfish: Snailfish): Snailfish {
        return Snailfish(
            parent = null,
        ).also {
            it.pair = this.deepCopy(parent = it) to snailfish.deepCopy(parent = it)
            it.reduce()
        }
    }

    operator fun plusAssign(value: Int) {
        number = number!! + value
    }

    private fun reduce() {
        do {
            val reduced = tryExplode() || trySplit()
        } while (reduced)
    }

    private fun tryExplode(level: Int = 0): Boolean {
        return when {
            level >= 4 && isPairOfNumbers -> {
                explode()
                true
            }
            else -> pair?.let {
                it.first.tryExplode(level + 1) || it.second.tryExplode(level + 1)
            } ?: false
        }
    }

    private fun trySplit(level: Int = 0): Boolean {
        return when {
            number?.let { it >= 10 } ?: false -> {
                split()
                if (level >= 4) explode()
                true
            }
            else -> pair?.let {
                it.first.trySplit(level + 1) || it.second.trySplit(level + 1)
            } ?: false
        }
    }

    private fun split() {
        pair = Snailfish(this, number = number!! / 2) to Snailfish(this, number = number!! / 2 + number!!.mod(2))
        number = null
    }

    private fun explode() {
        findRegularSnailfishOnLeft()?.also { it += pair!!.first.number!! }
        findRegularSnailfishOnRight()?.also { it += pair!!.second.number!! }
        pair = null
        number = 0
    }

    private fun findRegularSnailfishOnLeft(): Snailfish? {
        return parent?.let {
            val children = it.pair!!
            if (children.second === this) {
                children.first.findRightMostSnailfish()
            } else {
                it.findRegularSnailfishOnLeft()
            }
        }
    }

    private fun findRegularSnailfishOnRight(): Snailfish? {
        return parent?.let {
            val children = it.pair!!
            if (children.first === this) {
                children.second.findLeftMostSnailfish()
            } else {
                it.findRegularSnailfishOnRight()
            }
        }
    }

    private fun findRightMostSnailfish(): Snailfish {
        return pair?.second?.findRightMostSnailfish() ?: this
    }

    private fun findLeftMostSnailfish(): Snailfish {
        return pair?.first?.findLeftMostSnailfish() ?: this
    }

    override fun toString(): String {
        return pair?.let { "[${pair!!.first},${pair!!.second}]" } ?: number!!.toString()
    }

    private fun deepCopy(parent: Snailfish? = null): Snailfish {
        return Snailfish(
            parent = parent,
            number = number,
        ).also { copy ->
            copy.pair = pair?.let { it.first.deepCopy(copy) to it.second.deepCopy(copy) }
        }
    }
}

fun solveDay18Part1(input: List<String>): Int {
    val snailfishes = input.map { it.parseSnailfish() }

    val sum = snailfishes.reduce { acc, snailfish -> acc + snailfish }
    return sum.magnitude
}

fun solveDay18Part2(input: List<String>): Int {
    val snailfishes = input.map { it.parseSnailfish() }

    val maxMagnitude = snailfishes.maxOf { first ->
        snailfishes.maxOf { second ->
            if (first === second) 0 else (first + second).magnitude
        }
    }
    return maxMagnitude
}

private fun String.parseSnailfish(parent: Snailfish? = null, range: IntRange = this.indices): Snailfish {
    return Snailfish(parent).also {
        if (this[range.first] == '[') {
            val separatorIndex = findSeparatorIndex(range.first + 1 until range.last)
            it.pair = parseSnailfish(it, range.first + 1 until separatorIndex) to
                    parseSnailfish(it, separatorIndex + 1 until range.last)
        } else {
            it.number = substring(range).toInt()
        }
    }
}

private fun String.findSeparatorIndex(range: IntRange): Int {
    var openBracketCounter = 0
    return range.first { index ->
        when {
            this[index] == ',' && openBracketCounter == 0 -> true
            this[index] == '[' -> {
                openBracketCounter++
                false
            }
            this[index] == ']' -> {
                openBracketCounter--
                false
            }
            else -> false
        }
    }
}