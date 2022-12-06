package problem.day06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",
    )

    val sampleSolutionPart1 = 11

    val sampleSolutionPart2 = 26

    "Solving day 6" - {
        "part 1 for the sample input should return the correct output" {
            solveDay06Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay06Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
