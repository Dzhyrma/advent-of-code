package problem.day05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day05Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
    )

    val sampleInput2: List<String> = listOf(
    )

    val sampleSolutionPart1 = 18

    val sampleSolutionPart2 = 9

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay05Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay05Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
