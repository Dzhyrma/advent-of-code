package problem.day06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200",
    )

    val sampleSolutionPart1 = 288L

    val sampleSolutionPart2 = 71503L

    "Solving day 6" - {
        "part 1 for the sample input should return the correct output" {
            solveDay06Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay06Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
