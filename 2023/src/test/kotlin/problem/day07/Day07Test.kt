package problem.day07

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day07Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483",
    )

    val sampleSolutionPart1 = 6440L

    val sampleSolutionPart2 = 5905L

    "Solving day 7" - {
        "part 1 for the sample input should return the correct output" {
            solveDay07Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay07Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
