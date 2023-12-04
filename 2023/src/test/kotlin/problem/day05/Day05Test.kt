package problem.day05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import problem.day04.solveDay04Part1
import problem.day04.solveDay04Part2

class Day05Test : FreeSpec({

    val sampleInput: List<String> = listOf(
    )

    val sampleSolutionPart1 = -1

    val sampleSolutionPart2 = -1

    "Solving day 5" - {
        "part 1 for the sample input should return the correct output" {
            solveDay05Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay05Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
