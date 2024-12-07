package problem.day07

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day07Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "190: 10 19",
        "3267: 81 40 27",
        "83: 17 5",
        "156: 15 6",
        "7290: 6 8 6 15",
        "161011: 16 10 13",
        "192: 17 8 14",
        "21037: 9 7 18 13",
        "292: 11 6 16 20",
    )

    val sampleSolutionPart1 = 3749

    val sampleSolutionPart2 = 11387

    "Solving day 7" - {
        "part 1 for the sample input should return the correct output" {
            solveDay07Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay07Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
