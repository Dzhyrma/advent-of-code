package problem.day06

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}


fun solveDay06Part1(input: List<String>): Long {
    val initialFishAges = input.readFishAges()
    return countTotalNumberOfFishes(80, initialFishAges)
}

fun solveDay06Part2(input: List<String>): Long {
    val initialFishAges = input.readFishAges()
    return countTotalNumberOfFishes(256, initialFishAges)
}

private fun List<String>.readFishAges() = first().split(',').map { it.toInt() }

private fun countTotalNumberOfFishes(days: Int, initialFishAges: List<Int>): Long {
    val fishAgeToCount = mutableMapOf<Int, Long>(0 to 1, 1 to 1, 2 to 1, 3 to 1, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 1)
    return initialFishAges.sumOf { age ->
        fishAgeToCount.countFishesStartingFromBirth(8 - age + days)
    }
}

private fun MutableMap<Int, Long>.countFishesStartingFromBirth(days: Int): Long {
    return getOrPut(days) {
        1 + (days - 9 downTo 0 step 7).sumOf { countFishesStartingFromBirth(it) }
    }
}