package problem.day02

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day02Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "7 6 4 2 1",
        "1 2 7 8 9",
        "9 7 6 2 1",
        "1 3 2 4 5",
        "8 6 4 4 1",
        "1 3 6 7 9",
    )

    val sampleSolutionPart1 = 2

    val sampleSolutionPart2 = 4

    "Solving day 2" - {
        "part 1 for the sample input should return the correct output" {
            solveDay02Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay02Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
