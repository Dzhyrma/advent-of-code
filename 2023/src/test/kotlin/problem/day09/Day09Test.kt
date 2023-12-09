package problem.day09

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day09Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45",
    )

    val sampleSolutionPart1 = 114

    val sampleSolutionPart2 = 2

    "Solving day 9" - {
        "part 1 for the sample input should return the correct output" {
            solveDay09Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay09Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
