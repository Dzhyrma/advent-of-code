package problem.day10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Test : FreeSpec({

    val sampleInput: List<String> = listOf(
    )

    val sampleSolutionPart1 = -1L

    val sampleSolutionPart2 = -1L

    "Solving day 10" - {
        "part 1 for the sample input should return the correct output" {
            solveDay10Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay10Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
