package problem.day06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
    )

    val sampleInput2: List<String> = listOf(
    )

    val sampleSolutionPart1 = -1

    val sampleSolutionPart2 = -1

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay06Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay06Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
