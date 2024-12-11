package problem.day11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day11Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "125 17",
    )

    val sampleSolutionPart1 = 55312

    val sampleSolutionPart2 = 65601038650482L

    "Solving day 11" - {
        "part 1 for the sample input should return the correct output" {
            solveDay11Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay11Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
